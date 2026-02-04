package com.example.ur_color.data.model.user

import com.example.ur_color.data.model.SocialContent
import com.example.ur_color.data.model.User
import com.example.ur_color.data.model.entity.PostEntity
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.AuraColors
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserData(
    val id: String = UUID.randomUUID().toString(),
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

