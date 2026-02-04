package com.example.ur_color.data.remote

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("register")
    fun register(@Body user: RegisterRequest) : Response<MockResponse>

    fun getUser()
}

data class RegisterRequest(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val birthPlace: String,
    val gender: String,
    val zodiacSign: String
)

data class MockResponse(
    val success: Boolean,
    val token: String,
    val user: UserMock,
)

data class UserMock(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
)