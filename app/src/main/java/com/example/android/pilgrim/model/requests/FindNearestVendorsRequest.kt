package com.example.android.pilgrim.model

/**
 * Created by Toka on 2019-06-14.
 */
data class FindNearestVendorsRequest(
    val long: String,
    val lat: String,
    val category: String,
    val radius: String
)