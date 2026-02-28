package com.example.ur_color.data.remote

import retrofit2.http.GET
import com.example.ur_color.data.model.response.UserContent
import retrofit2.Response
import retrofit2.http.POST

interface ContentApi {

    @POST("post/create")
    suspend fun createPost(post: UserContent.Post)

    @GET("post/get/{id}")
    suspend fun getPostById(): Response<UserContent.Post>

    @GET("post/{user_id}/feed")
    suspend fun getUserPosts(): Response<UserContent.Post>

    @GET("post/feed")
    suspend fun getFeed(): Response<UserContent.Post>
}