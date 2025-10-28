package com.example.ur_color.data.user

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val birthDate: String,   // ДД/ММ/ГГГГ
    val birthTime: String?,   // ЧЧ:ММ
    val birthPlace: String,
    val gender: String,
    val zodiacSign: String,
    val avatarUri: String? = null,

    val personalityType: String? = null,                                // устанавливается 1 раз
    val element: String? = null,                                        // устанавливается 1 раз
    val energyLevel: Int = 5,                                           // текущее значение, изменяется динамически
    val energyCapacity: List<Int> = listOf(5, 5, 5),                    // максимум 3 последних значения
    val dominantColor: String = "white",                                // пересчитывается динамически
    val colorVector: List<String> = listOf("white", "white", "white"),  // максимум 3 последних значения
    val birthTimestamp: Long = 0L
)  {
    val auraSeed: Long = (firstName + lastName + birthPlace + zodiacSign).hashCode().toLong()
}

fun UserData.updateEnergy(newEnergy: Int): UserData {
    val updatedList = (energyCapacity + newEnergy).takeLast(3)
    return this.copy(energyLevel = newEnergy, energyCapacity = updatedList)
}

fun UserData.updateColor(newColor: String): UserData {
    val updatedList = (colorVector + newColor).takeLast(3)
    return this.copy(dominantColor = newColor, colorVector = updatedList)
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
