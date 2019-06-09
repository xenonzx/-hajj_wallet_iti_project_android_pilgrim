package com.example.android.pilgrim.qrScanner

import androidx.lifecycle.ViewModel;
import com.example.android.pilgrim.model.pojo.Vendor

class QrScannerViewModel : ViewModel() {

    fun getVendor(vendorId: String): Vendor {
        //TODO get vendor via it's id
        return Vendor("El Bek", "Resturant", 4, 5.2, "http://goo.gl/gEgYUd")
    }

}
