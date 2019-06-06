package com.example.android.pilgrim.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.pilgrim.model.pojo.VendorPreview


class HomeViewModel : ViewModel() {
    private var vendors: MutableLiveData<ArrayList<VendorPreview>> = MutableLiveData()

    fun getVendors(): LiveData<ArrayList<VendorPreview>> {
        val vendorsList: ArrayList<VendorPreview> = ArrayList()
        vendorsList.add(VendorPreview("j", "l", 1, 51.0, "http://goo.gl/gEgYUd"))
        vendorsList.add(VendorPreview("jkklas", "l", 2, 32.0, "http://goo.gl/gEgYUd"))
        vendorsList.add(VendorPreview("l;akd", "l", 5, 2.0, "http://goo.gl/gEgYUd"))
        vendors.value = vendorsList
        return vendors
    }
}
