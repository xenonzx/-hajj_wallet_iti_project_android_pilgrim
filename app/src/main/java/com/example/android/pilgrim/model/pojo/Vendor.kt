package com.example.android.pilgrim.model.pojo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Toka on 2019-06-08.
 */
data class Vendor(
    val id: Int,
    @SerializedName("store_name") val storeName: String,
    val username: String,
    val email: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    val nationality: String,
    val gender: String,
    @SerializedName("phone_number") val phoneNumber: Int,
    val crn: Int,
    val code: Int,
    val category: String,
    val image: String,
    val lat: Float,
    val long: Float
) : Serializable