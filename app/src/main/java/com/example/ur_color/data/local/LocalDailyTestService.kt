package com.example.ur_color.data.local

import com.example.ur_color.data.model.Mod
import com.example.ur_color.data.model.ModType
import com.example.ur_color.data.model.Question
import com.example.ur_color.data.model.QuestionType

class LocalDailyTestService {

    // опросник в формате свайп-карточек, между вопросами можно будет потом размещать призы/рекламу
    val baseQuestions = listOf(
        Pair(
            listOf(
                Mod(ModType.ENERGY_LEVEL, 0.8),
                Mod(ModType.MOTIVATION, 0.7)
            ),
            listOf(
                "Сегодня чувствуете прилив сил?",
                "Хочется что-то начать?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.ENERGY_LEVEL, -0.7),
                Mod(ModType.PHYSICAL_ENERGY, -0.6)
            ),
            listOf(
                "Чувствуете упадок энергии?",
                "Тело будто не слушается?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.FOCUS, 0.8),
                Mod(ModType.MOTIVATION, 0.5)
            ),
            listOf(
                "Легко сосредоточиться?",
                "Можете удерживать внимание?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.FOCUS, -0.6),
                Mod(ModType.STRESS_LEVEL, 0.7)
            ),
            listOf(
                "Рассеяны и не можете собраться?",
                "В голове каша?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.CREATIVITY, 0.9),
                Mod(ModType.INTUITION_LEVEL, 0.6)
            ),
            listOf(
                "Идеи приходят сами собой?",
                "Чувствуете внутреннее вдохновение?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.CREATIVITY, -0.5),
                Mod(ModType.MOOD, -0.4)
            ),
            listOf(
                "Всё кажется скучным?",
                "Нет желания что-то создавать?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.EMOTIONAL_BALANCE, 0.7),
                Mod(ModType.MOOD, 0.6)
            ),
            listOf(
                "Чувствуете внутреннее спокойствие?",
                "Настроение ровное и комфортное?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.EMOTIONAL_BALANCE, -0.6),
                Mod(ModType.STRESS_LEVEL, 0.6)
            ),
            listOf(
                "Чувствуете напряжение внутри?",
                "Эмоции не под контролем?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.SOCIAL_ENERGY, 0.6),
                Mod(ModType.MOOD, 0.5)
            ),
            listOf(
                "Общение даёт энергию?",
                "Хочется поговорить с кем-то?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.SOCIAL_ENERGY, -0.5),
                Mod(ModType.EMOTIONAL_BALANCE, -0.4)
            ),
            listOf(
                "Общение истощает?",
                "Чувствуете себя одиноко?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.PHYSICAL_ENERGY, 0.7),
                Mod(ModType.SLEEP_QUALITY, 0.6)
            ),
            listOf(
                "Чувствуете бодрость в теле?",
                "Выспались хорошо?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.PHYSICAL_ENERGY, -0.5),
                Mod(ModType.SLEEP_QUALITY, -0.9)
            ),
            listOf(
                "Тело тяжёлое после сна?",
                "Не выспались и чувствуете усталость?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.MOTIVATION, 0.6),
                Mod(ModType.ENERGY_LEVEL, 0.5)
            ),
            listOf(
                "Есть желание действовать?",
                "Чувствуете импульс к делам?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.MOTIVATION, -0.7),
                Mod(ModType.MOOD, -0.6)
            ),
            listOf(
                "Апатия и безразличие?",
                "Ничего не хочется делать?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.STRESS_LEVEL, -0.5),
                Mod(ModType.EMOTIONAL_BALANCE, 0.6)
            ),
            listOf(
                "Чувствуете контроль над стрессом?",
                "Можете успокаиваться?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.STRESS_LEVEL, 0.8),
                Mod(ModType.FOCUS, -0.5)
            ),
            listOf(
                "Чувствуете давление времени?",
                "Стресс мешает мыслить ясно?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.INTUITION_LEVEL, 0.7),
                Mod(ModType.CREATIVITY, 0.4)
            ),
            listOf(
                "Доверяете своим ощущениям?",
                "Чувствуете внутренний компас?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.INTUITION_LEVEL, -0.5),
                Mod(ModType.EMOTIONAL_BALANCE, -0.3)
            ),
            listOf(
                "Сомневаетесь во всём?",
                "Не чувствуете уверенности в выборе?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.SOCIAL_ENERGY, 0.5),
                Mod(ModType.ENERGY_LEVEL, 0.4)
            ),
            listOf(
                "Общение заряжает?",
                "Люди дают жизненную силу?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.SOCIAL_ENERGY, -0.6),
                Mod(ModType.STRESS_LEVEL, 0.5)
            ),
            listOf(
                "Общение вызывает напряжение?",
                "После разговоров чувствуете опустошение?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.MOOD, 0.7),
                Mod(ModType.EMOTIONAL_BALANCE, 0.5)
            ),
            listOf(
                "Настроение хорошее?",
                "Чувствуете лёгкость внутри?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.MOOD, -0.6),
                Mod(ModType.STRESS_LEVEL, 0.4)
            ),
            listOf(
                "Подавленность и тяжесть?",
                "Настроение плохое без причины?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.FOCUS, 0.6),
                Mod(ModType.PHYSICAL_ENERGY, 0.4)
            ),
            listOf(
                "Работаете без усталости?",
                "Можете долго концентрироваться?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.FOCUS, -0.7),
                Mod(ModType.PHYSICAL_ENERGY, -0.5)
            ),
            listOf(
                "Устали уже через минуту?",
                "Концентрация рассыпается?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.CREATIVITY, 0.6),
                Mod(ModType.MOOD, 0.5)
            ),
            listOf(
                "Чувствуете творческий поток?",
                "Идеи рождаются легко?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.CREATIVITY, -0.6),
                Mod(ModType.MOTIVATION, -0.5)
            ),
            listOf(
                "Нет вдохновения?",
                "Творчество не получается?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.SLEEP_QUALITY, 0.8),
                Mod(ModType.ENERGY_LEVEL, 0.6)
            ),
            listOf(
                "Проснулись отдохнувшим?",
                "Чувствуете свежесть ума?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.SLEEP_QUALITY, -0.7),
                Mod(ModType.MOOD, -0.5)
            ),
            listOf(
                "Сон был беспокойным?",
                "Проснулись с тревогой?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.EMOTIONAL_BALANCE, 0.6),
                Mod(ModType.SOCIAL_ENERGY, 0.4)
            ),
            listOf(
                "Чувствуете гармонию с собой?",
                "Комфортно общаться с другими?"
            )
        ),
        Pair(
            listOf(
                Mod(ModType.ENERGY_LEVEL, -0.6),
                Mod(ModType.MOTIVATION, -0.6)
            ),
            listOf(
                "Чувствуете внутреннюю пустоту?",
                "Нет желания ничего начинать?"
            )
        )
    )

    val firstVarTest = baseQuestions.mapIndexed { idx, base ->
        Question(id = "${1+idx}", text = base.second[0], mods = base.first)
    }
    val secondVarTest = baseQuestions.mapIndexed { idx, base ->
        Question(id = "${2+idx}", text = base.second[1], mods = base.first)
    }
}