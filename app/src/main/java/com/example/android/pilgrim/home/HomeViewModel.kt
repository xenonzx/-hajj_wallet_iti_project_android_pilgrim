package com.example.android.pilgrim.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.pilgrim.model.FindNearestVendorsRequest
import com.example.android.pilgrim.model.api.PilgrimApi
import com.example.android.pilgrim.model.pojo.Vendor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeViewModel : ViewModel() {
    private val _vendors = MutableLiveData<List<Vendor>>()
    val vendors: LiveData<List<Vendor>>
        get() = _vendors

    lateinit var searchCall: Call<List<Vendor>>

    fun getVendors(authorization: String, request: FindNearestVendorsRequest) {
        //TODO stop retrofit when viewmodel is closed
        PilgrimApi.retrofitService.getNearbyVendors(
            authorization,
            request
        )
            .enqueue(object : Callback<List<Vendor>> {
                override fun onFailure(call: Call<List<Vendor>>, t: Throwable) {
                    _vendors.value = null
                }

                override fun onResponse(
                    call: Call<List<Vendor>>,
                    response: Response<List<Vendor>>
                ) {
                    if (response.isSuccessful)
                        _vendors.value = response.body()
                    else
                        _vendors.value = null
                }
            })
    }

    fun searchForVendor(word: String) {
        /*searchCall = PilgrimApi.retrofitService.searchForVendor(
            authorization
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
