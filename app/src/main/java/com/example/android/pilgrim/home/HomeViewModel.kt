package com.example.android.pilgrim.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.pilgrim.model.pojo.Vendor


class HomeViewModel : ViewModel() {
    private val _vendors = MutableLiveData<List<Vendor>>()
    val vendors: LiveData<List<Vendor>>
        get() = _vendors

    fun getVendors(token: String, category: String, lat: Float, lng: Float, radius: Float) {
        /*//TODO stop retrofit when viewmodel is closed
        val authorization = "Token $token"
        PilgrimApi.retrofitService.getNearbyVendors(
            authorization,
            lat.toString(),
            lng.toString(),
            category,
            radius.toString()
        )
            .enqueue(object : Callback<List<Vendor>> {
                override fun onFailure(call: Call<List<Vendor>>, t: Throwable) {
                    _vendors.value = null
                }

                override fun onResponse(
                    call: Call<List<Vendor>>,
                    response: Response<List<Vendor>>
                ) {
                    _vendors.value = response.body()
                }
            })*/
    }
}
