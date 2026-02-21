package com.example.ur_color.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

fun lerp(start: Color, stop: Color, fraction: Float): Color {
    val f = fraction.coerceIn(0f, 1f)
    return Color(
        red = start.red + (stop.red - start.red) * f,
        green = start.green + (stop.green - start.green) * f,
        blue = start.blue + (stop.blue - start.blue) * f,
        alpha = start.alpha + (stop.alpha - start.alpha) * f
    )
}

class AutoScrollPagerScope {
    private val items = mutableListOf<@Composable () -> Unit>()

    fun item(content: @Composable () -> Unit) {
        items.add(content)
    }

    fun build(): List<@Composable () -> Unit> = items.toList()
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

enum class WindowType { Slim, Regular, Full }
enum class IconPosition { START, END }

class DateVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {

        val digits = text.text

        val formatted = buildString {
            for (i in digits.indices) {
                append(digits[i])
                if ((i == 1 || i == 3) && i != digits.lastIndex) {
                    append(".")
                }
            }
        }

        val offsetTranslator = object : OffsetMapping {

            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 2 -> offset
                    offset <= 4 -> offset + 1
                    else -> offset + 2
                }.coerceAtMost(formatted.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 2 -> offset
                    offset <= 5 -> offset - 1
                    else -> offset - 2
                }.coerceAtLeast(0)
            }
        }

        return TransformedText(
            AnnotatedString(formatted),
            offsetTranslator
        )
    }
}

class TimeVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val input = text.text
        val formatted = buildString {
            for (i in input.indices) {
                append(input[i])
                if (i == 1 && input.length > 2) append(":")
            }
        }

        val offsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 1 -> offset
                    input.length <= 2 -> offset
                    else -> offset + 1
                }.coerceAtMost(formatted.length)
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 2 -> offset
                    input.length <= 2 -> offset
                    else -> offset - 1
                }.coerceIn(0, input.length)
            }
        }

        return TransformedText(AnnotatedString(formatted), offsetTranslator)
    }
}