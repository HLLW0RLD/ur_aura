package com.example.ur_color.data.dataProcessor.aura

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.ur_color.data.model.user.UserData
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

object ZodiacLibrary {

    fun zodiacFrame(width: Int, height: Int, user: UserData): Bitmap {
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        val rnd = AuraUtils.seededRandom(user.auraSeed + 777)

        val hue = when (user.zodiacSign.lowercase()) {
            "aries","leo","sagittarius" -> 15f
            "taurus","virgo","capricorn" -> 120f
            "gemini","libra","aquarius" -> 200f
            "cancer","scorpio","pisces" -> 260f
            else -> 180f
        }

        val frameColor = Color.HSVToColor(
            (100 + user.characteristics.energyLevel * 10).coerceAtMost(255),
            floatArrayOf(hue, 0.7f, 1f)
        )

        // Внешняя рамка
        if ((user.characteristics.energyLevel + rnd.nextInt(100)) % 2 == 0) {
            val margin = (width / 10f) + rnd.nextInt(0, 40)
            val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.STROKE
                strokeWidth = (2.5f + user.characteristics.energyLevel * 0.35f)
                color = frameColor
                alpha = 180
            }
            canvas.drawRect(margin, margin, width - margin, height - margin, paint)
        }

        // Внутренний символ (стилизованный знак зодиака)
        val cx = width / 2f
        val cy = height / 2f
        val symbolPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 3f
            color = frameColor
            alpha = 140
        }
        drawSimpleZodiacSymbol(canvas, user.zodiacSign, cx, cy, min(width, height) * 0.18f, symbolPaint)

        return bmp
    }

    private fun drawSimpleZodiacSymbol(canvas: Canvas, zodiac: String, cx: Float, cy: Float, size: Float, paint: Paint) {
        when (zodiac.lowercase()) {
            "aries" -> {
                canvas.drawArc(cx - size, cy - size, cx + size, cy + size, -140f, 80f, false, paint)
                canvas.drawArc(cx - size, cy - size, cx + size, cy + size, -40f, 80f, false, paint)
            }
            "taurus" -> {
                canvas.drawCircle(cx, cy - size*0.4f, size*0.25f, paint)
                canvas.drawRect(cx - size*0.6f, cy - size*0.1f, cx + size*0.6f, cy + size*0.2f, paint)
            }
            // Можно расширить для всех знаков
            else -> {
                canvas.drawCircle(cx, cy, size, paint)
                for (i in 0 until 6) {
                    val a = (2 * Math.PI / 6 * i).toFloat()
                    val x = cx + cos(a) * size
                    val y = cy + sin(a) * size
                    canvas.drawLine(cx, cy, x, y, paint)
                }
            }
        }
    }
}