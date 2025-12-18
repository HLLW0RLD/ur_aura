package com.example.ur_color.ui

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

val demoCards = listOf (
    SocialContent.Product(
        id = "1",
        title = "Книга: «Тени внутреннего света»",
        price = "1 490 ₽",
        image = "https://picsum.photos/seed/airpods/600/600"
    ),
    SocialContent.Product(
        id = "2",
        title = "Кристалл горного хрусталя",
        price = "2 990 ₽",
        image = "https://picsum.photos/seed/vacuum/600/600"
    ),
    SocialContent.Product(
        id = "3",
        title = "Аромалампа с эфирными маслами",
        price = "3 490 ₽",
        image = "https://picsum.photos/seed/headphones/600/600"
    ),
    SocialContent.Ad(
        id = "4",
        title = "🔮 Индивидуальные консультации — откройте внутреннее Я",
        image = "https://picsum.photos/seed/sale/600/600",
        cta = "Записаться"
    ),
    SocialContent.Product(
        id = "5",
        title = "Дневник осознанности",
        price = "990 ₽",
        image = "https://picsum.photos/seed/nike/600/600"
    ),
    SocialContent.User(
        id = "6",
        username = "Анастасия",
        avatar = "https://picsum.photos/seed/user1/300/300"
    ),
    SocialContent.Product(
        id = "7",
        title = "Колода карт Таро «Лунный путь»",
        price = "2 290 ₽",
        image = "https://picsum.photos/seed/mouse/600/600"
    ),
    SocialContent.Product(
        id = "8",
        title = "Блокнот психотерапевта",
        price = "1 190 ₽",
        image = "https://picsum.photos/seed/ps5/600/600"
    ),
    SocialContent.Ad(
        id = "9",
        title = "🧘‍♀️ Онлайн-курс «Осознанность и Тишина»",
        image = "https://picsum.photos/seed/delivery/600/600",
        cta = "Начать путь"
    ),
    SocialContent.User(
        id = "10",
        username = "Дмитрий",
        avatar = "https://picsum.photos/seed/user2/300/300"
    ),
    SocialContent.Product(
        id = "11",
        title = "Свеча «Защита и покой»",
        price = "890 ₽",
        image = "https://picsum.photos/seed/camera/600/600"
    ),
    SocialContent.Product(
        id = "12",
        title = "Карта архетипов Карла Юнга",
        price = "1 290 ₽",
        image = "https://picsum.photos/seed/macbook/600/600"
    ),

    // Повтор второй половины
    SocialContent.Product(
        id = "13",
        title = "Книга: «Тени внутреннего света»",
        price = "1 490 ₽",
        image = "https://picsum.photos/seed/airpods/600/600"
    ),
    SocialContent.Product(
        id = "14",
        title = "Кристалл горного хрусталя",
        price = "2 990 ₽",
        image = "https://picsum.photos/seed/vacuum/600/600"
    ),
    SocialContent.Product(
        id = "15",
        title = "Аромалампа с эфирными маслами",
        price = "3 490 ₽",
        image = "https://picsum.photos/seed/headphones/600/600"
    ),
    SocialContent.Ad(
        id = "16",
        title = "🔮 Индивидуальные консультации — откройте внутреннее Я",
        image = "https://picsum.photos/seed/sale/600/600",
        cta = "Записаться"
    ),
    SocialContent.Product(
        id = "17",
        title = "Дневник осознанности",
        price = "990 ₽",
        image = "https://picsum.photos/seed/nike/600/600"
    ),
    SocialContent.User(
        id = "18",
        username = "Анастасия",
        avatar = "https://picsum.photos/seed/user1/300/300"
    ),
    SocialContent.Product(
        id = "19",
        title = "Колода карт Таро «Лунный путь»",
        price = "2 290 ₽",
        image = "https://picsum.photos/seed/mouse/600/600"
    ),
    SocialContent.Product(
        id = "20",
        title = "Блокнот психотерапевта",
        price = "1 190 ₽",
        image = "https://picsum.photos/seed/ps5/600/600"
    ),
    SocialContent.Ad(
        id = "21",
        title = "🧘‍♀️ Онлайн-курс «Осознанность и Тишина»",
        image = "https://picsum.photos/seed/delivery/600/600",
        cta = "Начать путь"
    ),
    SocialContent.User(
        id = "22",
        username = "Дмитрий",
        avatar = "https://picsum.photos/seed/user2/300/300"
    ),
    SocialContent.Product(
        id = "23",
        title = "Свеча «Защита и покой»",
        price = "890 ₽",
        image = "https://picsum.photos/seed/camera/600/600"
    ),
    SocialContent.Product(
        id = "24",
        title = "Карта архетипов Карла Юнга",
        price = "1 290 ₽",
        image = "https://picsum.photos/seed/macbook/600/600"
    )
)