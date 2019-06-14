package com.example.android.pilgrim.signIn

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.pilgrim.R
import com.example.android.pilgrim.model.api.PilgrimApi
import com.example.android.pilgrim.model.responses.PilgrimRegisterResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback

/**
 * Created by Toka on 2019-05-30.
 */
class SignInActivityViewModel : ViewModel() {

    private val _response = MutableLiveData<PilgrimRegisterResponse>()
    val response: LiveData<PilgrimRegisterResponse>
        get() = _response


    fun signIn(username: String, password: String, context: Context) {
        //TODO stop retrofit when viewmodel is closed
        PilgrimApi.retrofitService.signIn(username, password)
            .enqueue(object : Callback<PilgrimRegisterResponse> {
                override fun onFailure(call: Call<PilgrimRegisterResponse>, t: Throwable) {
                    _response.value = null
                    Toast.makeText(
                        context,
                        context.getString(R.string.error),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(
                    call: Call<PilgrimRegisterResponse>,
                    callResponse: retrofit2.Response<PilgrimRegisterResponse>
                ) {

                    if (callResponse.isSuccessful)
                        _response.value = callResponse.body()
                    else {
                        if (callResponse.code() == 400) {
                            val gson = Gson()
                            val json = gson.fromJson(
                                callResponse.errorBody()!!.string(),
                                PilgrimRegisterResponse::class.java
                            )
                            _response.value = json
                            Toast.makeText(
                                context,
                                json.errors!!.get(0).message?.get(0),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            _response.value = null

                            Toast.makeText(
                                context,
                                context.getString(R.string.error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }
            })
    }

}