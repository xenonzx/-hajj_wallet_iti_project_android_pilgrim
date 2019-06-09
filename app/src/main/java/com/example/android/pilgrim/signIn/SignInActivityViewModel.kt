package com.example.android.pilgrim.signIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.pilgrim.model.api.PilgrimApi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

/**
 * Created by Toka on 2019-05-30.
 */
class SignInActivityViewModel : ViewModel() {

    private val _response = MutableLiveData<retrofit2.Response<ResponseBody>>()
    val response: LiveData<retrofit2.Response<ResponseBody>>
        get() = _response


    fun signIn(username: String, password: String) {
        //TODO stop retrofit when viewmodel is closed
        PilgrimApi.retrofitService.signIn(username, password)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    _response.value = null
                }

                override fun onResponse(call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                    _response.value = response
                }
            })
    }

}