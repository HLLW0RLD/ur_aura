package com.example.ur_color.data.local

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.ur_color.data.model.Card
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.ZoneId
import kotlin.random.Random

class LocalDailyCardService {

    private val cards = listOf(
        Card(
            name = "Дурак",
            shortMeaning = "Новые начинания, спонтанность",
            fullMeaning = "Начало нового пути, доверие процессу, приключение, неожиданности.",
            advice = "Будь открытым для нового, риск может привести к интересным возможностям.",
            element = "Воздух",
            number = 0,
            keywords = listOf("новое", "свобода", "начало"),
            compatibleWith = listOf("Маг", "Верховная Жрица"),
            imageUrl = "https://example.com/fool.png"
        ),
        Card(
            name = "Маг",
            shortMeaning = "Воля и ресурсы",
            fullMeaning = "Способность использовать свои навыки и ресурсы для достижения цели.",
            advice = "Сосредоточься на своих силах и начинай с маленьких шагов.",
            element = "Воздух",
            number = 1,
            keywords = listOf("воля", "сосредоточенность", "ресурсы"),
            compatibleWith = listOf("Дурак", "Императрица"),
            imageUrl = "https://example.com/magician.png"
        ),
        Card(
            name = "Верховная Жрица",
            shortMeaning = "Интуиция и тайны",
            fullMeaning = "Слушай внутренний голос, ищи скрытую информацию и мудрость.",
            advice = "Прислушивайся к ощущениям, не всё стоит обсуждать вслух.",
            element = "Вода",
            number = 2,
            keywords = listOf("интуиция", "тайны", "мудрость"),
            compatibleWith = listOf("Дурак", "Императрица"),
            imageUrl = "https://example.com/high_priestess.png"
        ),
        Card(
            name = "Императрица",
            shortMeaning = "Творчество и забота",
            fullMeaning = "Плодородие идей, забота о себе и других, вдохновение.",
            advice = "Позаботься о себе и своих проектах, прояви мягкость и заботу.",
            element = "Земля",
            number = 3,
            keywords = listOf("творчество", "забота", "изобилие"),
            compatibleWith = listOf("Маг", "Влюблённые"),
            imageUrl = "https://example.com/empress.png"
        ),
        Card(
            name = "Император",
            shortMeaning = "Структура и лидерство",
            fullMeaning = "Контроль, порядок, ответственность и лидерство в делах.",
            advice = "Установи границы и следуй плану — это принесёт успех.",
            element = "Огонь",
            number = 4,
            keywords = listOf("порядок", "власть", "лидерство"),
            compatibleWith = listOf("Императрица", "Иерофант"),
            imageUrl = "https://example.com/emperor.png"
        ),
        Card(
            name = "Иерофант",
            shortMeaning = "Традиции и наставничество",
            fullMeaning = "Следование проверенным путям, мудрость старших и наставников.",
            advice = "Обратись к опыту тех, кто уже прошёл этот путь.",
            element = "Земля",
            number = 5,
            keywords = listOf("традиции", "учитель", "мудрость"),
            compatibleWith = listOf("Император", "Влюблённые"),
            imageUrl = "https://example.com/hierophant.png"
        ),
        Card(
            name = "Влюблённые",
            shortMeaning = "Выбор и гармония",
            fullMeaning = "Серьёзный выбор, отношения и поиск гармонии.",
            advice = "Прими решение сердцем, но не забывай о фактах.",
            element = "Воздух",
            number = 6,
            keywords = listOf("выбор", "гармония", "отношения"),
            compatibleWith = listOf("Иерофант", "Императрица"),
            imageUrl = "https://example.com/lovers.png"
        ),
        Card(
            name = "Колесница",
            shortMeaning = "Движение и контроль",
            fullMeaning = "Успех через контроль и настойчивость, движение вперёд.",
            advice = "Держи курс и проявляй настойчивость — победа будет за тобой.",
            element = "Вода",
            number = 7,
            keywords = listOf("воля", "успех", "путешествие"),
            compatibleWith = listOf("Сила", "Влюблённые"),
            imageUrl = "https://example.com/chariot.png"
        ),
        Card(
            name = "Сила",
            shortMeaning = "Терпение и смелость",
            fullMeaning = "Внутренняя сила проявляется через мягкость и терпение.",
            advice = "Действуй с состраданием и сохраняй баланс.",
            element = "Огонь",
            number = 8,
            keywords = listOf("смелость", "терпение", "самоконтроль"),
            compatibleWith = listOf("Колесница", "Отшельник"),
            imageUrl = "https://example.com/strength.png"
        ),
        Card(
            name = "Отшельник",
            shortMeaning = "Размышление и пауза",
            fullMeaning = "Время для анализа, поиска смысла и внутреннего роста.",
            advice = "Возьми паузу и подумай о следующем шаге.",
            element = "Земля",
            number = 9,
            keywords = listOf("размышление", "самопознание", "пауза"),
            compatibleWith = listOf("Сила", "Правосудие"),
            imageUrl = "https://example.com/hermit.png"
        ),
        Card(
            name = "Колесо Фортуны",
            shortMeaning = "Перемены и цикл",
            fullMeaning = "Жизненные перемены, неожиданные события, цикличность.",
            advice = "Прими изменения и используй новые возможности.",
            element = "Воздух",
            number = 10,
            keywords = listOf("изменения", "удача", "циклы"),
            compatibleWith = listOf("Правосудие", "Отшельник"),
            imageUrl = "https://example.com/wheel.png"
        ),
        Card(
            name = "Правосудие",
            shortMeaning = "Равновесие и ответственность",
            fullMeaning = "Чёткие решения, справедливость, честность и баланс.",
            advice = "Будь объективен и честен в поступках.",
            element = "Воздух",
            number = 11,
            keywords = listOf("баланс", "справедливость", "ответственность"),
            compatibleWith = listOf("Колесо Фортуны", "Отшельник"),
            imageUrl = "https://example.com/justice.png"
        )
    )

    // Функция для генерации карты дня
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun generateDailyCard(userName: String): Result<Card> = withContext(Dispatchers.Default) {
        try {
            val zone = ZoneId.systemDefault()
            val today = LocalDate.now(zone).toString()
            val seedString = "${userName.trim().lowercase()}|$today"
            val seed = seedString.hashCode().toLong()
            val rnd = Random(seed)

            val chosen = cards[rnd.nextInt(cards.size)]
            Result.success(chosen)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
