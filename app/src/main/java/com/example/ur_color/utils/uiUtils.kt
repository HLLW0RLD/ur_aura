package com.example.ur_color.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.ur_color.R
import com.example.ur_color.data.model.SocialContent

enum class WindowType { Slim, Regular, Full }

enum class IconPosition { START, END }

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

val demoCards = listOf(
    SocialContent.User(
        id = "1",
        username = "Анастасия",
        about = "Исследую осознанность и телесные практики",
        avatar = "https://picsum.photos/seed/abstract01/600/600"
    ),
    SocialContent.Post(
        id = "p1",
        title = "Иногда тишина говорит больше, чем слова",
        author = "Анастасия",
        image = ""
    ),
    SocialContent.User(
        id = "2",
        username = "Дмитрий",
        about = "Психология, логика и немного дзена",
        avatar = "https://picsum.photos/seed/abstract02/600/600"
    ),
    SocialContent.Ad(
        id = "ad1",
        title = "🧘 Онлайн-практика для восстановления фокуса",
        image = "https://picsum.photos/seed/ad01/800/600",
        cta = "Попробовать"
    ),
    SocialContent.User(
        id = "3",
        username = "Мария",
        about = "Пишу о чувствах и внутренних состояниях",
        avatar = "https://picsum.photos/seed/abstract03/600/600"
    ),
    SocialContent.Post(
        id = "p2",
        title = "Принятие начинается с честного взгляда на себя",
        author = "Мария",
        image = ""
    ),
    SocialContent.User(
        id = "4",
        username = "Илья",
        about = "Ищу баланс между рациональным и интуитивным",
        avatar = "https://picsum.photos/seed/abstract04/600/600"
    ),
    SocialContent.User(
        id = "5",
        username = "Екатерина",
        about = "Телесная терапия и мягкие практики",
        avatar = "https://picsum.photos/seed/abstract05/600/600"
    ),
    SocialContent.Post(
        id = "p3",
        title = "Иногда путь — это просто разрешение не спешить",
        author = "Екатерина",
        image = ""
    ),
    SocialContent.Ad(
        id = "ad2",
        title = "🌿 Курс «Медленное внимание»",
        image = "https://picsum.photos/seed/ad02/800/600",
        cta = "Начать"
    ),
    SocialContent.User(
        id = "6",
        username = "Артём",
        about = "Наблюдаю, анализирую, делюсь выводами",
        avatar = "https://picsum.photos/seed/abstract06/600/600"
    ),
    SocialContent.User(
        id = "7",
        username = "Ольга",
        about = "Практикую осознанное письмо",
        avatar = "https://picsum.photos/seed/abstract07/600/600"
    ),
    SocialContent.Post(
        id = "p4",
        title = "Записывать мысли — значит давать им форму",
        author = "Ольга",
        image = ""
    ),
    SocialContent.User(
        id = "8",
        username = "Никита",
        about = "Минимализм в жизни и в голове",
        avatar = "https://picsum.photos/seed/abstract08/600/600"
    ),
    SocialContent.Ad(
        id = "ad3",
        title = "🔮 Индивидуальная сессия самопознания",
        image = "https://picsum.photos/seed/ad03/800/600",
        cta = "Записаться"
    ),
    SocialContent.User(
        id = "9",
        username = "Валерия",
        about = "Работаю с эмоциями через визуальные образы",
        avatar = "https://picsum.photos/seed/abstract09/600/600"
    ),
    SocialContent.Post(
        id = "p5",
        title = "Образ иногда точнее слов",
        author = "Валерия",
        image = ""
    ),
    SocialContent.User(
        id = "10",
        username = "Сергей",
        about = "Рациональность — тоже форма заботы",
        avatar = "https://picsum.photos/seed/abstract10/600/600"
    )
)