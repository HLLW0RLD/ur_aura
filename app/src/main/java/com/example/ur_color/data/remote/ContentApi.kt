package com.example.ur_color.data.remote

import com.example.ur_color.data.model.request.CreatePostRequest
import retrofit2.http.GET
import com.example.ur_color.data.model.response.UserContent
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ContentApi {

    @POST("post/create")
    suspend fun createPost(post: CreatePostRequest)

    @GET("post/get/{id}")
    suspend fun getPostById(): Response<UserContent.Post>

    @GET("post/{user_id}/feed")
    suspend fun getUserPosts(
        @Path("user_id") userId: String,
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20
    ): Response<PaginatedResponse<UserContent.Post>>

    @GET("post/feed")
    suspend fun getFeed(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<PaginatedResponse<UserContent.Post>>
}



@Serializable
data class PaginatedResponse<T>(
    val items: List<T>,
    val pagination: PaginationInfo
)

@Serializable
data class PaginationInfo(
    val total: Int,
    val page: Int,
    val pageSize: Int,
    val totalPages: Int
)