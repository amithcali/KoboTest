package com.kobo.demo.challenge.application.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.kobo.demo.challenge.R
import com.kobo.demo.challenge.application.home.HomeActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        val splashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        splashViewModel.liveData.observe(this, Observer {
            when (it) {
                is SplashState.MainActivity -> {
                    goToMainActivity()
                }
            }
        })
    }

    private fun goToMainActivity() {
        finish()
        startActivity(Intent(this, HomeActivity::class.java))
    }
}