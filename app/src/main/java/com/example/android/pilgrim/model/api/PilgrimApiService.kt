package com.example.android.pilgrim.model.api

/**
 * Created by Toka on 2019-06-09.
 */
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

private const val BASE_URL = "https://hajwallet.herokuapp.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface PilgrimApiService {

    @FormUrlEncoded
    @POST("custom/login/")
    fun signIn(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<ResponseBody>

}

object PilgrimApi {
    val retrofitService: PilgrimApiService by lazy { retrofit.create(PilgrimApiService::class.java) }
}