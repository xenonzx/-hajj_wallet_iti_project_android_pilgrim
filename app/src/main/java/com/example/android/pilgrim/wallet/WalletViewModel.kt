package com.example.android.pilgrim.wallet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.pilgrim.model.responses.Success

class WalletViewModel : ViewModel() {

    private val _getCheckResult = MutableLiveData<Success>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val getCheckResult: MutableLiveData<Success>
        get() = _getCheckResult


    public fun getResult(token: String) {
        // Get the Deferred object for our Retrofit request
        /*var tokenGot = "077f89fe6b85ed46cf9c18e17d592ce1886435ae"
        var getResult = PilgrimApi.retrofitService.getCheckWalletExistence("Token " + tokenGot)
            // this will run on a thread managed by Retrofit
            val Result = getResult.await()
            Log.i("zamalek ", "success of ${Result}")
            if (Result != null) {
                _getCheckResult.value = Result.success
            }

        } catch (e: Exception) {
            Log.i("eror", "this is error ${e.message.toString()}")
        }
    }*/
    }
}
