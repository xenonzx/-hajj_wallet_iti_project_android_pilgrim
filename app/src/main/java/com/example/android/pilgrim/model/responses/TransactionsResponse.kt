package com.example.android.pilgrim.model.responses

import com.squareup.moshi.Json

data class TransactionsResponse(
    @Json(name = "money_paid")
    val moneyPaid: Int,
    @Json(name = "vendor_id")
    val vendor_id: Int,
    @Json(name = "vendor_username")
    val pilgrimUsername: String,
    @Json(name = "time_stamp")
    val timeStamp: String
)