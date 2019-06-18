package com.example.android.pilgrim.myWallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.pilgrim.model.api.PilgrimApi
import com.example.android.pilgrim.model.responses.Success
import com.example.android.pilgrim.model.responses.walletExistenceResponse
import retrofit2.Call
import retrofit2.Callback

class WalletViewModel : ViewModel() {

    private val _checkResult = MutableLiveData<Success>()

    val checkResult: MutableLiveData<Success>
        get() = _checkResult


    public fun getResult(token: String) {
        // Get the Deferred object for our Retrofit request
        PilgrimApi.retrofitService.getCheckWalletExistence("Token " + token).enqueue(object :
            Callback<walletExistenceResponse> {
            override fun onFailure(
                call: Call<walletExistenceResponse>,
                t: Throwable
            ) {
                _checkResult.value = null
            }

            override fun onResponse(
                call: Call<walletExistenceResponse>,
                callResponse: retrofit2.Response<walletExistenceResponse>
            ) {
                if (callResponse.isSuccessful) {
                    if (callResponse.body() != null)
                        _checkResult.value = callResponse.body()!!.success
                    else
                        _checkResult.value = null
                } else
                    _checkResult.value = null
            }
        })
    }

}
