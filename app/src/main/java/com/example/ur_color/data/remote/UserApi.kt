package com.example.ur_color.data.remote

import com.example.ur_color.data.model.user.UserData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {

    @GET("users/me")
    suspend fun getMe(): Response<ApiResponse<UserData>>

    @GET("users/{id}")
    suspend fun getUserById(
        @Path("id") id: String
    ): Response<ApiResponse<UserData>>
}

data class ApiResponse<T>(
    val success: Boolean,
    val data: T
)