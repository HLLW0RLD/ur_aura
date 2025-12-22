package com.example.ur_color.data.dataProcessor.aura

import android.graphics.Color
import kotlin.random.Random

object AuraUtils {

    fun colorFromValue(value: Float): Int {
        val v = value.coerceIn(1f, 10f)
        val fraction = (v - 1) / 9f
        val startColor = Color.BLUE
        val endColor = Color.RED

        val r = (Color.red(startColor) + fraction * (Color.red(endColor) - Color.red(startColor))).toInt()
        val g = (Color.green(startColor) + fraction * (Color.green(endColor) - Color.green(startColor))).toInt()
        val b = (Color.blue(startColor) + fraction * (Color.blue(endColor) - Color.blue(startColor))).toInt()

        return Color.rgb(r, g, b)
    }

    fun getColorFromEnum(vector: List<Float>?, idx: Int, defaultValue: Float = 5f): Int {
        val value = vector?.getOrNull(idx) ?: defaultValue
        return colorFromValue(value)
    }

    fun adjustAlpha(color: Int, factor: Float): Int {
        val a = (Color.alpha(color) * factor).toInt().coerceIn(0, 255)
        return Color.argb(a, Color.red(color), Color.green(color), Color.blue(color))
    }

    fun seededRandom(seed: Long) = Random(seed)
}