package com.example.android.pilgrim.vendorDetails

import androidx.lifecycle.ViewModel
import com.example.android.pilgrim.model.pojo.Vendor

/**
 * Created by Toka on 2019-06-08.
 */
class VendorDetailsViewModel : ViewModel() {

    fun getVendor(): Vendor {
        val vendor = Vendor("j", "l", 1, 51.0, "http://goo.gl/gEgYUd")
        return vendor
    }
}