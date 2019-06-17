package com.example.android.pilgrim.model.responses

import com.squareup.moshi.Json

/**
 * Created by Toka on 2019-06-17.
 */
data class VendorDetailsResponse(
    val id: String?,
    @Json(name = "store_name") val storeName: String,
    val username: String,
    val email: String?,
    @Json(name = "first_name") val firstName: String?,
    @Json(name = "last_name") val lastName: String?,
    val nationality: String?,
    val gender: String?,
    @Json(name = "phone_number") val phoneNumber: String?,
    val crn: String?,
    val code: String?,
    val category: String,
    val image: String,
    val lat: String?,
    val long: String?,
    val errors: List<ErrorResponse>?
)