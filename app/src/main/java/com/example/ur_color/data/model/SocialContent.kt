package com.example.ur_color.data.model

sealed class SocialContent {
    abstract val id: String

    data class Post(
        override val id: String,
        val title: String,
        val author: String,
        val image: String,
    ) : SocialContent()

    data class User(
        override val id: String,
        val username: String,
        val about: String,
        val avatar: String,
    ) : SocialContent()

    data class Ad(
        override val id: String,
        val title: String,
        val image: String,
        val cta: String,
    ) : SocialContent()
}