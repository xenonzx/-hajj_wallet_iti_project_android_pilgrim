package com.example.android.pilgrim.model.pojo

import com.squareup.moshi.Json
import java.io.Serializable

/**
 * Created by Toka on 2019-06-09.
 */
data class Pilgrim(
    var username: String?,
    var email: String?,
    var password1: String?,
    var password2: String?,
    var password: String?,
    @Json(name = "first_name") var firstName: String?,
    @Json(name = "last_name") var lastName: String?,
    var gender: String?,
    @Json(name = "phone_number") var phoneNumber: Int?,
    var image: String?,
    var nationality: String?
) : Serializable