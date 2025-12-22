package com.example.ur_color.data.model

import java.util.UUID

data class Question(
    val id: String? = UUID.randomUUID().toString(),
    val text: String,
    val mods: List<Mod> = emptyList()
)

data class Mod(
    val targetVariable: ModType,    // название динамической переменной из UserData
    val coef: Float,            // коэффициент влияния (можно отрицательный)
    val useVector: Boolean = true,  // учитывать ли предыдущие значения в векторе
)

enum class ModCategory {
    PHYSICAL,
    EMOTIONAL,
    SOCIAL,
    NEGATIVE
}

enum class ModType(val category: ModCategory) {
    // PHYSICAL
    ENERGY_LEVEL(ModCategory.PHYSICAL),
    PHYSICAL_ENERGY(ModCategory.PHYSICAL),
    SLEEP_QUALITY(ModCategory.PHYSICAL),

    // EMOTIONAL
    MOOD(ModCategory.EMOTIONAL),
    MOTIVATION(ModCategory.EMOTIONAL),
    FOCUS(ModCategory.EMOTIONAL),

    // SOCIAL
    CHARISMA(ModCategory.SOCIAL),
    SOCIAL_ENERGY(ModCategory.SOCIAL),
    COMMUNICATION(ModCategory.SOCIAL),

    // NEGATIVE
    STRESS(ModCategory.NEGATIVE),
    ANXIETY(ModCategory.NEGATIVE),
    FATIGUE(ModCategory.NEGATIVE)
}

object ModFactors {
    const val HIGH = 0.8f
    const val MEDIUM = 0.55f
    const val LOW = 0.3f
}