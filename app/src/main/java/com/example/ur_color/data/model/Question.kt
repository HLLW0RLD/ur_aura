package com.example.ur_color.data.model

data class Question(
    val id: String,
    val text: String,
    val mods: List<Mod> = emptyList()
)

data class Mod(
    val targetVariable: ModType,    // название динамической переменной из UserData
    val factor: Float,            // коэффициент влияния (можно отрицательный)
    val useVector: Boolean = true,  // учитывать ли предыдущие значения в векторе
)

enum class ModType {
    ENERGY_LEVEL,
    MOOD,
    STRESS_LEVEL,
    FOCUS,
    MOTIVATION,
    CREATIVITY,
    EMOTIONAL_BALANCE,
    PHYSICAL_ENERGY,
    SLEEP_QUALITY,
    INTUITION_LEVEL,
    SOCIAL_ENERGY,
    DOMINANT_COLOR // для этого тоже нужно вставлять моды
}

object ModFactors {
    const val VERY_HIGH = 0.9f
    const val HIGH = 0.8f
    const val MEDIUM = 0.7f
    const val LOW = 0.6f
    const val VERY_LOW = 0.5f
}