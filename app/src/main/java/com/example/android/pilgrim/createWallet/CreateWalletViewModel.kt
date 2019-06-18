package com.example.android.pilgrim.createWallet

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.pilgrim.model.api.PilgrimApi
import com.example.android.pilgrim.model.requests.CreateWalletBody
import com.example.android.pilgrim.model.responses.SuccessWalletCreated
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateWalletViewModel : ViewModel() {


    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response


    fun CreateWallet(createWalletBody: CreateWalletBody, context: Context, token: String) {
        PilgrimApi.retrofitService.createWallet("Token " + token, createWalletBody)
            .enqueue(object : Callback<SuccessWalletCreated> {
                override fun onFailure(call: Call<SuccessWalletCreated>, t: Throwable) {
                    _response.value = null
                }

                override fun onResponse(
                    call: Call<SuccessWalletCreated>,
                    response: Response<SuccessWalletCreated>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            _response.value = response.body()!!.success
                        } else
                            _response.value = null
                    }

                }
            })
    }


}