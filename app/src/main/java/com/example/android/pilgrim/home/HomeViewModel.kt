package com.example.android.pilgrim.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.pilgrim.model.pojo.Vendor


class HomeViewModel : ViewModel() {
    private var vendors: MutableLiveData<ArrayList<Vendor>> = MutableLiveData()

    fun getVendors(): LiveData<ArrayList<Vendor>> {
        val vendorsList: ArrayList<Vendor> = ArrayList()
        vendorsList.add(Vendor("j", "l", 1, 51.0, "http://goo.gl/gEgYUd"))
        vendorsList.add(Vendor("jkklas", "l", 2, 32.0, "http://goo.gl/gEgYUd"))
        vendorsList.add(Vendor("l;akd", "l", 5, 2.0, "http://goo.gl/gEgYUd"))
        vendorsList.add(Vendor("j", "l", 1, 51.0, "http://goo.gl/gEgYUd"))
        vendorsList.add(Vendor("jkklas", "l", 2, 32.0, "http://goo.gl/gEgYUd"))
        vendorsList.add(Vendor("l;akd", "l", 5, 2.0, "http://goo.gl/gEgYUd"))
        vendors.value = vendorsList
        return vendors
    }
}
