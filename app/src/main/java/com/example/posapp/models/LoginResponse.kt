package com.example.posapp.models

data class LoginResponse (
    val status: Status,
    val records: List<Record>
)

data class Status (
    val request: String,
    val requestUnixTime: Long,
    val responseStatus: String,
    val errorCode: Int,
    val generationTime: Double,
    val recordsTotal: Int,
    val recordsInResponse: Int
)

data class Record (
    val userID: String,
    val userName: String,
    val employeeID: String,
    val employeeName: String,
    val groupID: String,
    val groupName: String,
    val ipAddress: String,
    val sessionKey: String,
    val sessionLength: Int,
    val isPasswordExpired: Boolean,
    val loginUrl: String,
    // etc.
)
