package com.example.ur_color.utils

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.navigation.NavController
import com.example.ur_color.data.local.dataManager.SystemDataManager
import com.example.ur_color.ui.theme.AuraColors
import com.example.ur_color.ui.theme.ThemeMode
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale
import androidx.core.graphics.toColorInt

//  SYSTEM
/*==============================================================================================*/

internal fun Context.findActivity(): ComponentActivity {
    var context = this
    while (context is ContextWrapper) {
        if (context is ComponentActivity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Picture in picture should be called in the context of an Activity")
}

val LocalNavController =
    staticCompositionLocalOf<NavController> { throw IllegalStateException("No NavController found") }




//  UI / UX
/*==============================================================================================*/


fun String?.parseBirthHour(): Int {
    return try {
        if (this.isNullOrBlank()) return 12
        val parts = this.split(":")
        parts[0].toIntOrNull() ?: 12
    } catch (_: Exception) {
        12
    }
}

val isDarkTheme: Boolean get() = SystemDataManager.theme.value == ThemeMode.DARK

fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat("yyyy_MM_dd", Locale.getDefault())
    val date = Date()
    return dateFormat.format(date)
}

fun getCurrentDateTime(): String {
    val dateFormat = SimpleDateFormat("HH:mm:ss dd.MM.yyyy")
    val date = Date()
    return dateFormat.format(date)
}

fun formatTimeInput(oldValue: TextFieldValue, newValue: TextFieldValue): TextFieldValue {
    val digits = newValue.text.filter { it.isDigit() }

    val hour = when {
        digits.length >= 2 -> digits.substring(0, 2).take(2)
        else -> digits
    }

    val minute = if (digits.length > 2) digits.substring(2, minOf(4, digits.length)) else ""

    var formatted = hour
    if (minute.isNotEmpty()) formatted += ":$minute"

    if (formatted.length > 5) formatted = formatted.take(5)

    val digitsBeforeCursor = newValue.text.take(newValue.selection.start).count { it.isDigit() }

    var cursorPos = 0
    var digitsPassed = 0
    while (cursorPos < formatted.length && digitsPassed < digitsBeforeCursor) {
        if (formatted[cursorPos].isDigit()) digitsPassed++
        cursorPos++
    }

    return TextFieldValue(
        text = formatted,
        selection = TextRange(cursorPos)
    )
}

fun formatDateInput(oldValue: TextFieldValue, newValue: TextFieldValue): TextFieldValue {
    val digits = newValue.text.filter { it.isDigit() }

    val day = when {
        digits.length >= 2 -> digits.substring(0, 2)
        else -> digits
    }
    val month = when {
        digits.length >= 4 -> digits.substring(2, 4)
        digits.length > 2 -> digits.substring(2)
        else -> ""
    }
    val year = if (digits.length > 4) digits.substring(4, minOf(8, digits.length)) else ""

    var formatted = day
    if (month.isNotEmpty()) formatted += "/$month"
    if (year.isNotEmpty()) formatted += "/$year"

    if (formatted.length > 10) {
        formatted = formatted.take(10)
    }

    val digitsBeforeCursor = newValue.text.take(newValue.selection.start).count { it.isDigit() }

    var cursorPos = 0
    var digitsPassed = 0
    while (cursorPos < formatted.length && digitsPassed < digitsBeforeCursor) {
        if (formatted[cursorPos].isDigit()) {
            digitsPassed++
        }
        cursorPos++
    }

    return TextFieldValue(
        text = formatted,
        selection = TextRange(cursorPos)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateToRussian(dateStr: String): String {
    return try {
        val parser = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH)
        val parsed = LocalDate.parse(dateStr, parser)
        val rusFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("ru"))
        parsed.format(rusFormatter)
    } catch (e: Exception) {
        dateStr
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun calculateAge(birthDate: String): Int? {
    return try {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val date = LocalDate.parse(birthDate, formatter)
        val today = LocalDate.now()
        Period.between(date, today).years
    } catch (e: Exception) {
        null
    }
}

// для обозначения актуальности/ДЭДЛАЙНА относительно даты
@RequiresApi(Build.VERSION_CODES.O)
fun String.setColorDate(isDeadLine: Boolean = true): androidx.compose.ui.graphics.Color? {
    try {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

        if (!this.matches(Regex("\\d{2}\\.\\d{2}\\.\\d{4}"))) {
            return null
            throw IllegalArgumentException("Дата должна быть в формате dd.MM.yyyy")
        }

        val inputDate = LocalDate.parse(this, formatter)
        val currentDate = LocalDate.now()

        val days = if (isDeadLine) {
            ChronoUnit.DAYS.between(inputDate, currentDate)
        } else {
            ChronoUnit.DAYS.between(currentDate, inputDate)
        }


        return if (isDeadLine) {
            // Дедлайн: чем ближе дата, тем краснее
            when {
                days > 8 -> androidx.compose.ui.graphics.Color.Green
                days in 3..7 -> androidx.compose.ui.graphics.Color.Yellow
                days in 1..2 -> androidx.compose.ui.graphics.Color.hsl(35f, 1f, 0.5f)
                days == 0L -> androidx.compose.ui.graphics.Color.Red
                else -> androidx.compose.ui.graphics.Color.White
            }
        } else {
            // Актуальность: чем свежее дата, тем зеленее
            when {
                days <= 1 -> androidx.compose.ui.graphics.Color.Green
                days in 2..3 -> androidx.compose.ui.graphics.Color.Yellow
                days in 4..7 -> androidx.compose.ui.graphics.Color.hsl(35f, 1f, 0.5f)
                days > 7 -> androidx.compose.ui.graphics.Color.Red
                else -> androidx.compose.ui.graphics.Color.White
            }
        }

    } catch (e : Exception) {
        logError("string: $this \nerror $e")
    }
    return androidx.compose.ui.graphics.Color.Red
}

fun List<Int>.toColoredText(): AnnotatedString {
    val lowColors = listOf(
        AuraColors.RED,
        AuraColors.ORANGE,
        AuraColors.YELLOW,
        AuraColors.LIME,
        AuraColors.GREEN
    )

    val highColors = listOf(
        AuraColors.GREEN,
        AuraColors.CYAN,
        AuraColors.BLUE,
        AuraColors.INDIGO,
        AuraColors.VIOLET
    )

    return buildAnnotatedString {
        this@toColoredText.forEachIndexed { index, num ->
            val color = when (num) {
                in 1..5 -> Color(lowColors[num - 1].hex.toColorInt())
                in 6..10 -> Color(highColors[num - 6].hex.toColorInt())
                else -> Color.White
            }

            withStyle(SpanStyle(color = color)) {
                append(num.toString())
                if (index != this@toColoredText.lastIndex) append(" ")
            }
        }
    }
}