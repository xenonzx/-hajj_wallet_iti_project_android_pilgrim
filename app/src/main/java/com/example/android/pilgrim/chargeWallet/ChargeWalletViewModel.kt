package com.example.android.pilgrim.chargeWallet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.pilgrim.model.api.PilgrimApi
import com.example.android.pilgrim.model.requests.CardFields
import com.example.android.pilgrim.model.responses.SuccessWalletCreated
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Toka on 2019-06-18.
 */
class ChargeWalletViewModel : ViewModel() {
    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response


    fun chargeWallet(cardFields: CardFields, token: String) {
        PilgrimApi.retrofitService.chargeWallet("Token " + token, cardFields)
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
                    } else
                        _response.value = null

                }
            })
    }

}