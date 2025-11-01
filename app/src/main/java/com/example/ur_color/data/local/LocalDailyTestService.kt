package com.example.ur_color.data.local

import com.example.ur_color.data.model.Mod
import com.example.ur_color.data.model.ModType
import com.example.ur_color.data.model.Question
import com.example.ur_color.data.model.QuestionType

class LocalDailyTestService {

    val firstVarTest = listOf(
        Question(
            id = "M11",
            type = QuestionType.HEALTH, // прилив энергии → здоровье/физическое состояние
            text = "Сегодня вы ощущаете прилив энергии для начала новых дел?",
            mods = listOf(
                Mod(ModType.ENERGY_LEVEL, 0.7),
                Mod(ModType.MOTIVATION, 0.6),
                Mod(ModType.CREATIVITY, 0.5)
            )
        ),
        Question(
            id = "M12",
            type = QuestionType.MENTAL, // сосредоточение → ментальное состояние
            text = "Сегодня вам легко сосредоточиться на одной задаче?",
            mods = listOf(
                Mod(ModType.FOCUS, 0.9),
                Mod(ModType.STRESS_LEVEL, -0.4),
                Mod(ModType.INTUITION_LEVEL, 0.3)
            )
        ),
        Question(
            id = "M13",
            type = QuestionType.MOOD, // внутренний баланс → настроение
            text = "Сегодня вы чувствуете внутреннюю гармонию при выполнении своих дел?",
            mods = listOf(
                Mod(ModType.EMOTIONAL_BALANCE, 0.8),
                Mod(ModType.MOOD, 0.4),
                Mod(ModType.FOCUS, 0.3)
            )
        ),
        Question(
            id = "M14",
            type = QuestionType.MENTAL, // творческий процесс → ментальные способности
            text = "Сегодня вы легко погружаетесь в творческий процесс без внешнего давления?",
            mods = listOf(
                Mod(ModType.CREATIVITY, 0.9),
                Mod(ModType.MOTIVATION, 0.5),
                Mod(ModType.SOCIAL_ENERGY, 0.2)
            )
        ),
        Question(
            id = "M15",
            type = QuestionType.HEALTH, // усталость → физическое состояние
            text = "Сегодня выполнение рутинной работы вызывает у вас усталость?",
            mods = listOf(
                Mod(ModType.ENERGY_LEVEL, -0.6),
                Mod(ModType.MOOD, -0.4),
                Mod(ModType.STRESS_LEVEL, 0.5)
            )
        ),
        Question(
            id = "M16",
            type = QuestionType.MENTAL, // доверие интуиции → ментальное
            text = "Сегодня вы готовы полагаться на интуицию при принятии решений?",
            mods = listOf(
                Mod(ModType.INTUITION_LEVEL, 0.8),
                Mod(ModType.FOCUS, 0.3),
                Mod(ModType.EMOTIONAL_BALANCE, 0.2)
            )
        ),
        Question(
            id = "M17",
            type = QuestionType.STRESS, // стресс из-за сроков → настроение/стресс
            text = "Сегодня вы чувствуете стресс из-за необходимости соблюдать сроки?",
            mods = listOf(
                Mod(ModType.STRESS_LEVEL, 0.8),
                Mod(ModType.MOOD, -0.5),
                Mod(ModType.FOCUS, -0.3)
            )
        ),
        Question(
            id = "M18",
            type = QuestionType.SOCIAL, // делиться идеями → социальная энергия
            text = "Сегодня вы готовы делиться своими идеями с окружающими?",
            mods = listOf(
                Mod(ModType.SOCIAL_ENERGY, 0.7),
                Mod(ModType.MOOD, 0.3),
                Mod(ModType.CREATIVITY, 0.2)
            )
        ),
        Question(
            id = "M19",
            type = QuestionType.MOOD, // удовлетворение продуктивностью → настроение
            text = "Сегодня вы удовлетворены своей продуктивностью?",
            mods = listOf(
                Mod(ModType.MOTIVATION, 0.6),
                Mod(ModType.EMOTIONAL_BALANCE, 0.4),
                Mod(ModType.FOCUS, 0.3)
            )
        ),
        Question(
            id = "M20",
            type = QuestionType.HEALTH, // упадок сил → физическое состояние
            text = "Сегодня вы чувствуете упадок сил после умственного напряжения?",
            mods = listOf(
                Mod(ModType.ENERGY_LEVEL, -0.7),
                Mod(ModType.STRESS_LEVEL, 0.5),
                Mod(ModType.SLEEP_QUALITY, -0.4)
            )
        )
    )

    val secondVarTest = listOf(
        Question(
            id = "M31",
            type = QuestionType.HEALTH,
            text = "Сегодня у вас достаточно сил, чтобы браться за новые дела?",
            mods = listOf(
                Mod(ModType.ENERGY_LEVEL, 0.7),
                Mod(ModType.MOTIVATION, 0.6),
                Mod(ModType.CREATIVITY, 0.5)
            )
        ),
        Question(
            id = "M32",
            type = QuestionType.MENTAL,
            text = "Сегодня вам легко концентрироваться на одной задаче?",
            mods = listOf(
                Mod(ModType.FOCUS, 0.9),
                Mod(ModType.STRESS_LEVEL, -0.4),
                Mod(ModType.INTUITION_LEVEL, 0.3)
            )
        ),
        Question(
            id = "M33",
            type = QuestionType.MOOD,
            text = "Сегодня вы ощущаете внутреннее спокойствие при работе?",
            mods = listOf(
                Mod(ModType.EMOTIONAL_BALANCE, 0.8),
                Mod(ModType.MOOD, 0.4),
                Mod(ModType.FOCUS, 0.3)
            )
        ),
        Question(
            id = "M34",
            type = QuestionType.MENTAL,
            text = "Сегодня вы легко погружаетесь в творческое мышление?",
            mods = listOf(
                Mod(ModType.CREATIVITY, 0.9),
                Mod(ModType.MOTIVATION, 0.5),
                Mod(ModType.SOCIAL_ENERGY, 0.2)
            )
        ),
        Question(
            id = "M35",
            type = QuestionType.HEALTH,
            text = "Сегодня обычная работа кажется вам тяжелой или утомительной?",
            mods = listOf(
                Mod(ModType.ENERGY_LEVEL, -0.6),
                Mod(ModType.MOOD, -0.4),
                Mod(ModType.STRESS_LEVEL, 0.5)
            )
        ),
        Question(
            id = "M36",
            type = QuestionType.MENTAL,
            text = "Сегодня вы полагаетесь на свою интуицию в текущих делах?",
            mods = listOf(
                Mod(ModType.INTUITION_LEVEL, 0.8),
                Mod(ModType.FOCUS, 0.3),
                Mod(ModType.EMOTIONAL_BALANCE, 0.2)
            )
        ),
        Question(
            id = "M37",
            type = QuestionType.STRESS,
            text = "Сегодня вас напрягают сроки выполнения задач?",
            mods = listOf(
                Mod(ModType.STRESS_LEVEL, 0.8),
                Mod(ModType.MOOD, -0.5),
                Mod(ModType.FOCUS, -0.3)
            )
        ),
        Question(
            id = "M38",
            type = QuestionType.SOCIAL,
            text = "Сегодня вы готовы обсуждать идеи и делиться мыслями с другими?",
            mods = listOf(
                Mod(ModType.SOCIAL_ENERGY, 0.7),
                Mod(ModType.MOOD, 0.3),
                Mod(ModType.CREATIVITY, 0.2)
            )
        ),
        Question(
            id = "M39",
            type = QuestionType.MOOD,
            text = "Сегодня вы довольны своей эффективностью?",
            mods = listOf(
                Mod(ModType.MOTIVATION, 0.6),
                Mod(ModType.EMOTIONAL_BALANCE, 0.4),
                Mod(ModType.FOCUS, 0.3)
            )
        ),
        Question(
            id = "M40",
            type = QuestionType.HEALTH,
            text = "Сегодня вы чувствуете усталость после интеллектуальной работы?",
            mods = listOf(
                Mod(ModType.ENERGY_LEVEL, -0.7),
                Mod(ModType.STRESS_LEVEL, 0.5),
                Mod(ModType.SLEEP_QUALITY, -0.4)
            )
        )
    )

    val thirdVarTest = listOf(
        Question(
            id = "M21",
            type = QuestionType.HEALTH,
            text = "Сегодня вы чувствуете заряд энергии для начала новых задач?",
            mods = listOf(
                Mod(ModType.ENERGY_LEVEL, 0.7),
                Mod(ModType.MOTIVATION, 0.6),
                Mod(ModType.CREATIVITY, 0.5)
            )
        ),
        Question(
            "M22",
            type = QuestionType.MENTAL,
            text = "Сегодня легко удерживать внимание на одной задаче без отвлечений?",
            mods = listOf(
                Mod(ModType.FOCUS, 0.9),
                Mod(ModType.STRESS_LEVEL, -0.4),
                Mod(ModType.INTUITION_LEVEL, 0.3)
            )
        ),
        Question(
            id = "M23",
            type = QuestionType.MOOD,
            text = "Сегодня вы ощущаете спокойствие и внутренний баланс при работе?",
            mods = listOf(
                Mod(ModType.EMOTIONAL_BALANCE, 0.8),
                Mod(ModType.MOOD, 0.4),
                Mod(ModType.FOCUS, 0.3)
            )
        ),
        Question(
            id = "M24",
            type = QuestionType.MENTAL,
            text = "Сегодня вам легко погружаться в творческий процесс?",
            mods = listOf(
                Mod(ModType.CREATIVITY, 0.9),
                Mod(ModType.MOTIVATION, 0.5),
                Mod(ModType.SOCIAL_ENERGY, 0.2)
            )
        ),
        Question(
            id = "M25",
            type = QuestionType.HEALTH,
            text = "Сегодня выполнение рутинной работы кажется утомительным?",
            mods = listOf(
                Mod(ModType.ENERGY_LEVEL, -0.6),
                Mod(ModType.MOOD, -0.4),
                Mod(ModType.STRESS_LEVEL, 0.5)
            )
        ),
        Question(
            id = "M26",
            type = QuestionType.MENTAL,
            text = "Сегодня вы доверяете своей интуиции при принятии решений?",
            mods = listOf(
                Mod(ModType.INTUITION_LEVEL, 0.8),
                Mod(ModType.FOCUS, 0.3),
                Mod(ModType.EMOTIONAL_BALANCE, 0.2)
            )
        ),
        Question(
            id = "M27",
            type = QuestionType.STRESS,
            text = "Сегодня соблюдение сроков вызывает у вас напряжение?",
            mods = listOf(
                Mod(ModType.STRESS_LEVEL, 0.8),
                Mod(ModType.MOOD, -0.5),
                Mod(ModType.FOCUS, -0.3)
            )
        ),
        Question(
            id = "M28",
            type = QuestionType.SOCIAL,
            text = "Сегодня вы готовы обсуждать свои идеи с коллегами или друзьями?",
            mods = listOf(
                Mod(ModType.SOCIAL_ENERGY, 0.7),
                Mod(ModType.MOOD, 0.3),
                Mod(ModType.CREATIVITY, 0.2)
            )
        ),
        Question(
            id = "M29",
            type = QuestionType.MOOD,
            text = "Сегодня вы довольны тем, как справляетесь с делами?",
            mods = listOf(
                Mod(ModType.MOTIVATION, 0.6),
                Mod(ModType.EMOTIONAL_BALANCE, 0.4),
                Mod(ModType.FOCUS, 0.3)
            )
        ),
        Question(
            id = "M30",
            type = QuestionType.HEALTH,
            text = "Сегодня вы ощущаете усталость после умственной нагрузки?",
            mods = listOf(
                Mod(ModType.ENERGY_LEVEL, -0.7),
                Mod(ModType.STRESS_LEVEL, 0.5),
                Mod(ModType.SLEEP_QUALITY, -0.4)
            )
        )
    )
}