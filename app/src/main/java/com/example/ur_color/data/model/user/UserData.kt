package com.example.ur_color.data.model.user

import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.AuraColors
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserData(
    val id: String? = UUID.randomUUID().toString(),
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
    val personalityType: String? = null,

    val achievements: List<Achievement> = listOf()
)  {
    val auraSeed: Long = (firstName + lastName + birthPlace + zodiacSign).hashCode().toLong()
}

