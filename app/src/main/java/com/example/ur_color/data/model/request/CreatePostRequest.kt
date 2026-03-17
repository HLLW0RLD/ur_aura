package com.example.ur_color.data.model.request

import com.example.ur_color.utils.getCurrentDateTime

data class CreatePostRequest(
    val id: String = "",
    val text: String,
    val created: String = "",
    val authorId: String,
    val image: String? = null,

)