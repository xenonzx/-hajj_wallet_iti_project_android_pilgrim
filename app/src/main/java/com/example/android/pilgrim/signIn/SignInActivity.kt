package com.example.android.pilgrim.signIn

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.pilgrim.R
import com.example.android.pilgrim.home.HomeActivity
import com.example.android.pilgrim.signUp.SignUpActivity
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    private lateinit var viewModel: SignInActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.android.pilgrim.R.layout.activity_sign_in)

        initializeWithSharedPrefrences()

        viewModel = ViewModelProviders.of(this).get(SignInActivityViewModel::class.java)
        signIn.setOnClickListener {
            if (isUserDataValid()) {
                signIn.startAnimation()
                viewModel.signIn(username.text.toString(), password.text.toString())
            }
        }
        goToRegister.setOnClickListener {
            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
        }

        viewModel.response.observe(this, Observer { response ->

            if (response != null) {
                if (response.isSuccessful) {
                    if (remember_me.isChecked)
                        cacheUserData()
                    else
                        deCacheUserData()

                    startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
                    finish()
                } else {
                    signIn.revertAnimation()
                    signIn.background = ContextCompat.getDrawable(this, R.drawable.btn_background)
                    Toast.makeText(this, getString(R.string.wrong_user_data), Toast.LENGTH_SHORT).show()
                }
            } else {
                //TODO add internet connection check
                signIn.revertAnimation()
                signIn.background = ContextCompat.getDrawable(this, R.drawable.btn_background)
                Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initializeWithSharedPrefrences() {
        val sharedPreference = getSharedPreferences("LOGIN", Context.MODE_PRIVATE)
        val usernameValue = sharedPreference.getString("username", "")
        val passwordValue = sharedPreference.getString("password", "")

        if (!usernameValue.isNullOrEmpty() || !passwordValue.isNullOrEmpty())
            remember_me.isChecked = true

        username.setText(usernameValue)
        password.setText(passwordValue)
    }

    private fun cacheUserData() {
        val sharedPreference = getSharedPreferences("LOGIN", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putString("username", username.text.toString())
        editor.putString("password", password.text.toString())
        editor.apply()
    }

    private fun deCacheUserData() {
        val preferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE)
        preferences.edit().clear().apply()
    }


    private fun isUserDataValid(): Boolean {
        return isFieldValid(username) && isFieldValid(password)
    }

    private fun isFieldValid(editText: EditText): Boolean {
        return if (editText.text.toString().isEmpty()) {
            editText.error = getString(R.string.required_field)
            editText.requestFocus()
            false
        } else
            true
    }


    override fun onDestroy() {
        super.onDestroy()
        signIn.dispose()
    }
}