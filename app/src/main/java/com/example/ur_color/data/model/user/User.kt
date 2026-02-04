package com.example.ur_color.data.model.user

import java.util.UUID

data class User(
    val id: String = UUID.randomUUID().toString(),
    val nickName: String,
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val about: String? = null,
    val age: String,
    val avatarUri: String? = null,
    val birthTimestamp: Long = 0L,
    val userLevel: Int = 1,
)