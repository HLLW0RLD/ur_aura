package com.example.ur_color.data.local.mocServece

import com.example.ur_color.data.model.question.Mod
import com.example.ur_color.data.model.question.ModCategory
import com.example.ur_color.data.model.question.ModFactors
import com.example.ur_color.data.model.question.ModType
import com.example.ur_color.data.model.question.Question
import com.example.ur_color.utils.questionTemplates

private const val VECTOR_SMOOTH_FACTOR = 0.3f
class LocalDailyTestService {

    // Матрица синергий между модами (какие моды хорошо сочетаются)
    private val synergyMatrix = mapOf(
        ModType.ENERGY_LEVEL to setOf(ModType.PHYSICAL_ENERGY, ModType.MOTIVATION, ModType.FOCUS),
        ModType.MOOD to setOf(ModType.SOCIAL_ENERGY, ModType.COMMUNICATION, ModType.CHARISMA),
        ModType.STRESS to setOf(ModType.ANXIETY, ModType.FATIGUE),
        ModType.FOCUS to setOf(ModType.MOTIVATION, ModType.ENERGY_LEVEL),
        ModType.SOCIAL_ENERGY to setOf(ModType.COMMUNICATION, ModType.CHARISMA, ModType.MOOD),
    )

    // Матрица антагонизмов
    private val antagonismMatrix = mapOf(
        ModType.STRESS to setOf(ModType.MOOD, ModType.FOCUS, ModType.SLEEP_QUALITY),
        ModType.FATIGUE to setOf(ModType.ENERGY_LEVEL, ModType.MOTIVATION, ModType.PHYSICAL_ENERGY),
        ModType.ANXIETY to setOf(ModType.COMMUNICATION, ModType.CHARISMA, ModType.SOCIAL_ENERGY),
    )

    val questionMods = generateBaseQuestions().shuffled()

    fun generateBaseQuestions(): List<Question> {
        return ModType.values().flatMap { main ->
            generateQuestions(main).map { text ->
                Question(
                    mods = generateMods(main),
                    text = text
                )
            }
        }
    }

    fun generateQuestions(main: ModType): List<String> {
        val templates = questionTemplates[main] ?: listOf("Как вы себя чувствуете?")
        return templates.shuffled()
    }

    fun generateMods(main: ModType): List<Mod> {
        return ModType.values().map { modType ->
            Mod(
                targetVariable = modType,
                coef = (autoPriority(modType, main) * signForMod(main, modType)) * VECTOR_SMOOTH_FACTOR,
            )
        }
    }

    fun signForMod(main: ModType, mod: ModType): Float {
        return when (mod.category) {
            ModCategory.NEGATIVE -> {
                // Для негативных модов: если основной тоже негативный - положительный коэффициент,
                // если основной позитивный - отрицательный (уменьшаем негатив)
                if (main.category == ModCategory.NEGATIVE) -1f else 1f
            }

            else -> {
                // Для позитивных модов: если основной негативный - отрицательный коэффициент
                // (чтобы не усиливать позитивное при негативном основном)
                if (main.category == ModCategory.NEGATIVE) 0.5f else -1f
            }
        }
    }

    fun autoPriority(mod: ModType, main: ModType): Float {
        return when {
            mod == main -> ModFactors.HIGH
            mod.category == main.category -> ModFactors.MEDIUM
            else -> ModFactors.LOW
        }
    }
}
