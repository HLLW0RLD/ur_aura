package com.example.ur_color.data.model.user

import com.example.ur_color.data.model.request.UserRegistration
import com.example.ur_color.data.model.response.User
import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val id: String = "",
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
    val characteristics: CharacteristicData,
    val userLevel: Int = 1,

//    val achievements: List<Achievement> = listOf()
)  {
    val auraSeed: Long = (firstName + lastName + birthPlace + zodiacSign).hashCode().toLong()
}

fun UserData?.toUser(): User {
    return User(
        id = this?.id ?: "",
        username = "${this?.firstName} ${this?.lastName}",
        level = this?.userLevel ?: 1,
        about = this?.about,
        avatar = this?.avatarUri
    )
}

fun UserRegistration.toUserData() = UserData(
//    id = id,
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
    characteristics = characteristics,
    userLevel = userLevel
)

