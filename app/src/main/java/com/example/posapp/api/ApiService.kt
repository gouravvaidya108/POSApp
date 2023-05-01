package com.example.posapp.api

import com.example.posapp.models.LoginResponse
import com.example.posapp.models.Product
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("api/")
    suspend fun loginUser(
        @Field("clientCode") clientCode: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("request") request: String = "verifyUser",
        @Field("sendContentType") sendContentType: Int = 1
    ): Response<LoginResponse>

    @GET("v1/ui/product")
    suspend fun getProducts(@Query("skip") skip: Int, @Query("take") take: Int): List<Product>

    @GET("v1/product/full-text-lookup")
    suspend fun searchProducts(@Query("lookupPhrase") searchString: String): List<Product>
}
