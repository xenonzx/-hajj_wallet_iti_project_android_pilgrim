package com.example.android.pilgrim.qrScanner

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.pilgrim.R
import com.example.android.pilgrim.model.api.PilgrimApi
import com.example.android.pilgrim.model.pojo.Vendor
import com.example.android.pilgrim.model.responses.ScanQrResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback

class QrScannerViewModel : ViewModel() {

    private val _vendor = MutableLiveData<Vendor>()
    val vendor: LiveData<Vendor>
        get() = _vendor


    fun getVendor(authorization: String, vendorCode: String, context: Context) {
        Log.i("QR", "token $authorization")
        PilgrimApi.retrofitService.getVendorFromQr(authorization, vendorCode)
            .enqueue(object : Callback<ScanQrResponse> {
                override fun onFailure(call: Call<ScanQrResponse>, t: Throwable) {
                    _vendor.value = null
                    Toast.makeText(
                        context,
                        context.getString(R.string.error),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(
                    call: Call<ScanQrResponse>,
                    callResponse: retrofit2.Response<ScanQrResponse>
                ) {

                    if (callResponse.isSuccessful) {
                        val body = callResponse.body()!!
                        val vendor = Vendor(
                            body.store_id!!,
                            body.store_name!!,
                            body.store_username!!,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            body.store_category!!,
                            body.store_image!!,
                            null,
                            null,
                            null
                        )
                        _vendor.value = vendor
                    } else {
                        if (callResponse.code() == 400 || callResponse.code() == 401) {

                            val gson = Gson()
                            val json = gson.fromJson(
                                callResponse.errorBody()!!.string(),
                                ScanQrResponse::class.java
                            )
                            if (!json?.error.isNullOrEmpty()) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.no_vendor),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (!json.code.isNullOrEmpty() || !json.detail.isNullOrEmpty() || !json.errors.isNullOrEmpty()) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.error),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        _vendor.value = null
                    }
                }
            })
    }

    fun transferMoney() {

    }

}

