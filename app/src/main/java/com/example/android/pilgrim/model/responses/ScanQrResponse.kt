package com.example.android.pilgrim.model.responses

/**
 * Created by Toka on 2019-06-16.
 */
data class ScanQrResponse(
    val code: List<String>?,
    val detail: String?,
    val error: String?,
    val store_name: String?,
    val store_image: String?,
    val store_id: String?,
    val store_username: String?,
    val store_category: String?,
    val errors: List<ErrorResponse>?
)