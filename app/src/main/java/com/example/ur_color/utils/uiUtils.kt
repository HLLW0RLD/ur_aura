package com.example.ur_color.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.ur_color.R
import com.example.ur_color.data.model.ModType
import com.example.ur_color.data.model.SocialContent
import com.example.ur_color.data.model.User

enum class WindowType { Slim, Regular, Full }

enum class IconPosition { START, END }

fun lerp(start: Color, stop: Color, fraction: Float): Color {
    val f = fraction.coerceIn(0f, 1f)
    return Color(
        red = start.red + (stop.red - start.red) * f,
        green = start.green + (stop.green - start.green) * f,
        blue = start.blue + (stop.blue - start.blue) * f,
        alpha = start.alpha + (stop.alpha - start.alpha) * f
    )
}

class TwoColumnScopeImpl : TwoColumnScope {
    val leftColumn = mutableListOf<@Composable () -> Unit>()
    val rightColumn = mutableListOf<@Composable () -> Unit>()
    private var toggle = false

    override fun left(content: @Composable () -> Unit) {
        leftColumn.add(content)
    }

    override fun right(content: @Composable () -> Unit) {
        rightColumn.add(content)
    }

    override fun item(content: @Composable () -> Unit) {
        if (toggle) rightColumn.add(content) else leftColumn.add(content)
        toggle = !toggle
    }
}
interface TwoColumnScope {
    fun left(content: @Composable () -> Unit)
    fun right(content: @Composable () -> Unit)
    fun item(content: @Composable () -> Unit)
}

val animPic = listOf(
    R.drawable.illusion,
    R.drawable.magic_sparkles,
    R.drawable.magic_potion,
    R.drawable.card_trick,
    R.drawable.cauldron_potion,
    R.drawable.magic_stick_sparckles,
    R.drawable.ball_crystal,
    R.drawable.candle,
    R.drawable.witch_hat,
    R.drawable.magic_hat,
)

val questionTemplates: Map<ModType, List<String>> = mapOf(

    // ---------- PHYSICAL ----------
    ModType.ENERGY_LEVEL to listOf(
        "Чувствуете ли вы сегодня прилив энергии?",
        "Хватало ли вам сил на повседневные дела?",
        "Были ли периоды физического подъёма?"
    ),

    ModType.PHYSICAL_ENERGY to listOf(
        "Насколько активно вы чувствовали своё тело?",
        "Было ли ощущение силы и выносливости?",
        "Легко ли давалась физическая активность?"
    ),

    ModType.SLEEP_QUALITY to listOf(
        "Насколько качественным был ваш сон?",
        "Удалось ли вам хорошо выспаться?",
        "Проснулись ли вы отдохнувшим?"
    ),

    // ---------- EMOTIONAL ----------
    ModType.MOOD to listOf(
        "Как бы вы оценили своё настроение сегодня?",
        "Были ли положительные эмоции в течение дня?",
        "Чувствовали ли вы внутренний комфорт?"
    ),

    ModType.MOTIVATION to listOf(
        "Было ли желание что-то делать и начинать новое?",
        "Чувствовали ли вы внутренний импульс к действиям?",
        "Легко ли было браться за задачи?"
    ),

    ModType.FOCUS to listOf(
        "Насколько легко было сосредоточиться?",
        "Удалось ли вам сохранять внимание на задачах?",
        "Были ли сложности с концентрацией?"
    ),

    // ---------- SOCIAL ----------
    ModType.CHARISMA to listOf(
        "Чувствовали ли вы уверенность в общении?",
        "Люди реагировали на вас позитивно?",
        "Легко ли было привлекать внимание?"
    ),

    ModType.SOCIAL_ENERGY to listOf(
        "Было ли у вас желание взаимодействовать с людьми?",
        "Давало ли общение энергию?",
        "Чувствовали ли вы социальный подъём?"
    ),

    ModType.COMMUNICATION to listOf(
        "Легко ли удавалось доносить свои мысли?",
        "Было ли взаимопонимание в общении?",
        "Чувствовали ли вы себя услышанным?"
    ),

    // ---------- NEGATIVE ----------
    ModType.STRESS to listOf(
        "Испытывали ли вы стресс сегодня?",
        "Были ли напряжённые ситуации?",
        "Чувствовали ли вы давление или перегрузку?"
    ),

    ModType.ANXIETY to listOf(
        "Было ли чувство тревоги?",
        "Возникало ли беспокойство без причины?",
        "Сложно ли было расслабиться?"
    ),

    ModType.FATIGUE to listOf(
        "Чувствовали ли вы сильную усталость?",
        "Было ли ощущение истощения?",
        "Хотелось ли просто отдохнуть без активности?"
    )
)

val demoUsers = listOf(
    User(
        id = "0",
        username = "bobo",
        level = 777,
        about = "ауры это круто!",
        avatar = "https://picsum.photos/seed/abstract02/600/600"
    ),
    User(
        id = "1",
        username = "Анастасия",
        level = 2,
        about = "Исследую осознанность и телесные практики",
        avatar = "https://picsum.photos/seed/abstract01/600/600"
    ),
    User(
        id = "2",
        username = "Дмитрий",
        level = 7,
        about = "Психология, логика и немного дзена",
        avatar = "https://picsum.photos/seed/abstract02/600/600"
    ),
    User(
        id = "3",
        username = "Мария",
        level = 3,
        about = "Пишу о чувствах и внутренних состояниях",
        avatar = "https://picsum.photos/seed/abstract03/600/600"
    ),
    User(
        id = "4",
        username = "Илья",
        level = 11,
        about = "Ищу баланс между рациональным и интуитивным",
        avatar = "https://picsum.photos/seed/abstract04/600/600"
    ),
    User(
        id = "5",
        username = "Екатерина",
        level = 5,
        about = "Телесная терапия и мягкие практики",
        avatar = "https://picsum.photos/seed/abstract05/600/600"
    ),
    User(
        id = "6",
        username = "Ольга",
        level = 2,
        about = "Практикую осознанное письмо",
        avatar = "https://picsum.photos/seed/abstract07/600/600"
    ),
    User(
        id = "7",
        username = "Валерия",
        level = 1,
        about = "Работаю с эмоциями через визуальные образы",
        avatar = "https://picsum.photos/seed/abstract09/600/600"
    )
)
val feedCards = listOf(
    SocialContent.Post(
        id = "p1",
        text = "Иногда тишина говорит больше, чем слова",
        author = demoUsers[0],
        image = null
    ),

    SocialContent.Ad(
        id = "ad1",
        title = "🧘 Онлайн-практика для восстановления фокуса",
        image = "https://picsum.photos/seed/ad01/800/600",
        cta = "Попробовать"
    ),

    SocialContent.Post(
        id = "p2",
        text = "Принятие начинается с честного взгляда на себя",
        author = demoUsers[2],
        image = "https://picsum.photos/seed/post01/900/600"
    ),

    SocialContent.Post(
        id = "p3",
        text = "Иногда путь — это просто разрешение не спешить",
        author = demoUsers[4],
        image = null
    ),

    SocialContent.Ad(
        id = "ad2",
        title = "🌿 Курс «Медленное внимание»",
        image = "https://picsum.photos/seed/ad02/800/600",
        cta = "Начать"
    ),

    SocialContent.Post(
        id = "p4",
        text = "Записывать мысли — значит давать им форму",
        author = demoUsers[5],
        image = null
    ),

    SocialContent.Post(
        id = "p5",
        text = "Образ иногда точнее слов",
        author = demoUsers[6],
        image = "https://picsum.photos/seed/post02/900/600"
    ),

    SocialContent.Ad(
        id = "ad3",
        title = "🔮 Индивидуальная сессия самопознания",
        image = "https://picsum.photos/seed/ad03/800/600",
        cta = "Записаться"
    )
)
val profileCards = listOf(
    SocialContent.Post(
        id = "p1",
        text = "Начал отвечать на ежедневные вопросы и неожиданно понял, как редко я вообще останавливаюсь и слушаю себя.",
        author = demoUsers[0],
        image = null
    ),

    SocialContent.Post(
        id = "p2",
        text = "Иногда тишина говорит больше, чем любые формулировки. Особенно когда честно отвечаешь себе.",
        author = demoUsers[0],
        image = "https://picsum.photos/seed/post01/900/600"
    ),

    SocialContent.Post(
        id = "p3",
        text = "Принятие — это не согласие и не оправдание. Скорее, это признание того, что сейчас именно так.",
        author = demoUsers[0],
        image = null
    ),

    SocialContent.Post(
        id = "p4",
        text = "Поймал себя на мысли, что путь — это не движение вперёд, а разрешение иногда не торопиться.",
        author = demoUsers[0],
        image = null
    ),

    SocialContent.Post(
        id = "p5",
        text = "Записывать ощущения после тестов оказалось важнее, чем я думал. Мысли становятся чётче, когда им дают форму.",
        author = demoUsers[0],
        image = "https://picsum.photos/seed/post02/900/600"
    ),

    SocialContent.Post(
        id = "p6",
        text = "Чем больше наблюдаю за собой, тем меньше хочется давать быстрые оценки — себе и другим.",
        author = demoUsers[0],
        image = null
    ),

    SocialContent.Post(
        id = "p7",
        text = "Иногда образ точнее слов. Состояние можно почувствовать, но сложно объяснить.",
        author = demoUsers[0],
        image = "https://picsum.photos/seed/post03/900/600"
    )
)