package com.example.ur_color.data.model.user

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Achievement(
    val id: String = UUID.randomUUID().toString(),
    val name: String? = "",
    val icon: String? = "",
)
