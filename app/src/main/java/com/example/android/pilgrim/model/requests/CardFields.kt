package com.example.android.pilgrim.model.requests

/**
 * Created by Toka on 2019-06-18.
 */
data class CardFields(
    val card_number: String,
    val exp_month: String,
    val exp_year: String,
    val cvc: String,
    val amount: String,
    val currency: String,
    val pin_code: String
)