package com.example.ur_color.data.repo

import com.example.ur_color.data.local.db.PostDao
import com.example.ur_color.data.model.response.UserContent
import com.example.ur_color.data.model.entity.toPost
import com.example.ur_color.data.model.entity.toPostEntity
import com.example.ur_color.data.remote.ContentApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PostRepository(
    private val postDao: PostDao,
    private val contentApi: ContentApi,
    private val keepAllPosts: Boolean = true
) {
    suspend fun createPost(post: UserContent.Post) {
        postDao.insert(post.toPostEntity())
    }

    suspend fun getPostById(postId: String): UserContent.Post? {
        return postDao.getPostById(postId)?.toPost()
    }

    fun getUserPosts(userId: String): Flow<List<UserContent.Post>> {
        return postDao.getPostsByUserId(userId).map { entities ->
            entities.map { it.toPost() }
        }
    }

    fun getFeed(): Flow<List<UserContent.Post>> {
        return postDao.getAllPosts().map { entities ->
            entities.map { it.toPost() }
        }
    }

    suspend fun deletePost(postId: String) {
        postDao.deleteById(postId)
    }
}