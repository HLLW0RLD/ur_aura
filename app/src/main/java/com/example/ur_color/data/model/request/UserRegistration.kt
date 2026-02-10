package com.example.ur_color.data.model.request

import com.example.ur_color.data.model.user.CharacteristicData
import kotlinx.serialization.Serializable

@Serializable
data class UserRegistration(
//    val id: String = "",
    val email: String,
    val password: String,
    val nickName: String,
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val about: String? = null,
    val birthDate: String,
    val birthTime: String?,
    val birthPlace: String,
    val gender: String,
    val zodiacSign: String,
    val avatarUri: String? = null,
    val characteristics: CharacteristicData = CharacteristicData(),
    val userLevel: Int = 1,
)