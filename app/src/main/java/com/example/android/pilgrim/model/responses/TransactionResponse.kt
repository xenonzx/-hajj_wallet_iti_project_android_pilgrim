package com.example.android.pilgrim.model.responses

/**
 * Created by Toka on 2019-06-16.
 */
data class TransactionResponse(
    val currency: List<String>?,
    val detail: String?,
    val error: String?,
    val success: String?
)