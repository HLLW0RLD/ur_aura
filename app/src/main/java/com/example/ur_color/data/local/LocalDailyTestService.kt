package com.example.ur_color.data.local

import com.example.ur_color.data.model.Mod
import com.example.ur_color.data.model.ModType
import com.example.ur_color.data.model.Question
import com.example.ur_color.data.model.QuestionType

class LocalDailyTestService {

    val baseQuestions = listOf(
        Pair(
            listOf(
                Mod(ModType.ENERGY_LEVEL, 0.6),
                Mod(ModType.CREATIVITY, 0.5),
                Mod(ModType.EMOTIONAL_BALANCE, 0.4),
                Mod(ModType.PHYSICAL_ENERGY, -0.3),
                Mod(ModType.SLEEP_QUALITY, -0.2),
                Mod(ModType.INTUITION_LEVEL, -0.4)
            ),
            listOf(
                "Сегодня вы ощущаете прилив энергии для начала новых дел?",
                "Сегодня у вас достаточно сил, чтобы браться за новые дела?",
                "Сегодня вы чувствуете заряд энергии для начала новых задач?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.FOCUS, 0.8),
                Mod(ModType.ENERGY_LEVEL, 0.3),
                Mod(ModType.EMOTIONAL_BALANCE, 0.2),
                Mod(ModType.CREATIVITY, -0.3),
                Mod(ModType.SLEEP_QUALITY, -0.4),
                Mod(ModType.PHYSICAL_ENERGY, -0.3)
            ),
            listOf(
                "Сегодня вам легко сосредоточиться на одной задаче?",
                "Сегодня вам легко концентрироваться на одной задаче?",
                "Сегодня легко удерживать внимание на одной задаче без отвлечений?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.EMOTIONAL_BALANCE, 0.7),
                Mod(ModType.INTUITION_LEVEL, 0.4),
                Mod(ModType.CREATIVITY, 0.3),
                Mod(ModType.SLEEP_QUALITY, -0.3),
                Mod(ModType.PHYSICAL_ENERGY, -0.2),
                Mod(ModType.ENERGY_LEVEL, -0.4)
            ),
            listOf(
                "Сегодня вы чувствуете внутреннюю гармонию при выполнении своих дел?",
                "Сегодня вы ощущаете внутреннее спокойствие при работе?",
                "Сегодня вы ощущаете спокойствие и внутренний баланс при работе?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.CREATIVITY, 0.9),
                Mod(ModType.EMOTIONAL_BALANCE, 0.4),
                Mod(ModType.INTUITION_LEVEL, 0.3),
                Mod(ModType.SLEEP_QUALITY, -0.4),
                Mod(ModType.PHYSICAL_ENERGY, -0.3),
                Mod(ModType.ENERGY_LEVEL, -0.2)
            ),
            listOf(
                "Сегодня вы легко погружаетесь в творческий процесс без внешнего давления?",
                "Сегодня вы легко погружаетесь в творческое мышление?",
                "Сегодня вам легко погружаться в творческий процесс?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.ENERGY_LEVEL, -0.6),
                Mod(ModType.SLEEP_QUALITY, -0.5),
                Mod(ModType.PHYSICAL_ENERGY, -0.4),
                Mod(ModType.CREATIVITY, 0.3),
                Mod(ModType.EMOTIONAL_BALANCE, 0.2),
                Mod(ModType.INTUITION_LEVEL, 0.2)
            ),
            listOf(
                "Сегодня выполнение рутинной работы вызывает у вас усталость?",
                "Сегодня обычная работа кажется вам тяжелой или утомительной?",
                "Сегодня выполнение рутинной работы кажется утомительным?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.INTUITION_LEVEL, 0.8),
                Mod(ModType.EMOTIONAL_BALANCE, 0.3),
                Mod(ModType.CREATIVITY, 0.2),
                Mod(ModType.PHYSICAL_ENERGY, -0.2),
                Mod(ModType.SLEEP_QUALITY, -0.3),
                Mod(ModType.ENERGY_LEVEL, -0.3)
            ),
            listOf(
                "Сегодня вы готовы полагаться на интуицию при принятии решений?",
                "Сегодня вы полагаетесь на свою интуицию в текущих делах?",
                "Сегодня вы доверяете своей интуиции при принятии решений?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.EMOTIONAL_BALANCE, -0.6),
                Mod(ModType.SLEEP_QUALITY, -0.4),
                Mod(ModType.ENERGY_LEVEL, -0.3),
                Mod(ModType.PHYSICAL_ENERGY, 0.3),
                Mod(ModType.CREATIVITY, 0.2),
                Mod(ModType.INTUITION_LEVEL, 0.4)
            ),
            listOf(
                "Сегодня вы чувствуете стресс из-за необходимости соблюдать сроки?",
                "Сегодня вас напрягают сроки выполнения задач?",
                "Сегодня соблюдение сроков вызывает у вас напряжение?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.CREATIVITY, 0.4),
                Mod(ModType.PHYSICAL_ENERGY, 0.3),
                Mod(ModType.EMOTIONAL_BALANCE, 0.2),
                Mod(ModType.SLEEP_QUALITY, -0.2),
                Mod(ModType.ENERGY_LEVEL, -0.3),
                Mod(ModType.INTUITION_LEVEL, -0.4)
            ),
            listOf(
                "Сегодня вы готовы делиться своими идеями с окружающими?",
                "Сегодня вы готовы обсуждать идеи и делиться мыслями с другими?",
                "Сегодня вы готовы обсуждать свои идеи с коллегами или друзьями?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.EMOTIONAL_BALANCE, 0.5),
                Mod(ModType.CREATIVITY, 0.4),
                Mod(ModType.ENERGY_LEVEL, 0.3),
                Mod(ModType.PHYSICAL_ENERGY, -0.3),
                Mod(ModType.SLEEP_QUALITY, -0.3),
                Mod(ModType.INTUITION_LEVEL, -0.2)
            ),
            listOf(
                "Сегодня вы удовлетворены своей продуктивностью?",
                "Сегодня вы довольны своей эффективностью?",
                "Сегодня вы довольны тем, как справляетесь с делами?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.ENERGY_LEVEL, -0.7),
                Mod(ModType.SLEEP_QUALITY, -0.5),
                Mod(ModType.PHYSICAL_ENERGY, -0.4),
                Mod(ModType.EMOTIONAL_BALANCE, 0.3),
                Mod(ModType.CREATIVITY, 0.2),
                Mod(ModType.INTUITION_LEVEL, 0.1)
            ),
            listOf(
                "Сегодня вы чувствуете упадок сил после умственного напряжения?",
                "Сегодня вы чувствуете усталость после интеллектуальной работы?",
                "Сегодня вы ощущаете усталость после умственной нагрузки?"
            )
        )
    )

    val firstVarTest = baseQuestions.mapIndexed { idx, base ->
        Question(id = "${1+idx}", text = base.second[0], mods = base.first)
    }
    val secondVarTest = baseQuestions.mapIndexed { idx, base ->
        Question(id = "${2+idx}", text = base.second[1], mods = base.first)
    }
    val thirdVarTest = baseQuestions.mapIndexed { idx, base ->
        Question(id = "${3+idx}", text = base.second[2], mods = base.first)
    }
}