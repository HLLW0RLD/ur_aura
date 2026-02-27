package com.example.ur_color.utils

import com.example.ur_color.R
import com.example.ur_color.data.model.response.AuraItem
import com.example.ur_color.data.model.response.AuraItemType
import com.example.ur_color.data.model.response.AuraRowConfig
import com.example.ur_color.data.model.response.AuraSection
import com.example.ur_color.data.model.response.UserContent
import com.example.ur_color.data.model.response.UserModel
import com.example.ur_color.data.model.question.ModType
import com.example.ur_color.ui.theme.AuraColors

val animPic = listOf(
    R.drawable.illusion,
    R.drawable.magic_sparkles,
    R.drawable.magic_potion,
    R.drawable.card_trick,
    R.drawable.cauldron_potion,
    R.drawable.magic_stick_sparckles,
    R.drawable.wizard_hat,
    R.drawable.ball_crystal,
    R.drawable.candle,
    R.drawable.witch_hat,
    R.drawable.magic_hat,
)

val questionTemplates: Map<ModType, List<String>> = mapOf(

    // ---------- PHYSICAL ----------
    ModType.ENERGY_LEVEL to listOf(
        "Был ли у вас ощущение внутреннего подъёма в течение дня?"
    ),

    ModType.PHYSICAL_ENERGY to listOf(
        "Чувствовали ли вы лёгкость и готовность к физической активности?"
    ),

    ModType.SLEEP_QUALITY to listOf(
        "Проснулись ли вы с ощущением, что сон восстановил вас?"
    ),

    // ---------- EMOTIONAL ----------
    ModType.MOOD to listOf(
        "Были ли моменты, когда вы чувствовали внутреннюю гармонию?"
    ),

    ModType.MOTIVATION to listOf(
        "Возникало ли у вас естественное стремление двигаться вперёд?"
    ),

    ModType.FOCUS to listOf(
        "Удавалось ли вам оставаться в потоке, не отвлекаясь на постороннее?"
    ),

    // ---------- SOCIAL ----------
    ModType.CHARISMA to listOf(
        "Чувствовали ли вы, что легко привлекаете внимание других, не прилагая усилий?"
    ),

    ModType.SOCIAL_ENERGY to listOf(
        "Давало ли вам общение ощущение прилива сил, а не утечки?"
    ),

    ModType.COMMUNICATION to listOf(
        "Были ли ситуации, в которых вас понимали с полуслова?"
    ),

    // ---------- NEGATIVE ----------
    ModType.STRESS to listOf(
        "Ощущали ли вы внутреннее напряжение, которое мешало быть собой?"
    ),

    ModType.ANXIETY to listOf(
        "Возникало ли чувство, будто что-то неприятное вот-вот произойдёт?"
    ),

    ModType.FATIGUE to listOf(
        "Чувствовали ли вы тяжесть в теле или разуме, даже после отдыха?"
    )
)

val demoUsers = listOf(
    UserModel(
        id = "0",
        nickName = "bobo",
        level = 777,
        about = "ауры это круто!",
        avatar = "https://picsum.photos/seed/abstract02/600/600"
    ),
    UserModel(
        id = "1",
        nickName = "Анастасия",
        level = 2,
        about = "Исследую осознанность и телесные практики",
        avatar = "https://picsum.photos/seed/abstract01/600/600"
    ),
    UserModel(
        id = "2",
        nickName = "Дмитрий",
        level = 7,
        about = "Психология, логика и немного дзена",
        avatar = "https://picsum.photos/seed/abstract02/600/600"
    ),
    UserModel(
        id = "3",
        nickName = "Мария",
        level = 3,
        about = "Пишу о чувствах и внутренних состояниях",
        avatar = "https://picsum.photos/seed/abstract03/600/600"
    ),
    UserModel(
        id = "4",
        nickName = "Илья",
        level = 11,
        about = "Ищу баланс между рациональным и интуитивным",
        avatar = "https://picsum.photos/seed/abstract04/600/600"
    ),
    UserModel(
        id = "5",
        nickName = "Екатерина",
        level = 5,
        about = "Телесная терапия и мягкие практики",
        avatar = "https://picsum.photos/seed/abstract05/600/600"
    ),
    UserModel(
        id = "6",
        nickName = "Ольга",
        level = 2,
        about = "Практикую осознанное письмо",
        avatar = "https://picsum.photos/seed/abstract07/600/600"
    ),
    UserModel(
        id = "7",
        nickName = "Валерия",
        level = 1,
        about = "Работаю с эмоциями через визуальные образы",
        avatar = "https://picsum.photos/seed/abstract09/600/600"
    )
)
val feedCards = listOf(
    UserContent.Post(
        id = "p1",
        text = "Иногда тишина говорит больше, чем любые формулировки. \nОсобенно когда честно отвечаешь себе.\n" +
                "bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla \n" +
                "bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla \n" +
                "bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla \n" +
                "bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla \n" +
                "bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla \n" +
                "bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla \n" +
                "bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla \n" +
                "bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla \n" +
                "bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla \n" +
                "bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla bla \n" +
                "",
        author = demoUsers[0],
        image = null
    ),

//    SocialContent.Ad(
//        id = "ad1",
//        title = "🧘 Онлайн-практика для восстановления фокуса",
//        image = "https://picsum.photos/seed/ad01/800/600",
//        cta = "Попробовать"
//    ),

    UserContent.Post(
        id = "p2",
        text = "Принятие начинается с честного взгляда на себя",
        author = demoUsers[2],
        image = "https://picsum.photos/seed/post01/900/600"
    ),

    UserContent.Post(
        id = "p3",
        text = "Иногда путь — это просто разрешение не спешить",
        author = demoUsers[4],
        image = null
    ),

//    SocialContent.Ad(
//        id = "ad2",
//        title = "🌿 Курс «Медленное внимание»",
//        image = "https://picsum.photos/seed/ad02/800/600",
//        cta = "Начать"
//    ),

    UserContent.Post(
        id = "p4",
        text = "Записывать мысли — значит давать им форму",
        author = demoUsers[5],
        image = null
    ),

    UserContent.Post(
        id = "p5",
        text = "Образ иногда точнее слов",
        author = demoUsers[6],
        image = "https://picsum.photos/seed/post02/900/600"
    ),

//    SocialContent.Ad(
//        id = "ad3",
//        title = "🔮 Индивидуальная сессия самопознания",
//        image = "https://picsum.photos/seed/ad03/800/600",
//        cta = "Записаться"
//    )
)

val auraSections = listOf(
    AuraSection(
        sectionTitle = "Психология",
        rowConfig = AuraRowConfig(
            color = AuraColors.BURGUNDY,
            topIconRes = R.drawable.illusion,
            centerIconRes = R.drawable.card_trick,
            bottomIconRes = R.drawable.card_trick
        ),
        items = listOf(
            AuraItem(
                id = "0",
                type = AuraItemType.PSYCHOLOGY_TEST,
                title = "Дневные тесты",
                description = "Ежедневная диагностика состояния"
            ),
            AuraItem(
                id = "personal_tests",
                type = AuraItemType.PSYCHOLOGY_TEST,
                title = "Персональные тесты",
                description = "Глубокий анализ личности"
            )
        )
    ),

    AuraSection(
        sectionTitle = "Гороскоп",
        rowConfig = AuraRowConfig(
            color = AuraColors.BLUE,
            topIconRes = R.drawable.wizard_hat,
            centerIconRes = R.drawable.wizard_hat,
            bottomIconRes = R.drawable.wizard_hat
        ),
        items = listOf(
            AuraItem(
                id = "horoscope_day",
                type = AuraItemType.HOROSCOPE,
                title = "Гороскоп\nНа день",
                description = "Расширенный гороскоп"
            ),
            AuraItem(
                id = "horoscope_extended",
                type = AuraItemType.HOROSCOPE,
                title = "Гороскоп\nНа неделю",
                description = "Расширенный гороскоп"
            ),
            AuraItem(
                id = "horoscope_year",
                type = AuraItemType.HOROSCOPE,
                title = "Гороскоп\nНа месяц",
                description = "Расширенный гороскоп"
            )
        )
    ),

    AuraSection(
        sectionTitle = "Совместимость",
        rowConfig = AuraRowConfig(
            color = AuraColors.INDIGO,
            topIconRes = null,
            centerIconRes = R.drawable.magic_potion,
            bottomIconRes = null
        ),
        items = listOf(
            AuraItem(
                id = "compatibility_names",
                type = AuraItemType.COMPATIBILITY,
                title = "Совместимость \nПо именам",
                description = "По именам"
            ),
            AuraItem(
                id = "compatibility_dates",
                type = AuraItemType.COMPATIBILITY,
                title = "Совместимость \nПо датам",
                description = "По датам рождения"
            ),
            AuraItem(
                id = "compatibility_horoscope",
                type = AuraItemType.COMPATIBILITY,
                title = "Совместимость \nПо гороскопу",
                description = "По гороскопу"
            )
        )
    ),

    AuraSection(
        sectionTitle = "Изотерика",
        rowConfig = AuraRowConfig(
            color = AuraColors.PLUM,
            topIconRes = R.drawable.magic_hat,
            centerIconRes = R.drawable.magic_hat,
            bottomIconRes = R.drawable.magic_hat
        ),
        items = listOf(
            AuraItem(
                id = "divination_topics",
                type = AuraItemType.DIVINATION,
                title = "Карта дня",
                description = "Карта дня"
            ),
            AuraItem(
                id = "divination_topics",
                type = AuraItemType.DIVINATION,
                title = "Гадания",
                description = "Гадания по темам"
            ),
            AuraItem(
                id = "divination_situations",
                type = AuraItemType.DIVINATION,
                title = "Гадания",
                description = "Гадания по ситуациям"
            )
        )
    ),

    AuraSection(
        sectionTitle = "Курсы",
        rowConfig = AuraRowConfig(
            color = AuraColors.GOLD,
            topIconRes = R.drawable.cauldron_potion,
            centerIconRes = R.drawable.cauldron_potion,
            bottomIconRes = R.drawable.cauldron_potion
        ),
        items = listOf(
            AuraItem(
                id = "aura_base",
                type = AuraItemType.COURSE,
                title = "Аура: база",
                description = "Основы работы с аурой"
            ),
            AuraItem(
                id = "master_classes",
                type = AuraItemType.COURSE,
                title = "Мастер-классы",
                description = "Экспертные сессии"
            ),
            AuraItem(
                id = "tarot_course",
                type = AuraItemType.COURSE,
                title = "Таро",
                description = "Классическое Таро"
            ),
            AuraItem(
                id = "numerology_course",
                type = AuraItemType.COURSE,
                title = "Нумерология",
                description = "Числа судьбы"
            )
        )
    ),
)