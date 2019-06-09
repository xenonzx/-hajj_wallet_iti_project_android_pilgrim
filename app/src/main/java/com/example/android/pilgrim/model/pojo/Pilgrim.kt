package com.example.android.pilgrim.model.pojo

import com.squareup.moshi.Json
import java.io.Serializable

/**
 * Created by Toka on 2019-06-09.
 */
data class Pilgrim(
    val username: String,
    val password: String,
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    val gender: String,
    @Json(name = "phone_number") val phoneNumber: Int?,
    val image: String?,
    val nationality: String
) : Serializable