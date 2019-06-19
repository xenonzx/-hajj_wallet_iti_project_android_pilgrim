package com.example.android.pilgrim.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.pilgrim.model.api.PilgrimApi
import com.example.android.pilgrim.model.responses.TransactionsResponse
import retrofit2.Call
import retrofit2.Callback

class TransActionsViewModel : ViewModel() {

    private val _transactions = MutableLiveData<List<TransactionsResponse>>()

    val transactions: LiveData<List<TransactionsResponse>>
        get() = _transactions

    fun getTransactions(token: String) {

        PilgrimApi.retrofitService.getTransactions("Token " + token).enqueue(
            object :
                Callback<List<TransactionsResponse>> {
                override fun onFailure(
                    call: Call<List<TransactionsResponse>>,
                    t: Throwable
                ) {
                    _transactions.value = null
                }

                override fun onResponse(
                    call: Call<List<TransactionsResponse>>,
                    callResponse: retrofit2.Response<List<TransactionsResponse>>
                ) {
                    if (callResponse.isSuccessful)
                        _transactions.value = callResponse.body()
                    else
                        _transactions.value = null
                }
            })


    }
}