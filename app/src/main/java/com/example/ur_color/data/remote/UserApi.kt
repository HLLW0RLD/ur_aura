package com.example.ur_color.data.remote

import com.example.ur_color.data.model.user.UserData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("user/me")
    fun getMe(): Response<UserData>

    @GET("user/{id}")
    fun getUserById(
        @Query("id") id: String
    ): Response<UserData>
}