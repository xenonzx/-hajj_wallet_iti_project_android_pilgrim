package com.example.android.pilgrim.model.api

/**
 * Created by Toka on 2019-06-09.
 */
import com.example.android.pilgrim.model.FindNearestVendorsRequest
import com.example.android.pilgrim.model.pojo.Pilgrim
import com.example.android.pilgrim.model.pojo.Vendor
import com.example.android.pilgrim.model.responses.CategoryNationalityResponse
import com.example.android.pilgrim.model.responses.PilgrimRegisterResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

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
    ): Call<PilgrimRegisterResponse>


    @POST("pilgrims/registration/pilgrims_register")
    fun signUp(@Body pilgrim: Pilgrim): Call<PilgrimRegisterResponse>

    @GET("accounts/nationalities")
    fun getNationalities(): Call<List<CategoryNationalityResponse>>


    @POST("/search/nearest_vendors")
    fun getNearbyVendors(
        @Header("Authorization") authorization: String,
        @Body request: FindNearestVendorsRequest
    ): Call<List<Vendor>>


    @FormUrlEncoded
    @POST("/vendors/find")
    fun searchForVendor(
        @Field("search_word") searchWord: String
    ): Call<List<Vendor>>

}

object PilgrimApi {
    val retrofitService: PilgrimApiService by lazy { retrofit.create(PilgrimApiService::class.java) }
}