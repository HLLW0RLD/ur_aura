package com.example.ur_color.data.local.mocServece

import com.example.ur_color.data.model.Mod
import com.example.ur_color.data.model.ModCategory
import com.example.ur_color.data.model.ModFactors
import com.example.ur_color.data.model.ModType
import com.example.ur_color.data.model.Question

class LocalDailyTestService {

    val questionTemplates = mapOf(
        ModCategory.PHYSICAL to listOf(
            "Сегодня чувствуете прилив физической энергии?",
            "Как оцениваете качество сна сегодня?",
            "Чувствуете ли вы усталость в теле?"
        ),
        ModCategory.EMOTIONAL to listOf(
            "Сегодня настроение на высоте?",
            "Хочется что-то творческое или начать новые дела?",
            "Как легко сосредоточиться на задачах?"
        ),
        ModCategory.SOCIAL to listOf(
            "Чувствуете желание общаться с людьми?",
            "Легко ли вам взаимодействовать с командой?",
            "Как комфортно вы себя чувствуете в компании других?"
        ),
        ModCategory.NEGATIVE to listOf(
            "Чувствуете стресс или тревогу сегодня?",
            "Есть ли ощущение усталости или апатии?",
            "Наблюдаются ли негативные эмоции?"
        )
    )

    val questionMods = generateBaseQuestions()

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
        val templates = questionTemplates[main.category] ?: listOf("Как вы себя чувствуете?")
        return templates.shuffled()
    }

    fun generateMods(main: ModType, useVector: Boolean = false): List<Mod> {
        return ModType.values().map { modType ->
            Mod(
                targetVariable = modType,
                coef = autoPriority(modType, main) * signForMod(main, modType),
                useVector = useVector
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


//    val firstVarTest = baseQuestions.mapIndexed { idx, base ->
//        Question(id = "${1 + idx}", text = base.second[0], mods = base.first)
//    }
//    val secondVarTest = baseQuestions.mapIndexed { idx, base ->
//        Question(id = "${2 + idx}", text = base.second[1], mods = base.first)
//    }
//
//        val baseQuestions = listOf(
////        MOOD
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.VERY_HIGH),
//                Mod(ModType.STRESS_LEVEL, ModFactors.HIGH),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.HIGH),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.MEDIUM),
//
//                Mod(ModType.FOCUS, ModFactors.LOW),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.LOW),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.LOW),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.LOW),
//                Mod(ModType.CREATIVITY, ModFactors.LOW),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.LOW),
//            ),
//            listOf(
//                "Сегодня чувствуете прилив сил?",
//                "Хочется что-то начать?"
//            )
//        ),
////        STRESS_LEVEL
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.HIGH),
//                Mod(ModType.STRESS_LEVEL, ModFactors.VERY_HIGH),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.HIGH),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.MEDIUM),
//
//                Mod(ModType.FOCUS, ModFactors.LOW),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.LOW),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.LOW),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.LOW),
//                Mod(ModType.CREATIVITY, ModFactors.LOW),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.LOW),
//            ),
//            listOf(
//                "Чувствуете упадок энергии?",
//                "Тело будто не слушается?"
//            )
//        ),
////        ENERGY_LEVEL
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.HIGH),
//                Mod(ModType.STRESS_LEVEL, ModFactors.HIGH),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.VERY_HIGH),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.MEDIUM),
//
//                Mod(ModType.FOCUS, ModFactors.LOW),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.LOW),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.LOW),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.LOW),
//                Mod(ModType.CREATIVITY, ModFactors.LOW),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.LOW),
//            ),
//            listOf(
//                "Сегодня чувствуете прилив сил?",
//                "Хочется что-то начать?"
//            )
//        ),
////        SOCIAL_ENERGY
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.MEDIUM),
//                Mod(ModType.STRESS_LEVEL, ModFactors.MEDIUM),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.MEDIUM),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.VERY_HIGH),
//                Mod(ModType.MOTIVATION, ModFactors.HIGH),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.HIGH),
//
//                Mod(ModType.FOCUS, ModFactors.MEDIUM),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.MEDIUM),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.LOW),
//                Mod(ModType.CREATIVITY, ModFactors.LOW),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.LOW),
//            ),
//            listOf(
//                "Чувствуете упадок энергии?",
//                "Тело будто не слушается?"
//            )
//        ),
////        MOTIVATION
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.MEDIUM),
//                Mod(ModType.STRESS_LEVEL, ModFactors.MEDIUM),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.MEDIUM),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.HIGH),
//                Mod(ModType.MOTIVATION, ModFactors.VERY_HIGH),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.HIGH),
//
//                Mod(ModType.FOCUS, ModFactors.MEDIUM),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.MEDIUM),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.LOW),
//                Mod(ModType.CREATIVITY, ModFactors.LOW),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.LOW),
//            ),
//            listOf(
//                "Сегодня чувствуете прилив сил?",
//                "Хочется что-то начать?"
//            )
//        ),
////        EMOTIONAL_BALANCE
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.MEDIUM),
//                Mod(ModType.STRESS_LEVEL, ModFactors.MEDIUM),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.MEDIUM),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.HIGH),
//                Mod(ModType.MOTIVATION, ModFactors.HIGH),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.VERY_HIGH),
//
//                Mod(ModType.FOCUS, ModFactors.MEDIUM),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.MEDIUM),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.LOW),
//                Mod(ModType.CREATIVITY, ModFactors.LOW),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.LOW),
//            ),
//            listOf(
//                "Чувствуете упадок энергии?",
//                "Тело будто не слушается?"
//            )
//        ),
////        FOCUS
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.LOW),
//                Mod(ModType.STRESS_LEVEL, ModFactors.LOW),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.LOW),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.MEDIUM),
//
//                Mod(ModType.FOCUS, ModFactors.VERY_HIGH),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.HIGH),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.HIGH),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.MEDIUM),
//                Mod(ModType.CREATIVITY, ModFactors.MEDIUM),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.MEDIUM),
//            ),
//            listOf(
//                "Сегодня чувствуете прилив сил?",
//                "Хочется что-то начать?"
//            )
//        ),
////        PHYSICAL_ENERGY
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.LOW),
//                Mod(ModType.STRESS_LEVEL, ModFactors.LOW),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.LOW),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.MEDIUM),
//
//                Mod(ModType.FOCUS, ModFactors.HIGH),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.VERY_HIGH),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.HIGH),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.MEDIUM),
//                Mod(ModType.CREATIVITY, ModFactors.MEDIUM),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.MEDIUM),
//            ),
//            listOf(
//                "Чувствуете упадок энергии?",
//                "Тело будто не слушается?"
//            )
//        ),
////        SLEEP_QUALITY
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.HIGH),
//                Mod(ModType.STRESS_LEVEL, ModFactors.LOW),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.HIGH),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.MEDIUM),
//
//                Mod(ModType.FOCUS, ModFactors.HIGH),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.HIGH),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.VERY_HIGH),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.MEDIUM),
//                Mod(ModType.CREATIVITY, ModFactors.MEDIUM),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.MEDIUM),
//            ),
//            listOf(
//                "Сегодня чувствуете прилив сил?",
//                "Хочется что-то начать?"
//            )
//        ),
////        INTUITION_LEVEL
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.LOW),
//                Mod(ModType.STRESS_LEVEL, ModFactors.MEDIUM),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.LOW),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.MEDIUM),
//
//                Mod(ModType.FOCUS, ModFactors.LOW),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.LOW),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.LOW),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.VERY_HIGH),
//                Mod(ModType.CREATIVITY, ModFactors.HIGH),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.HIGH),
//            ),
//            listOf(
//                "Чувствуете упадок энергии?",
//                "Тело будто не слушается?"
//            )
//        ),
////        CREATIVITY
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.LOW),
//                Mod(ModType.STRESS_LEVEL, ModFactors.LOW),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.LOW),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.LOW),
//
//                Mod(ModType.FOCUS, ModFactors.MEDIUM),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.MEDIUM),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.HIGH),
//                Mod(ModType.CREATIVITY, ModFactors.VERY_HIGH),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.HIGH),
//            ),
//            listOf(
//                "Сегодня чувствуете прилив сил?",
//                "Хочется что-то начать?"
//            )
//        ),
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.VERY_HIGH),
//                Mod(ModType.STRESS_LEVEL, ModFactors.HIGH),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.HIGH),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.MEDIUM),
//
//                Mod(ModType.FOCUS, ModFactors.LOW),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.LOW),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.LOW),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.LOW),
//                Mod(ModType.CREATIVITY, ModFactors.LOW),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.LOW),
//            ),
//            listOf(
//                "Сегодня чувствуете прилив сил?",
//                "Хочется что-то начать?"
//            )
//        ),
////        STRESS_LEVEL
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.HIGH),
//                Mod(ModType.STRESS_LEVEL, ModFactors.VERY_HIGH),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.HIGH),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.MEDIUM),
//
//                Mod(ModType.FOCUS, ModFactors.LOW),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.LOW),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.LOW),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.LOW),
//                Mod(ModType.CREATIVITY, ModFactors.LOW),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.LOW),
//            ),
//            listOf(
//                "Чувствуете упадок энергии?",
//                "Тело будто не слушается?"
//            )
//        ),
////        ENERGY_LEVEL
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.HIGH),
//                Mod(ModType.STRESS_LEVEL, ModFactors.HIGH),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.VERY_HIGH),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.MEDIUM),
//
//                Mod(ModType.FOCUS, ModFactors.LOW),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.LOW),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.LOW),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.LOW),
//                Mod(ModType.CREATIVITY, ModFactors.LOW),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.LOW),
//            ),
//            listOf(
//                "Сегодня чувствуете прилив сил?",
//                "Хочется что-то начать?"
//            )
//        ),
////        SOCIAL_ENERGY
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.MEDIUM),
//                Mod(ModType.STRESS_LEVEL, ModFactors.MEDIUM),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.MEDIUM),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.VERY_HIGH),
//                Mod(ModType.MOTIVATION, ModFactors.HIGH),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.HIGH),
//
//                Mod(ModType.FOCUS, ModFactors.MEDIUM),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.MEDIUM),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.LOW),
//                Mod(ModType.CREATIVITY, ModFactors.LOW),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.LOW),
//            ),
//            listOf(
//                "Чувствуете упадок энергии?",
//                "Тело будто не слушается?"
//            )
//        ),
////        MOTIVATION
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.MEDIUM),
//                Mod(ModType.STRESS_LEVEL, ModFactors.MEDIUM),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.MEDIUM),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.HIGH),
//                Mod(ModType.MOTIVATION, ModFactors.VERY_HIGH),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.HIGH),
//
//                Mod(ModType.FOCUS, ModFactors.MEDIUM),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.MEDIUM),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.LOW),
//                Mod(ModType.CREATIVITY, ModFactors.LOW),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.LOW),
//            ),
//            listOf(
//                "Сегодня чувствуете прилив сил?",
//                "Хочется что-то начать?"
//            )
//        ),
////        EMOTIONAL_BALANCE
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.MEDIUM),
//                Mod(ModType.STRESS_LEVEL, ModFactors.MEDIUM),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.MEDIUM),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.HIGH),
//                Mod(ModType.MOTIVATION, ModFactors.HIGH),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.VERY_HIGH),
//
//                Mod(ModType.FOCUS, ModFactors.MEDIUM),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.MEDIUM),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.LOW),
//                Mod(ModType.CREATIVITY, ModFactors.LOW),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.LOW),
//            ),
//            listOf(
//                "Чувствуете упадок энергии?",
//                "Тело будто не слушается?"
//            )
//        ),
////        FOCUS
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.LOW),
//                Mod(ModType.STRESS_LEVEL, ModFactors.LOW),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.LOW),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.MEDIUM),
//
//                Mod(ModType.FOCUS, ModFactors.VERY_HIGH),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.HIGH),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.HIGH),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.MEDIUM),
//                Mod(ModType.CREATIVITY, ModFactors.MEDIUM),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.MEDIUM),
//            ),
//            listOf(
//                "Сегодня чувствуете прилив сил?",
//                "Хочется что-то начать?"
//            )
//        ),
////        PHYSICAL_ENERGY
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.LOW),
//                Mod(ModType.STRESS_LEVEL, ModFactors.LOW),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.LOW),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.MEDIUM),
//
//                Mod(ModType.FOCUS, ModFactors.HIGH),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.VERY_HIGH),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.HIGH),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.MEDIUM),
//                Mod(ModType.CREATIVITY, ModFactors.MEDIUM),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.MEDIUM),
//            ),
//            listOf(
//                "Чувствуете упадок энергии?",
//                "Тело будто не слушается?"
//            )
//        ),
////        SLEEP_QUALITY
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.HIGH),
//                Mod(ModType.STRESS_LEVEL, ModFactors.LOW),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.HIGH),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.MEDIUM),
//
//                Mod(ModType.FOCUS, ModFactors.HIGH),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.HIGH),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.VERY_HIGH),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.MEDIUM),
//                Mod(ModType.CREATIVITY, ModFactors.MEDIUM),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.MEDIUM),
//            ),
//            listOf(
//                "Сегодня чувствуете прилив сил?",
//                "Хочется что-то начать?"
//            )
//        ),
////        INTUITION_LEVEL
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.LOW),
//                Mod(ModType.STRESS_LEVEL, ModFactors.MEDIUM),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.LOW),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.MEDIUM),
//
//                Mod(ModType.FOCUS, ModFactors.LOW),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.LOW),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.LOW),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.VERY_HIGH),
//                Mod(ModType.CREATIVITY, ModFactors.HIGH),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.HIGH),
//            ),
//            listOf(
//                "Чувствуете упадок энергии?",
//                "Тело будто не слушается?"
//            )
//        ),
////        CREATIVITY
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.LOW),
//                Mod(ModType.STRESS_LEVEL, ModFactors.LOW),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.LOW),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.LOW),
//
//                Mod(ModType.FOCUS, ModFactors.MEDIUM),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.MEDIUM),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.HIGH),
//                Mod(ModType.CREATIVITY, ModFactors.VERY_HIGH),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.HIGH),
//            ),
//            listOf(
//                "Сегодня чувствуете прилив сил?",
//                "Хочется что-то начать?"
//            )
//        ),
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.VERY_HIGH),
//                Mod(ModType.STRESS_LEVEL, ModFactors.HIGH),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.HIGH),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.MEDIUM),
//
//                Mod(ModType.FOCUS, ModFactors.LOW),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.LOW),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.LOW),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.LOW),
//                Mod(ModType.CREATIVITY, ModFactors.LOW),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.LOW),
//            ),
//            listOf(
//                "Сегодня чувствуете прилив сил?",
//                "Хочется что-то начать?"
//            )
//        ),
////        STRESS_LEVEL
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.HIGH),
//                Mod(ModType.STRESS_LEVEL, ModFactors.VERY_HIGH),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.HIGH),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.MEDIUM),
//
//                Mod(ModType.FOCUS, ModFactors.LOW),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.LOW),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.LOW),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.LOW),
//                Mod(ModType.CREATIVITY, ModFactors.LOW),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.LOW),
//            ),
//            listOf(
//                "Чувствуете упадок энергии?",
//                "Тело будто не слушается?"
//            )
//        ),
////        ENERGY_LEVEL
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.HIGH),
//                Mod(ModType.STRESS_LEVEL, ModFactors.HIGH),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.VERY_HIGH),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.MEDIUM),
//
//                Mod(ModType.FOCUS, ModFactors.LOW),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.LOW),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.LOW),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.LOW),
//                Mod(ModType.CREATIVITY, ModFactors.LOW),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.LOW),
//            ),
//            listOf(
//                "Сегодня чувствуете прилив сил?",
//                "Хочется что-то начать?"
//            )
//        ),
////        SOCIAL_ENERGY
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.MEDIUM),
//                Mod(ModType.STRESS_LEVEL, ModFactors.MEDIUM),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.MEDIUM),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.VERY_HIGH),
//                Mod(ModType.MOTIVATION, ModFactors.HIGH),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.HIGH),
//
//                Mod(ModType.FOCUS, ModFactors.MEDIUM),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.MEDIUM),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.LOW),
//                Mod(ModType.CREATIVITY, ModFactors.LOW),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.LOW),
//            ),
//            listOf(
//                "Чувствуете упадок энергии?",
//                "Тело будто не слушается?"
//            )
//        ),
////        MOTIVATION
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.MEDIUM),
//                Mod(ModType.STRESS_LEVEL, ModFactors.MEDIUM),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.MEDIUM),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.HIGH),
//                Mod(ModType.MOTIVATION, ModFactors.VERY_HIGH),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.HIGH),
//
//                Mod(ModType.FOCUS, ModFactors.MEDIUM),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.MEDIUM),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.LOW),
//                Mod(ModType.CREATIVITY, ModFactors.LOW),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.LOW),
//            ),
//            listOf(
//                "Сегодня чувствуете прилив сил?",
//                "Хочется что-то начать?"
//            )
//        ),
////        EMOTIONAL_BALANCE
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.MEDIUM),
//                Mod(ModType.STRESS_LEVEL, ModFactors.MEDIUM),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.MEDIUM),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.HIGH),
//                Mod(ModType.MOTIVATION, ModFactors.HIGH),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.VERY_HIGH),
//
//                Mod(ModType.FOCUS, ModFactors.MEDIUM),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.MEDIUM),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.LOW),
//                Mod(ModType.CREATIVITY, ModFactors.LOW),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.LOW),
//            ),
//            listOf(
//                "Чувствуете упадок энергии?",
//                "Тело будто не слушается?"
//            )
//        ),
////        FOCUS
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.LOW),
//                Mod(ModType.STRESS_LEVEL, ModFactors.LOW),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.LOW),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.MEDIUM),
//
//                Mod(ModType.FOCUS, ModFactors.VERY_HIGH),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.HIGH),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.HIGH),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.MEDIUM),
//                Mod(ModType.CREATIVITY, ModFactors.MEDIUM),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.MEDIUM),
//            ),
//            listOf(
//                "Сегодня чувствуете прилив сил?",
//                "Хочется что-то начать?"
//            )
//        ),
////        PHYSICAL_ENERGY
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.LOW),
//                Mod(ModType.STRESS_LEVEL, ModFactors.LOW),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.LOW),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.MEDIUM),
//
//                Mod(ModType.FOCUS, ModFactors.HIGH),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.VERY_HIGH),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.HIGH),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.MEDIUM),
//                Mod(ModType.CREATIVITY, ModFactors.MEDIUM),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.MEDIUM),
//            ),
//            listOf(
//                "Чувствуете упадок энергии?",
//                "Тело будто не слушается?"
//            )
//        ),
////        SLEEP_QUALITY
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.HIGH),
//                Mod(ModType.STRESS_LEVEL, ModFactors.LOW),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.HIGH),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.MEDIUM),
//
//                Mod(ModType.FOCUS, ModFactors.HIGH),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.HIGH),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.VERY_HIGH),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.MEDIUM),
//                Mod(ModType.CREATIVITY, ModFactors.MEDIUM),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.MEDIUM),
//            ),
//            listOf(
//                "Сегодня чувствуете прилив сил?",
//                "Хочется что-то начать?"
//            )
//        ),
////        INTUITION_LEVEL
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.LOW),
//                Mod(ModType.STRESS_LEVEL, ModFactors.MEDIUM),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.LOW),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.MEDIUM),
//
//                Mod(ModType.FOCUS, ModFactors.LOW),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.LOW),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.LOW),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.VERY_HIGH),
//                Mod(ModType.CREATIVITY, ModFactors.HIGH),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.HIGH),
//            ),
//            listOf(
//                "Чувствуете упадок энергии?",
//                "Тело будто не слушается?"
//            )
//        ),
////        CREATIVITY
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.LOW),
//                Mod(ModType.STRESS_LEVEL, ModFactors.LOW),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.LOW),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.LOW),
//
//                Mod(ModType.FOCUS, ModFactors.MEDIUM),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.MEDIUM),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.MEDIUM),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.HIGH),
//                Mod(ModType.CREATIVITY, ModFactors.VERY_HIGH),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.HIGH),
//            ),
//            listOf(
//                "Сегодня чувствуете прилив сил?",
//                "Хочется что-то начать?"
//            )
//        ),
////        DOMINANT_COLOR
//        Pair(
//            listOf(
//                Mod(ModType.MOOD, ModFactors.LOW),
//                Mod(ModType.STRESS_LEVEL, ModFactors.LOW),
//                Mod(ModType.ENERGY_LEVEL, ModFactors.LOW),
//
//                Mod(ModType.SOCIAL_ENERGY, ModFactors.LOW),
//                Mod(ModType.MOTIVATION, ModFactors.LOW),
//                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.LOW),
//
//                Mod(ModType.FOCUS, ModFactors.LOW),
//                Mod(ModType.PHYSICAL_ENERGY, ModFactors.LOW),
//                Mod(ModType.SLEEP_QUALITY, ModFactors.LOW),
//
//                Mod(ModType.INTUITION_LEVEL, ModFactors.LOW),
//                Mod(ModType.CREATIVITY, ModFactors.LOW),
//                Mod(ModType.DOMINANT_COLOR, ModFactors.VERY_HIGH),
//            ),
//            listOf(
//                "Чувствуете упадок энергии?",
//                "Тело будто не слушается?"
//            )
//        ),
//    )
