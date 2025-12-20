package com.example.ur_color.data.model.user

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