package com.example.ur_color.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

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
