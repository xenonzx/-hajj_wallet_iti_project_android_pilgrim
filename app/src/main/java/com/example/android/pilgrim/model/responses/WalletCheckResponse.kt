package com.example.android.pilgrim.model.responses

/**
 * Created by Toka on 2019-06-17.
 */

import com.squareup.moshi.Json

data class walletExistenceResponse(
    @Json(name = "success")
    val success: Success
)