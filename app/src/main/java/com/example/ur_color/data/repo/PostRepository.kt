package com.example.ur_color.data.repo

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.local.db.PostDao
import com.example.ur_color.data.model.response.UserContent
import com.example.ur_color.data.model.entity.toPost
import com.example.ur_color.data.model.entity.toPostEntity
import com.example.ur_color.data.model.request.CreatePostRequest
import com.example.ur_color.data.remote.ContentApi
import com.example.ur_color.utils.getCurrentDateTime
import com.example.ur_color.utils.logDebug
import com.example.ur_color.utils.logError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class PostRepository(
    private val postDao: PostDao,
    private val contentApi: ContentApi,
    private val keepAllPosts: Boolean = true
) {
    suspend fun createPost(
        text: String,
        authorId: String,
    ) {
        try {
            val request = CreatePostRequest(
                id = UUID.randomUUID().toString(),
                text = text,
                created = getCurrentDateTime(),
                authorId = authorId,
                image = null
            )

            val response = contentApi.createPost(request)

            if (!response.isSuccessful) {
                logError("Failed to create post: ${response.code()}")
            }
        } catch (e: Exception) {
            logError("Failed to create post: ${e}")
        }
    }

    suspend fun getPostById(postId: String): UserContent.Post? {
        return try {
            val response = contentApi.getPostById(postId)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    fun getUserPosts(userId: String): PagingSource<Int, UserContent.Post> {
        return UserPostsPagingSource(contentApi, userId)
    }

    fun getFeed(): PagingSource<Int, UserContent.Post> {
        return FeedPagingSource(contentApi)
    }
}

class UserPostsPagingSource(
    private val contentApi: ContentApi,
    private val userId: String
) : PagingSource<Int, UserContent.Post>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserContent.Post> {
        return try {
            val page = params.key ?: 1
            logDebug("page = ${params.key}")
            val response = contentApi.getUserPosts(
                userId = userId,
                page = page,
                pageSize = params.loadSize
            )

            logDebug("response = ${response.isSuccessful}")
            if (response.isSuccessful) {
                val paginatedResponse = response.body()
                val posts = paginatedResponse?.items ?: emptyList()

                LoadResult.Page(
                    data = posts,
                    prevKey = if (page > 1) page - 1 else null,
                    nextKey = if (posts.isNotEmpty() && page < (paginatedResponse?.pagination?.totalPages ?: 1))
                        page + 1 else null
                )
            } else {
                LoadResult.Error(Exception("Failed to load user posts: ${response.code()}"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserContent.Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}

class FeedPagingSource(
    private val contentApi: ContentApi
) : PagingSource<Int, UserContent.Post>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserContent.Post> {
        return try {
            val page = params.key ?: 1
            val response = contentApi.getFeed(
                page = page,
                pageSize = params.loadSize
            )

            if (response.isSuccessful) {
                val paginatedResponse = response.body()
                val posts = paginatedResponse?.items ?: emptyList()

                LoadResult.Page(
                    data = posts,
                    prevKey = if (page > 1) page - 1 else null,
                    nextKey = if (posts.isNotEmpty() && page < (paginatedResponse?.pagination?.totalPages ?: 1))
                        page + 1 else null
                )
            } else {
                LoadResult.Error(Exception("Failed to load feed: ${response.code()}"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserContent.Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
