package com.example.ur_color.data.local.mocServece

import com.example.ur_color.data.model.Mod
import com.example.ur_color.data.model.ModCategory
import com.example.ur_color.data.model.ModFactors
import com.example.ur_color.data.model.ModType
import com.example.ur_color.data.model.Question
import com.example.ur_color.utils.questionTemplates

private const val VECTOR_SMOOTH_FACTOR = 0.2f
class LocalDailyTestService {

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
            ModCategory.NEGATIVE -> if (main.category == ModCategory.NEGATIVE) 1f else -1f
            else -> 1f
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
