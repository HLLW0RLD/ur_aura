package com.example.ur_color.data.remote

import com.example.ur_color.data.model.request.UserAuth
import com.example.ur_color.data.model.request.UserRegistration
import com.example.ur_color.data.model.response.AuthData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/register")
    suspend fun register(@Body user: UserRegistration) : Response<AuthData>

    @POST("auth/login")
    suspend fun login(@Body user: UserAuth): Response<AuthData>
}