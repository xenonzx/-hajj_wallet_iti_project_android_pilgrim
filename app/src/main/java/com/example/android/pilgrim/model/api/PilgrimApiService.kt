package com.example.android.pilgrim.model.api

/**
 * Created by Toka on 2019-06-09.
 */
import com.example.android.pilgrim.model.FindNearestVendorsRequest
import com.example.android.pilgrim.model.pojo.Pilgrim
import com.example.android.pilgrim.model.pojo.Vendor
import com.example.android.pilgrim.model.requests.CardFields
import com.example.android.pilgrim.model.requests.CreateWalletBody
import com.example.android.pilgrim.model.responses.*
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


    @GET("/vendors/categories")
    fun getCategories(): Call<List<CategoryNationalityResponse>>


    @FormUrlEncoded
    @POST("/wallet/vendor/scan/")
    fun getVendorFromQr(
        @Header("Authorization") authorization: String,
        @Field("code") code: String
    ): Call<ScanQrResponse>

    @FormUrlEncoded
    @POST("/wallet/vendor/pay/")
    fun payToVendor(
        @Header("Authorization") authorization: String,
        @Field("amount") amount: String,
        @Field("vendor_id") vendor_id: String,
        @Field("pin_code") pinCode: String,
        @Field("currency") currency: String
    ): Call<TransactionResponse>

    @GET("/pilgrims/{vendor_id}")
    fun getVendorDetails(
        @Header("Authorization") authorization: String,
        @Path("vendor_id") vendorId: String
    ): Call<VendorDetailsResponse>

    @GET("/wallet/exists")
    fun getCheckWalletExistence(@Header("Authorization") token: String):
            Call<walletExistenceResponse>

    @POST("/wallet/create/")
    fun createWallet(@Header("Authorization") token: String, @Body createWalletBody: CreateWalletBody):
            Call<SuccessWalletCreated>

    @POST("/wallet/charge/")
    fun chargeWallet(@Header("Authorization") token: String, @Body cardFields: CardFields):
            Call<SuccessWalletCreated>
}

object PilgrimApi {
    val retrofitService: PilgrimApiService by lazy { retrofit.create(PilgrimApiService::class.java) }
}