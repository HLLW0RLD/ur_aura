package com.example.ur_color.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ur_color.data.model.response.SocialContent
import com.example.ur_color.data.model.response.User

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "text")
    val text: String?,
    @ColumnInfo(name = "author_id")
    val authorId: String,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "user_level")
    val userLevel: Int,
    @ColumnInfo(name = "about")
    val about: String?,
    @ColumnInfo(name = "avatar")
    val avatar: String?,
    @ColumnInfo(name = "post_image")
    val postImage: String?,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "is_synced")
    val isSynced: Boolean = false,

)

fun SocialContent.Post.toPostEntity(
    synced: Boolean = false
): PostEntity {
    return PostEntity(
        id = id,
        text = text,
        authorId = author.id,
        username = author.username,
        userLevel = author.level,
        about = author.about,
        avatar = author.avatar,
        postImage = image,
        isSynced = synced
    )
}

fun PostEntity.toPost(): SocialContent.Post {
    return SocialContent.Post(
        id = id,
        text = text,
        author = User(
            id = authorId,
            username = username,
            level = userLevel,
            about = about,
            avatar = avatar
        ),
        image = postImage
    )
}