package com.example.android.pilgrim.signIn

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.android.pilgrim.R
import com.example.android.pilgrim.signUp.SignUpActivity
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    private lateinit var viewModel: SignInActivityViewModel
    lateinit var sharedPreference: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        initializeWithSharedPrefrences()
        viewModel = ViewModelProviders.of(this).get(SignInActivityViewModel::class.java)
        signIn.setOnClickListener {
            var name = email.text.toString()
            var passwordtaken = CRNNumber.text.toString()
            viewModel.ValidateEmailAndPAssword(email, CRNNumber, name, passwordtaken)
            viewModel.signIn(name, passwordtaken, this, progress)

        }
        goToRegister.setOnClickListener {
            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
        }
    }

    private fun initializeWithSharedPrefrences() {
        sharedPreference = getSharedPreferences("LOGIN", Context.MODE_PRIVATE)
        if (sharedPreference == null) {

        } else {
            val text = sharedPreference.getString("username", "")
            val pass = sharedPreference.getString("password", "")

            Log.i("hello", "your username is  is $text")
            Log.i("hello", "your username is  is $pass")
            email.setText(text)
            CRNNumber.setText(pass)
        }
    }
}