package com.example.posapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.posapp.viewmodels.LoginViewModel
import androidx.activity.viewModels
import com.example.posapp.activities.HomeActivity

class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var loadingProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val etUsername = findViewById<EditText>(R.id.usernameEditText)
        val etPassword = findViewById<EditText>(R.id.passwordEditText)
        val btnLogin = findViewById<Button>(R.id.loginButton)
        loadingProgressBar = findViewById(R.id.loadingProgressBar)

        loginViewModel.loginStatus.observe(this) { response ->
            loadingProgressBar.visibility = View.GONE

            if (response != null) {
                val loginResponse = response.body()
                val records = loginResponse?.records
                val sessionKey = records?.get(0)?.sessionKey
                val sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                sharedPreferences.edit().putString("session_key", sessionKey).apply()
                if (sessionKey != null) {
                    Log.v("sharedKey",sessionKey)
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
                };
            }else{
                Toast.makeText(this, "Please enter correct username and password", Toast.LENGTH_SHORT).show()
            }

        }
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show()
            } else {
                loadingProgressBar.visibility = View.VISIBLE
                loginViewModel.loginUser(username, password)
            }
        }

    }
}