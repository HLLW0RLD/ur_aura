package com.example.ur_color.data.model.user

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserDataRequest(
    val id: String = UUID.randomUUID().toString(),
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
    val birthTimestamp: Long = 0L,
    val characteristics: CharacteristicData,
    val userLevel: Int = 1,
)

fun UserDataRequest.toUserData() = UserData(
    id = id,
    nickName = nickName,
    firstName = firstName,
    lastName = lastName,
    middleName = middleName,
    about = about,
    birthDate = birthDate,
    birthTime = birthTime,
    birthPlace = birthPlace,
    gender = gender,
    zodiacSign = zodiacSign,
    avatarUri = avatarUri,
    birthTimestamp = birthTimestamp,
    characteristics = characteristics,
    userLevel = userLevel
)
