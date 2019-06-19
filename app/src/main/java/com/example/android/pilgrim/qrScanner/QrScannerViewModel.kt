package com.example.android.pilgrim.qrScanner

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.pilgrim.R
import com.example.android.pilgrim.model.api.PilgrimApi
import com.example.android.pilgrim.model.pojo.Vendor
import com.example.android.pilgrim.model.responses.ScanQrResponse
import com.example.android.pilgrim.model.responses.TransfareRequest
import com.example.android.pilgrim.model.responses.VendorDetailsResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback

class QrScannerViewModel : ViewModel() {

    private val _vendor = MutableLiveData<Vendor>()
    val vendor: LiveData<Vendor>
        get() = _vendor

    private val _transactionResult = MutableLiveData<String>()
    val transactionResult: LiveData<String>
        get() = _transactionResult

    private val _vendorDetails = MutableLiveData<Vendor>()
    val vendorDetails: LiveData<Vendor>
        get() = _vendorDetails


    fun getVendor(authorization: String, vendorCode: String, context: Context) {
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

    fun transferMoney(
        authorization: String,
        vendorId: String,
        amount: String,
        pinCode: String,
        currency: String
    ) {
        PilgrimApi.retrofitService.payToVendor(authorization, amount, vendorId, pinCode, currency)
            .enqueue(object : Callback<TransfareRequest> {
                override fun onFailure(call: Call<TransfareRequest>, t: Throwable) {
                    _transactionResult.value = null
                }

                override fun onResponse(
                    call: Call<TransfareRequest>,
                    callResponse: retrofit2.Response<TransfareRequest>
                ) {

                    if (callResponse.isSuccessful) {
                        _transactionResult.value = callResponse.body()!!.success
                    } else {
                        _transactionResult.value = null
                    }
                }
            })
    }

    fun getVendorDetails(
        authorization: String,
        vendorId: String
    ) {
        PilgrimApi.retrofitService.getVendorDetails(authorization, vendorId)
            .enqueue(object : Callback<VendorDetailsResponse> {
                override fun onFailure(call: Call<VendorDetailsResponse>, t: Throwable) {
                    _vendorDetails.value = null
                }

                override fun onResponse(
                    call: Call<VendorDetailsResponse>,
                    callResponse: retrofit2.Response<VendorDetailsResponse>
                ) {

                    if (callResponse.isSuccessful) {
                        val body = callResponse.body()!!
                        _vendorDetails.value = Vendor(
                            body.id,
                            body.storeName,
                            body.username,
                            body.email,
                            body.firstName,
                            body.lastName,
                            body.nationality,
                            body.gender,
                            body.phoneNumber,
                            body.crn,
                            body.code,
                            body.category,
                            body.image,
                            body.lat,
                            body.long,
                            null
                        )
                    } else {
                        _vendorDetails.value = null
                    }
                }
            })

    }

}

