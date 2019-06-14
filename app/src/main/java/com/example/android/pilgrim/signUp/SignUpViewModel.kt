package com.example.android.pilgrim.signUp

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.pilgrim.R
import com.example.android.pilgrim.model.api.PilgrimApi
import com.example.android.pilgrim.model.pojo.Pilgrim
import com.example.android.pilgrim.model.responses.PilgrimRegisterResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback

/**
 * Created by Toka on 2019-06-11.
 */
class SignUpViewModel : ViewModel() {
    private val _response = MutableLiveData<PilgrimRegisterResponse>()
    val response: LiveData<PilgrimRegisterResponse>
        get() = _response


    fun signUp(pilgrim: Pilgrim, context: Context) {
        //TODO stop retrofit when viewmodel is closed
        PilgrimApi.retrofitService.signUp(pilgrim)
            .enqueue(object : Callback<PilgrimRegisterResponse> {
                override fun onFailure(call: Call<PilgrimRegisterResponse>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()

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