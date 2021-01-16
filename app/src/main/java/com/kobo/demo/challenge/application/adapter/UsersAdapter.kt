package com.kobo.demo.challenge.application.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.kobo.demo.challenge.R
import com.kobo.demo.challenge.application.model.User
import kotlinx.android.synthetic.main.item_user_row.view.*

class UsersAdapter(private val onUserClickListener: OnUserClickListener) :
    RecyclerView.Adapter<ViewHolder>() {

    private val ITEM = 0
    private val LOADING = 1
    private var isLoadingAdded = false

    private val usersList: ArrayList<User> = arrayListOf()
    class UserViewHolder(itemView: View) :
        ViewHolder(itemView) {
        fun bind(user: User) {
            Glide.with(itemView).load(user.avatar).circleCrop().into(itemView.imageView)
            itemView.txt_user.text = "${user.firstName} ${user.lastName ?: ""}"
        }
    }


    internal class LoadingVH(itemView: View) : ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var viewHolder: ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            ITEM -> {
                val v2: View = inflater.inflate(R.layout.item_user_row, parent, false)
                viewHolder = UserViewHolder(v2)
            }
            LOADING -> {
                val v2: View = inflater.inflate(R.layout.item_loading, parent, false)
                viewHolder =
                    LoadingVH(
                        v2
                    )
            }
        }
        return viewHolder!!

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = usersList[position]
        when (getItemViewType(position)) {
            ITEM -> {
                val userViewHolder: UserViewHolder = holder as UserViewHolder
                userViewHolder.bind(user)
                userViewHolder.itemView.setOnClickListener {
                    onUserClickListener.onItemClick(user)
                }
            }
            LOADING -> {
                Log.i("TAG", "Progress is loading")
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == usersList.size - 1 && isLoadingAdded) LOADING else ITEM
    }


    private fun add(user: User?) {
        user?.let {
            usersList.add(it)
            notifyItemInserted(usersList.size - 1)
        }
    }

    fun addAll(usersList: List<User?>) {
        for (result in usersList) {
            add(result)
        }
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(User())
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position: Int = usersList.size - 1
        val result = getItem(position)
        if (result != null) {
            usersList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    private fun getItem(position: Int): User? {
        return usersList.get(position)
    }


    interface OnUserClickListener {
        fun onItemClick(user: User)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }
}


