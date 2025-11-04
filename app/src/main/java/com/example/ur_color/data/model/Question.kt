package com.example.ur_color.data.model

data class Question(
    val id: String,
//    val type: QuestionType,
    val text: String,
    val mods: List<Mod> = emptyList()
)

data class Mod(
    val targetVariable: ModType,    // название динамической переменной из UserData
    val factor: Double,            // коэффициент влияния (можно отрицательный)
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
    DOMINANT_COLOR // можно оставить для будущих визуальных метрик
}

enum class QuestionType {
    MENTAL,       // мотивация, креативность, фокус
    MOOD,         // настроение, стресс
    HEALTH,       // энергия, физическое состояние, сон
    SOCIAL,       // социальная энергия, коммуникация
    STRESS,
    GENERAL,       // универсальные вопросы, влияют на несколько областей
}