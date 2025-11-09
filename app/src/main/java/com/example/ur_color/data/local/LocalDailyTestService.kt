package com.example.ur_color.data.local

import com.example.ur_color.data.model.Mod
import com.example.ur_color.data.model.ModFactors
import com.example.ur_color.data.model.ModType
import com.example.ur_color.data.model.Question

class LocalDailyTestService {

    // опросник в формате свайп-карточек, между вопросами можно будет потом размещать призы/рекламу
    val baseQuestions = listOf(
        Pair(
            listOf(
                Mod(ModType.ENERGY_LEVEL, ModFactors.HIGH),
                Mod(ModType.MOTIVATION, ModFactors.HIGH),
                Mod(ModType.PHYSICAL_ENERGY, ModFactors.MEDIUM),
                Mod(ModType.MOOD, ModFactors.LOW)
            ),
            listOf(
                "Сегодня чувствуете прилив сил?",
                "Хочется что-то начать?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.ENERGY_LEVEL, -ModFactors.VERY_HIGH),
                Mod(ModType.PHYSICAL_ENERGY, -ModFactors.HIGH),
                Mod(ModType.SLEEP_QUALITY, -ModFactors.MEDIUM),
                Mod(ModType.MOOD, -ModFactors.LOW)
            ),
            listOf(
                "Чувствуете упадок энергии?",
                "Тело будто не слушается?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.FOCUS, ModFactors.VERY_HIGH),
                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
                Mod(ModType.ENERGY_LEVEL, ModFactors.MEDIUM),
                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.LOW)
            ),
            listOf(
                "Легко сосредоточиться?",
                "Можете удерживать внимание?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.FOCUS, -ModFactors.HIGH),
                Mod(ModType.STRESS_LEVEL, ModFactors.VERY_HIGH),
                Mod(ModType.MOOD, -ModFactors.MEDIUM),
                Mod(ModType.PHYSICAL_ENERGY, -ModFactors.LOW)
            ),
            listOf(
                "Рассеяны и не можете собраться?",
                "В голове каша?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.CREATIVITY, ModFactors.VERY_HIGH),
                Mod(ModType.INTUITION_LEVEL, ModFactors.HIGH),
                Mod(ModType.MOOD, ModFactors.MEDIUM),
                Mod(ModType.ENERGY_LEVEL, ModFactors.LOW)
            ),
            listOf(
                "Идеи приходят сами собой?",
                "Чувствуете внутреннее вдохновение?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.CREATIVITY, -ModFactors.MEDIUM),
                Mod(ModType.MOOD, -ModFactors.MEDIUM),
                Mod(ModType.MOTIVATION, -ModFactors.HIGH),
                Mod(ModType.SOCIAL_ENERGY, -ModFactors.LOW)
            ),
            listOf(
                "Всё кажется скучным?",
                "Нет желания что-то создавать?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.HIGH),
                Mod(ModType.MOOD, ModFactors.MEDIUM),
                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
                Mod(ModType.STRESS_LEVEL, -ModFactors.LOW)
            ),
            listOf(
                "Чувствуете внутреннее спокойствие?",
                "Настроение ровное и комфортное?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.EMOTIONAL_BALANCE, -ModFactors.HIGH),
                Mod(ModType.STRESS_LEVEL, ModFactors.HIGH),
                Mod(ModType.FOCUS, -ModFactors.MEDIUM),
                Mod(ModType.INTUITION_LEVEL, -ModFactors.LOW)
            ),
            listOf(
                "Чувствуете напряжение внутри?",
                "Эмоции не под контролем?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.SOCIAL_ENERGY, ModFactors.HIGH),
                Mod(ModType.MOOD, ModFactors.MEDIUM),
                Mod(ModType.ENERGY_LEVEL, ModFactors.LOW),
                Mod(ModType.MOTIVATION, ModFactors.LOW)
            ),
            listOf(
                "Общение даёт энергию?",
                "Хочется поговорить с кем-то?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.SOCIAL_ENERGY, -ModFactors.HIGH),
                Mod(ModType.EMOTIONAL_BALANCE, -ModFactors.MEDIUM),
                Mod(ModType.MOOD, -ModFactors.LOW),
                Mod(ModType.STRESS_LEVEL, ModFactors.LOW)
            ),
            listOf(
                "Общение истощает?",
                "Чувствуете себя одиноко?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.PHYSICAL_ENERGY, ModFactors.HIGH),
                Mod(ModType.SLEEP_QUALITY, ModFactors.HIGH),
                Mod(ModType.ENERGY_LEVEL, ModFactors.MEDIUM),
                Mod(ModType.MOOD, ModFactors.LOW)
            ),
            listOf(
                "Чувствуете бодрость в теле?",
                "Выспались хорошо?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.PHYSICAL_ENERGY, -ModFactors.MEDIUM),
                Mod(ModType.SLEEP_QUALITY, -ModFactors.VERY_HIGH),
                Mod(ModType.MOTIVATION, -ModFactors.LOW),
                Mod(ModType.STRESS_LEVEL, ModFactors.MEDIUM)
            ),
            listOf(
                "Тело тяжёлое после сна?",
                "Не выспались и чувствуете усталость?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.MOTIVATION, ModFactors.MEDIUM),
                Mod(ModType.ENERGY_LEVEL, ModFactors.MEDIUM),
                Mod(ModType.FOCUS, ModFactors.LOW),
                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.LOW)
            ),
            listOf(
                "Есть желание действовать?",
                "Чувствуете импульс к делам?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.MOTIVATION, -ModFactors.HIGH),
                Mod(ModType.MOOD, -ModFactors.MEDIUM),
                Mod(ModType.SOCIAL_ENERGY, -ModFactors.LOW),
                Mod(ModType.ENERGY_LEVEL, -ModFactors.LOW)
            ),
            listOf(
                "Апатия и безразличие?",
                "Ничего не хочется делать?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.STRESS_LEVEL, -ModFactors.MEDIUM),
                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.MEDIUM),
                Mod(ModType.FOCUS, ModFactors.LOW),
                Mod(ModType.MOOD, ModFactors.LOW)
            ),
            listOf(
                "Чувствуете контроль над стрессом?",
                "Можете успокаиваться?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.STRESS_LEVEL, ModFactors.VERY_HIGH),
                Mod(ModType.FOCUS, -ModFactors.MEDIUM),
                Mod(ModType.MOOD, -ModFactors.LOW),
                Mod(ModType.EMOTIONAL_BALANCE, -ModFactors.LOW)
            ),
            listOf(
                "Чувствуете давление времени?",
                "Стресс мешает мыслить ясно?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.INTUITION_LEVEL, ModFactors.HIGH),
                Mod(ModType.CREATIVITY, ModFactors.MEDIUM),
                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.LOW),
                Mod(ModType.SLEEP_QUALITY, ModFactors.LOW)
            ),
            listOf(
                "Доверяете своим ощущениям?",
                "Чувствуете внутренний компас?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.INTUITION_LEVEL, -ModFactors.MEDIUM),
                Mod(ModType.EMOTIONAL_BALANCE, -ModFactors.LOW),
                Mod(ModType.FOCUS, -ModFactors.LOW),
                Mod(ModType.STRESS_LEVEL, ModFactors.LOW)
            ),
            listOf(
                "Сомневаетесь во всём?",
                "Не чувствуете уверенности в выборе?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.SOCIAL_ENERGY, ModFactors.MEDIUM),
                Mod(ModType.ENERGY_LEVEL, ModFactors.LOW),
                Mod(ModType.MOOD, ModFactors.LOW),
                Mod(ModType.MOTIVATION, ModFactors.LOW)
            ),
            listOf(
                "Общение заряжает?",
                "Люди дают жизненную силу?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.SOCIAL_ENERGY, -ModFactors.HIGH),
                Mod(ModType.STRESS_LEVEL, ModFactors.MEDIUM),
                Mod(ModType.MOOD, -ModFactors.LOW),
                Mod(ModType.ENERGY_LEVEL, -ModFactors.LOW)
            ),
            listOf(
                "Общение вызывает напряжение?",
                "После разговоров чувствуете опустошение?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.MOOD, ModFactors.HIGH),
                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.MEDIUM),
                Mod(ModType.CREATIVITY, ModFactors.LOW),
                Mod(ModType.INTUITION_LEVEL, ModFactors.LOW)
            ),
            listOf(
                "Настроение хорошее?",
                "Чувствуете лёгкость внутри?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.MOOD, -ModFactors.HIGH),
                Mod(ModType.STRESS_LEVEL, ModFactors.MEDIUM),
                Mod(ModType.ENERGY_LEVEL, -ModFactors.LOW),
                Mod(ModType.MOTIVATION, -ModFactors.LOW)
            ),
            listOf(
                "Подавленность и тяжесть?",
                "Настроение плохое без причины?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.FOCUS, ModFactors.MEDIUM),
                Mod(ModType.PHYSICAL_ENERGY, ModFactors.LOW),
                Mod(ModType.ENERGY_LEVEL, ModFactors.LOW),
                Mod(ModType.MOTIVATION, ModFactors.LOW)
            ),
            listOf(
                "Работаете без усталости?",
                "Можете долго концентрироваться?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.FOCUS, -ModFactors.HIGH),
                Mod(ModType.PHYSICAL_ENERGY, -ModFactors.MEDIUM),
                Mod(ModType.STRESS_LEVEL, ModFactors.LOW),
                Mod(ModType.MOOD, -ModFactors.LOW)
            ),
            listOf(
                "Устали уже через минуту?",
                "Концентрация рассыпается?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.CREATIVITY, ModFactors.MEDIUM),
                Mod(ModType.MOOD, ModFactors.LOW),
                Mod(ModType.ENERGY_LEVEL, ModFactors.LOW),
                Mod(ModType.INTUITION_LEVEL, ModFactors.LOW)
            ),
            listOf(
                "Чувствуете творческий поток?",
                "Идеи рождаются легко?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.CREATIVITY, -ModFactors.HIGH),
                Mod(ModType.MOTIVATION, -ModFactors.MEDIUM),
                Mod(ModType.EMOTIONAL_BALANCE, -ModFactors.LOW),
                Mod(ModType.MOOD, -ModFactors.LOW)
            ),
            listOf(
                "Нет вдохновения?",
                "Творчество не получается?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.SLEEP_QUALITY, ModFactors.VERY_HIGH),
                Mod(ModType.ENERGY_LEVEL, ModFactors.HIGH),
                Mod(ModType.PHYSICAL_ENERGY, ModFactors.MEDIUM),
                Mod(ModType.MOOD, ModFactors.LOW)
            ),
            listOf(
                "Проснулись отдохнувшим?",
                "Чувствуете свежесть ума?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.SLEEP_QUALITY, -ModFactors.HIGH),
                Mod(ModType.MOOD, -ModFactors.MEDIUM),
                Mod(ModType.ENERGY_LEVEL, -ModFactors.LOW),
                Mod(ModType.STRESS_LEVEL, ModFactors.LOW)
            ),
            listOf(
                "Сон был беспокойным?",
                "Проснулись с тревогой?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.EMOTIONAL_BALANCE, ModFactors.MEDIUM),
                Mod(ModType.SOCIAL_ENERGY, ModFactors.LOW),
                Mod(ModType.MOOD, ModFactors.LOW),
                Mod(ModType.FOCUS, ModFactors.LOW)
            ),
            listOf(
                "Чувствуете гармонию с собой?",
                "Комфортно общаться с другими?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.ENERGY_LEVEL, -ModFactors.HIGH),
                Mod(ModType.MOTIVATION, -ModFactors.HIGH),
                Mod(ModType.PHYSICAL_ENERGY, -ModFactors.LOW),
                Mod(ModType.MOOD, -ModFactors.LOW)
            ),
            listOf(
                "Чувствуете внутреннюю пустоту?",
                "Нет желания ничего начинать?"
            )
        )
    )

    val firstVarTest = baseQuestions.mapIndexed { idx, base ->
        Question(id = "${1 + idx}", text = base.second[0], mods = base.first)
    }
    val secondVarTest = baseQuestions.mapIndexed { idx, base ->
        Question(id = "${2 + idx}", text = base.second[1], mods = base.first)
    }
}