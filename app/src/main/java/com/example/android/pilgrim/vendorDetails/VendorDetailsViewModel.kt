package com.example.android.pilgrim.vendorDetails

import androidx.lifecycle.ViewModel
import com.example.android.pilgrim.model.pojo.Vendor

/**
 * Created by Toka on 2019-06-08.
 */
class VendorDetailsViewModel : ViewModel() {

    fun getVendor(): Vendor {
        val vendor = Vendor(
            null,
            "as",
            "as",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            "l",
            "lk",
            null,
            null,
            null
        )
         return vendor
    }
}