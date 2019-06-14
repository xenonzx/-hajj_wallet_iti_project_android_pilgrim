package com.example.android.pilgrim.nationality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.pilgrim.model.api.PilgrimApi
import com.example.android.pilgrim.model.responses.CategoryNationalityResponse
import retrofit2.Call
import retrofit2.Callback


class NationalityViewModel : ViewModel() {

    private val _response = MutableLiveData<List<CategoryNationalityResponse>>()
    val response: LiveData<List<CategoryNationalityResponse>>
        get() = _response

    fun getNationalities() {
        PilgrimApi.retrofitService.getNationalities()
            .enqueue(object : Callback<List<CategoryNationalityResponse>> {
                override fun onFailure(
                    call: Call<List<CategoryNationalityResponse>>,
                    t: Throwable
                ) {
                    _response.value = null
                }

                override fun onResponse(
                    call: Call<List<CategoryNationalityResponse>>,
                    callResponse: retrofit2.Response<List<CategoryNationalityResponse>>
                ) {
                    _response.value = callResponse.body()
                }
            })
    }
}