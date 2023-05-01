package com.example.posapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posapp.models.LoginResponse
import com.example.posapp.models.User
import com.example.posapp.utils.ApiUtils
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel : ViewModel() {
    val loginStatus = MutableLiveData<Response<LoginResponse>?>()

    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = ApiUtils.instanceOne.loginUser(clientCode = "104417", username=username, password=password, request="verifyUser", sendContentType=1)
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    val records = loginResponse?.records
                    val sessionKey = records?.get(0)?.sessionKey
                    loginStatus.postValue(response)
                } else {
                    // Handle the case when the HTTP response status is not in the 200-299 range.
                    // You might want to send a specific error message
                    loginStatus.postValue(null)

                }
            } catch (e: Exception) {
                // Handle the exception (network error, parsing error, etc.)
                loginStatus.postValue(null)
                // You might want to send a specific error message
            }
        }


    }
}
