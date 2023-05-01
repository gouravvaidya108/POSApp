package com.example.posapp.utils

import android.content.Context
import com.example.posapp.activities.MyApplication
import com.example.posapp.api.ApiService
import com.example.posapp.api.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object ApiUtils {
    private const val BASE_URL_ONE = "https://104417.erply.com/"
    private const val BASE_URL_TWO = "https://api-pim-eu10.erply.com/"
    private fun getSessionKey(): String {
        val sharedPreferences = MyApplication.appContext.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("session_key", "") ?: ""
    }
    val instanceOne: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_ONE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(HeaderInterceptor(::getSessionKey))
        .build()
    val instanceTwo: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_TWO)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        retrofit.create(ApiService::class.java)
    }
}

