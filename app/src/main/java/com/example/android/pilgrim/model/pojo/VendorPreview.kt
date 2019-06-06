package com.example.android.pilgrim.model.pojo

import java.io.Serializable

/**
 * Created by Toka on 2019-06-01.
 */
data class VendorPreview(
    val name: String,
    val type: String,
    val rating: Int,
    val distance: Double,
    val logoUrl: String
) : Serializable