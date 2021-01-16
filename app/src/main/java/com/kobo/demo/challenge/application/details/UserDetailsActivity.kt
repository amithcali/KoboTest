package com.kobo.demo.challenge.application.details

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.kobo.demo.challenge.R
import com.kobo.demo.challenge.application.model.User
import com.kobo.demo.challenge.databinding.ActivityUserDetailsBinding
import kotlinx.android.synthetic.main.activity_user_details.*


class UserDetailsActivity : AppCompatActivity() {

    private var dataBinding: ActivityUserDetailsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_user_details)
        val user = intent.getStringExtra("user")
        val userInfo = Gson().fromJson<User>(user, User::class.java)
        dataBinding?.user = userInfo
        dataBinding?.executePendingBindings()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "User Details"
        loadImage(userInfo.avatarLarge)
//        if (!userInfo.backgroundColor.isNullOrEmpty()) {
//            container.setBackgroundColor(Color.parseColor(userInfo.backgroundColor))
//        }
    }

    private fun loadImage(avatarLarge: String?) {
        avatarLarge.let {
            Glide.with(this).load(it).into(iv_profile)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}