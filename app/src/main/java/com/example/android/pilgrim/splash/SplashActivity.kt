package com.example.android.pilgrim.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.android.pilgrim.R
import com.example.android.pilgrim.onBoarding.OnBoardingActivity
import com.example.android.pilgrim.signIn.SignInActivity

class SplashActivity : AppCompatActivity() {
    private val SPLASH_TIME_OUT: Long = 3000 // 3 sec
    lateinit var sharedPreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        sharedPreference = getSharedPreferences("Once", Context.MODE_PRIVATE)
        val text = sharedPreference.getString("Once", "once")

        Handler().postDelayed({
            if (text == "yes") {
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, OnBoardingActivity::class.java))
                finish()
            }
        }, SPLASH_TIME_OUT)

    }
}