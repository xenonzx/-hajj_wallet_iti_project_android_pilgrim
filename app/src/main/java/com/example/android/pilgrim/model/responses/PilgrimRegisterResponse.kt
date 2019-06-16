package com.example.android.pilgrim.model.responses

import com.example.android.pilgrim.model.pojo.Pilgrim

/**
 * Created by Toka on 2019-06-13.
 */
data class PilgrimRegisterResponse(
    val token: String?,
    val user: Pilgrim?,
    val errors: List<ErrorArrayResponse>?
)