package com.example.ur_color.data.model.response

import com.example.ur_color.utils.getCurrentDateTime
import kotlinx.serialization.Serializable

@Serializable
sealed class UserContent {
    abstract val id: String
    abstract val created: String

    @Serializable
    data class Post(
        override val id: String,
        override val created: String = getCurrentDateTime(),
        val text: String?,
        val author: UserModel,
        val image: String? = null,
    ) : UserContent()

}

@Serializable
data class UserModel(
    val id: String,
    val nickName: String,
    val level: Int,
    val about: String?,
    val avatar: String?
)