package com.example.ur_color.data

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
    val zodiacSign: String,   // новый атрибут
    val avatarUri: String? = null
)

fun calculateZodiac(day: Int, month: Int): String {
    return when (month) {
        1 -> if (day < 20) "Козерог" else "Водолей"
        2 -> if (day < 19) "Водолей" else "Рыбы"
        3 -> if (day < 21) "Рыбы" else "Овен"
        4 -> if (day < 20) "Овен" else "Телец"
        5 -> if (day < 21) "Телец" else "Близнецы"
        6 -> if (day < 21) "Близнецы" else "Рак"
        7 -> if (day < 23) "Рак" else "Лев"
        8 -> if (day < 23) "Лев" else "Дева"
        9 -> if (day < 23) "Дева" else "Весы"
        10 -> if (day < 23) "Весы" else "Скорпион"
        11 -> if (day < 22) "Скорпион" else "Стрелец"
        12 -> if (day < 22) "Стрелец" else "Козерог"
        else -> "Неизвестно"
    }
}