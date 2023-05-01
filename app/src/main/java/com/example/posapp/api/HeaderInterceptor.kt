package com.example.posapp.api

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val sessionKeyProvider: () -> String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val sessionKey = sessionKeyProvider()
        Log.v("ss" , sessionKey);
        val request = chain.request().newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("clientCode", "104417")
            .addHeader("sessionKey", sessionKey)
            .build()

        return chain.proceed(request)
    }
}
