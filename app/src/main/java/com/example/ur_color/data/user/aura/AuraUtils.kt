package com.example.ur_color.data.user.aura

import android.graphics.Color
import com.example.ur_color.ui.theme.AuraColors
import kotlin.random.Random
import androidx.core.graphics.toColorInt

object AuraUtils {

    fun parseColor(hex: String?): Int {
        return try {
            if (hex.isNullOrBlank()) Color.WHITE else Color.parseColor(hex)
        } catch (t: Throwable) {
            Color.WHITE
        }
    }

    fun getColorFromEnum(vector: List<String>?, idx: Int, defaultHex: String = "#FFFFFF"): Int {
        val hex = vector?.getOrNull(idx)
        return parseColor(hex ?: defaultHex)
    }

    fun adjustAlpha(color: Int, factor: Float): Int {
        val a = (Color.alpha(color) * factor).toInt().coerceIn(0, 255)
        return Color.argb(a, Color.red(color), Color.green(color), Color.blue(color))
    }

    fun seededRandom(seed: Long) = Random(seed)
}