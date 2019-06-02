package com.example.android.pilgrim.model.api

import com.squareup.moshi.Json

/**
 * Created by Toka on 2019-05-30.
 */

data class Response(
    @Json(name = "token")
    val token: String?
)