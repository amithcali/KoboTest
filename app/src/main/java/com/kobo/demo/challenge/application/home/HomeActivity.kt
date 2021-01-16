package com.kobo.demo.challenge.application.home

import android.content.Intent
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.gson.Gson
import com.kobo.demo.challenge.R
import com.kobo.demo.challenge.application.MainApplication
import com.kobo.demo.challenge.application.adapter.UsersAdapter
import com.kobo.demo.challenge.application.data.Resource
import com.kobo.demo.challenge.application.details.UserDetailsActivity
import com.kobo.demo.challenge.application.local.UserDatabase
import com.kobo.demo.challenge.application.model.User
import com.kobo.demo.challenge.application.views.PaginationScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), UsersAdapter.OnUserClickListener {

    private lateinit var mAdapter: UsersAdapter
    private lateinit var mLayoutManger: LinearLayoutManager
    private var pages = ArrayList<String>()
    private var viewModel: HomeViewModel? = null
    private var isLoad = false
    private var PAGE_SIZE = 0
    private var isLast = false
    private var userDatabase: UserDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

      //  (applicationContext as My).appComponent.inject(this)

        userDatabase = UserDatabase.getInstance(this)
        mLayoutManger = LinearLayoutManager(this)
        rv_users.layoutManager = mLayoutManger

        mAdapter = UsersAdapter(this)
        rv_users.adapter = mAdapter
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        if (isOnline(this)) {
            viewModel?.getUserPages()?.observe(this, Observer {
                if (it != null && !it.pages.isNullOrEmpty()) {
                    PAGE_SIZE = it.pages.size
                    addOnScrollListener()
                    pages.clear()
                    pages.addAll(it.pages.reversed())
                    getUsers()
                }
            })
        } else {
            viewModel?.getUsersFromDb()?.observe(this, Observer {
                mAdapter.addAll(it)
            })
        }

    }


    private fun addOnScrollListener() {
        rv_users.addOnScrollListener(object : PaginationScrollListener(mLayoutManger) {
            override fun pageSize(): Int {
                return PAGE_SIZE
            }

            override fun loadMoreItems() {
                isLoad = true
                getUsers()
            }

            override fun isLastPage(): Boolean {
                return isLast
            }

            override fun isLoading(): Boolean {
                return isLoad
            }

        })
    }

    private fun getUsers() {
        val page = PAGE_SIZE - 1
        PAGE_SIZE -= 1
        viewModel?.callUsers(pages[page])?.observe(this, Observer {
            if (it.status == Resource.Status.SUCCESS) {
                isLoad = false
                it.data?.let { it1 -> mAdapter.addAll(it1) }
                Log.e("TAG", "---$PAGE_SIZE")
                if (PAGE_SIZE != 0) {
                    mAdapter.addLoadingFooter()
                } else {
                    mAdapter.removeLoadingFooter()
                    isLast = true
                }

                GlobalScope.launch {
                    userDatabase!!.userDao().insertAll(it.data!!)
                }
            }
        })
    }

    override fun onItemClick(user: User) {
        val intent = Intent(this, UserDetailsActivity::class.java)
        intent.putExtra("user", Gson().toJson(user))
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return true
            }
        }
        return false
    }
}