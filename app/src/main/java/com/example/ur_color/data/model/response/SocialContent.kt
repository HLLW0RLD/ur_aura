package com.example.ur_color.data.model.response

sealed class SocialContent {
    abstract val id: String

    data class Post(
        override val id: String,
        val text: String?,
        val author: User,
        val image: String?,
    ) : SocialContent()

    data class Ad(
        override val id: String,
        val title: String,
        val image: String,
        val cta: String,
    ) : SocialContent()
}

data class User(
    val id: String,
    val username: String,
    val level: Int,
    val about: String?,
    val avatar: String?
)