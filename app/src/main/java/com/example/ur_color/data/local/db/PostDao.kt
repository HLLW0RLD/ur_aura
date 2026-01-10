package com.example.ur_color.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update
import androidx.room.OnConflictStrategy
import com.example.ur_color.data.model.entity.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<PostEntity>)

    @Query("SELECT * FROM posts ORDER BY created_at DESC")
    fun getAllPosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE author_id = :userId ORDER BY created_at DESC")
    fun getPostsByUserId(userId: String): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE id = :postId")
    suspend fun getPostById(postId: String): PostEntity?

    @Delete
    suspend fun delete(post: PostEntity)

    @Query("DELETE FROM posts WHERE id = :postId")
    suspend fun deleteById(postId: String)

    @Query("DELETE FROM posts")
    suspend fun deleteAll()

    @Update
    suspend fun update(post: PostEntity)

    @Query("SELECT * FROM posts WHERE is_synced = 0")
    suspend fun getUnsyncedPosts(): List<PostEntity>

    @Query("UPDATE posts SET is_synced = :isSynced WHERE id = :postId")
    suspend fun updateSyncStatus(postId: String, isSynced: Boolean)
}