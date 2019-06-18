package com.example.android.pilgrim.model.responses

import com.squareup.moshi.Json

data class Success(
    @Json(name = "total_balance")
    val totalBalance: Int
)