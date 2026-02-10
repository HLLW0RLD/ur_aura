package com.example.ur_color.data.repo

import com.example.ur_color.data.local.db.PostDao
import com.example.ur_color.data.model.response.SocialContent
import com.example.ur_color.data.model.entity.toPost
import com.example.ur_color.data.model.entity.toPostEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PostRepository(
    private val postDao: PostDao,
    private val keepAllPosts: Boolean = true
) {
    suspend fun savePost(post: SocialContent.Post) {
        postDao.insert(post.toPostEntity())
    }

    suspend fun savePosts(posts: List<SocialContent.Post>) {
        val entities = posts.map { it.toPostEntity() }
        postDao.insertAll(entities)
    }

    fun getAllPosts(): Flow<List<SocialContent.Post>> {
        return postDao.getAllPosts().map { entities ->
            entities.map { it.toPost() }
        }
    }

    fun getPostsByUser(userId: String): Flow<List<SocialContent.Post>> {
        return postDao.getPostsByUserId(userId).map { entities ->
            entities.map { it.toPost() }
        }
    }

    suspend fun getPostById(postId: String): SocialContent.Post? {
        return postDao.getPostById(postId)?.toPost()
    }

    suspend fun deletePost(postId: String) {
        postDao.deleteById(postId)
    }

    suspend fun deleteAllPosts() {
        postDao.deleteAll()
    }

    suspend fun getUnsyncedPosts(): List<SocialContent.Post> {
        return postDao.getUnsyncedPosts().map { it.toPost() }
    }

    suspend fun markAsSynced(postId: String) {
        postDao.updateSyncStatus(postId, true)

        // Если выбрана стратегия "только несинхронизированные"
        if (!keepAllPosts) {
            deleteSyncedPosts()
        }
    }

    // Метод для удаления синхронизированных постов
    suspend fun deleteSyncedPosts() {
        val allPosts = postDao.getAllPosts().first() // Получаем первый элемент Flow
        val syncedPosts = allPosts.filter { it.isSynced }
        syncedPosts.forEach { postDao.delete(it) }
    }

    // Дополнительный метод для получения только несинхронизированных постов как Flow
    fun getUnsyncedPostsFlow(): Flow<List<SocialContent.Post>> {
        return postDao.getAllPosts().map { entities ->
            entities.filter { !it.isSynced }.map { it.toPost() }
        }
    }
}