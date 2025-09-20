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
import kotlin.random.Random

val LocalNavController =
    staticCompositionLocalOf<NavController> { throw IllegalStateException("No NavController found") }

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

fun generatePatternAura(
    fullName: String,
    birthDate: String,
    width: Int = 512,
    height: Int = 512
): Bitmap {
    val seedString = "$fullName$birthDate"
    val seed = seedString.hashCode().toLong()
    val random = Random(seed)

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    val baseColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256))
    val accentColor = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256))

    val paint = Paint().apply {
        shader = LinearGradient(
            0f, 0f, width.toFloat(), height.toFloat(),
            baseColor,
            accentColor,
            Shader.TileMode.MIRROR
        )
    }
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

    val shapesCount = 5 + random.nextInt(6) // от 5 до 10 фигур
    repeat(shapesCount) {
        val shapePaint = Paint().apply {
            color = Color.argb(
                150, // прозрачность
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256)
            )
            style = Paint.Style.FILL
        }

        val x = random.nextInt(width).toFloat()
        val y = random.nextInt(height).toFloat()
        val size = 50f + random.nextInt(150)

        when (random.nextInt(3)) {
            0 -> canvas.drawCircle(x, y, size, shapePaint)
            1 -> canvas.drawRect(x, y, x + size, y + size, shapePaint)
            2 -> canvas.drawRoundRect(x, y, x + size, y + size, 20f, 20f, shapePaint)
        }
    }

    return bitmap
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