package com.example.ur_color.data.model.user

import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.AuraColors
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UserData(
    val id: String = UUID.randomUUID().toString(),
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val birthDate: String,
    val birthTime: String?,
    val birthPlace: String,
    val gender: String,
    val zodiacSign: String,
    val avatarUri: String? = null,
    val birthTimestamp: Long = 0L,


    val userLevel: Int = 1,


    val personalityType: String? = null,


    val energyLevel: Int = 5,
    val mood: Int = 5,
    val stressLevel: Int = 5,
    val focus: Int = 5,
    val motivation: Int = 5,
    val creativity: Int = 5,
    val emotionalBalance: Int = 5,
    val physicalEnergy: Int = 5,
    val sleepQuality: Int = 5,
    val intuitionLevel: Int = 5,
    val socialEnergy: Int = 5,
    val dominantColor: String = AuraColors.WHITE.hex,


    val energyCapacity: List<Int> = List(10) { 5 },
    val moodVector: List<Int> = List(10) { 5 },
    val stressVector: List<Int> = List(10) { 5 },
    val focusVector: List<Int> = List(10) { 5 },
    val motivationVector: List<Int> = List(10) { 5 },
    val creativityVector: List<Int> = List(10) { 5 },
    val emotionalBalanceVector: List<Int> = List(10) { 5 },
    val physicalEnergyVector: List<Int> = List(10) { 5 },
    val sleepQualityVector: List<Int> = List(10) { 5 },
    val intuitionVector: List<Int> = List(10) { 5 },
    val socialVector: List<Int> = List(10) { 5 },
    val colorVector: List<String> = List(10) { AuraColors.WHITE.hex }
)  {
    val auraSeed: Long = (firstName + lastName + birthPlace + zodiacSign).hashCode().toLong()
}

enum class ZodiacSign(val value: String, val nameRu: String) {
    ARIES("Aries", "Овен"),
    TAURUS("Taurus", "Телец"),
    GEMINI("Gemini", "Близнецы"),
    CANCER("Cancer", "Рак"),
    LEO("Leo", "Лев"),
    VIRGO("Virgo", "Дева"),
    LIBRA("Libra", "Весы"),
    SCORPIO("Scorpio", "Скорпион"),
    SAGITTARIUS("Sagittarius", "Стрелец"),
    CAPRICORN("Capricorn", "Козерог"),
    AQUARIUS("Aquarius", "Водолей"),
    PISCES("Pisces", "Рыбы");

    companion object {

        fun fromName(name: String): ZodiacSign? =
            entries.find { it.nameRu.equals(name, ignoreCase = true) }

        fun calculateZodiac(day: Int, month: Int): ZodiacSign = when (month) {
            1 -> if (day < 20) CAPRICORN else AQUARIUS
            2 -> if (day < 19) AQUARIUS else PISCES
            3 -> if (day < 21) PISCES else ARIES
            4 -> if (day < 20) ARIES else TAURUS
            5 -> if (day < 21) TAURUS else GEMINI
            6 -> if (day < 21) GEMINI else CANCER
            7 -> if (day < 23) CANCER else LEO
            8 -> if (day < 23) LEO else VIRGO
            9 -> if (day < 23) VIRGO else LIBRA
            10 -> if (day < 23) LIBRA else SCORPIO
            11 -> if (day < 22) SCORPIO else SAGITTARIUS
            12 -> if (day < 22) SAGITTARIUS else CAPRICORN
            else -> CAPRICORN
        }
    }
}
