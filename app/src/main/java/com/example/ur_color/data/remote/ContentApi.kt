package com.example.ur_color.data.remote

import com.example.ur_color.data.model.response.UserContent
import retrofit2.Response

interface ContentApi {

    suspend fun createPost(post: UserContent.Post)

    suspend fun getPostById(): Response<UserContent.Post>

    suspend fun getUserPosts(): Response<UserContent.Post>

    suspend fun getFeed(): Response<UserContent.Post>
}