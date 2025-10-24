package com.example.ur_color.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.random.Random

val LocalNavController =
    staticCompositionLocalOf<NavController> { throw IllegalStateException("No NavController found") }

fun String?.parseBirthHour(): Int {
    return try {
        if (this.isNullOrBlank()) return 12
        val parts = this.split(":")
        parts[0].toIntOrNull() ?: 12
    } catch (_: Exception) {
        12
    }
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