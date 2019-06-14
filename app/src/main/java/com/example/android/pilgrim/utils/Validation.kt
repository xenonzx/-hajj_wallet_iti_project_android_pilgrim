package com.example.android.pilgrim.utils

import android.content.Context
import android.widget.EditText
import com.example.android.pilgrim.R

/**
 * Created by Toka on 2019-06-12.
 */
class Validation {
    companion object {

        fun isPasswordValid(context: Context, editText: EditText): Boolean {
            return !isNumericPassword(context, editText) && isPasswordLengthValid(context, editText)
        }

        private fun isNumericPassword(context: Context, editText: EditText): Boolean {
            val isNumericOnly = editText.text.toString().toIntOrNull()
            return if (isNumericOnly != null) {
                editText.error = context.getString(R.string.password_is_numbers)
                editText.requestFocus()
                true
            } else
                false
        }

        private fun isPasswordLengthValid(context: Context, editText: EditText): Boolean {
            return if (editText.text.toString().length > 7)
                true
            else {
                editText.error = context.getString(R.string.password_is_short)
                editText.requestFocus()
                false
            }
        }

        fun passwordsMatch(context: Context, password1: EditText, password2: EditText): Boolean {
            if (password1.text.toString() == password2.text.toString())
                return true
            else {
                password2.error = context.getString(R.string.password_doesnt_match)
                return false
            }
        }

        fun isFieldValid(context: Context, editText: EditText): Boolean {
            return if (editText.text.toString().isEmpty()) {
                editText.error = context.getString(R.string.required_field)
                editText.requestFocus()
                false
            } else
                true
        }

        fun isEmailValid(context: Context, editText: EditText): Boolean {
            return if (android.util.Patterns.EMAIL_ADDRESS.matcher(editText.text.toString()).matches())
                true
            else {
                editText.error = context.getString(R.string.not_valid_email)
                false
            }
        }
    }
}