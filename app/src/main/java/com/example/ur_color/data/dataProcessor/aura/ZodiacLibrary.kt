package com.example.ur_color.data.dataProcessor.aura

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import com.example.ur_color.data.model.user.UserData
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.random.Random

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
            (100 + user.characteristics.energy.toInt() * 10).coerceAtMost(255),
            floatArrayOf(hue, 0.7f, 1f)
        )

        // Фрактальная внешняя рамка
        if ((user.characteristics.energy.toInt() + rnd.nextInt(100)) % 2 == 0) {
            val margin = (width / 10f) + rnd.nextInt(0, 40)
            val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.STROKE
                strokeWidth = (2.5f + user.characteristics.energy * 0.35f)
                color = frameColor
                alpha = 180
            }

            // Рисуем фрактальный прямоугольник
            drawFractalRect(canvas, margin, margin, width - margin, height - margin, user, paint)
        }

        // Фрактальный символ зодиака
        val cx = width / 2f
        val cy = height / 2f
        val symbolPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 3f
            color = frameColor
            alpha = 140
        }
        drawFractalZodiacSymbol(canvas, user.zodiacSign, cx, cy, min(width, height) * 0.18f, user, symbolPaint)

        return bmp
    }

    private fun drawFractalZodiacSymbol(canvas: Canvas, zodiac: String, cx: Float, cy: Float, size: Float, user: UserData, paint: Paint) {
        when (zodiac.lowercase()) {
            "aries" -> {
                // Фрактальные дуги
                drawFractalArc(canvas, cx - size, cy - size, cx + size, cy + size, -140f, 80f, user, paint)
                drawFractalArc(canvas, cx - size, cy - size, cx + size, cy + size, -40f, 80f, user, paint)
            }
            "taurus" -> {
                // Фрактальные круги
                drawFractalCircle(canvas, cx, cy - size*0.4f, size*0.25f, user, paint)
                drawFractalRect(canvas, cx - size*0.6f, cy - size*0.1f, cx + size*0.6f, cy + size*0.2f, user, paint)
            }
            else -> {
                // Фрактальный круг с лучами
                drawFractalCircle(canvas, cx, cy, size, user, paint)
                for (i in 0 until 6) {
                    val a = (2 * Math.PI / 6 * i).toFloat()

                    // Фрактальная длина луча
                    val fractalLength = mandelbrotIterations(
                        cos(a.toDouble()) * 0.3,
                        sin(a.toDouble()) * 0.3,
                        50
                    ).toFloat() / 50f * size * 0.3f

                    val x = cx + cos(a) * (size + fractalLength)
                    val y = cy + sin(a) * (size + fractalLength)
                    drawFractalLine(canvas, cx, cy, x, y, user, paint)
                }
            }
        }
    }

    private fun drawFractalRect(canvas: Canvas, left: Float, top: Float, right: Float, bottom: Float, user: UserData, paint: Paint) {
        // Рисуем прямоугольник с фрактальными краями
        val path = Path()

        // Верхняя грань с фрактальными вариациями
        for (x in left.toInt()..right.toInt() step 5) {
            val fractalY = perlinNoise(x * 0.01f, top * 0.01f, user.auraSeed) * 5f
            val y = top + fractalY

            if (x == left.toInt()) {
                path.moveTo(x.toFloat(), y)
            } else {
                path.lineTo(x.toFloat(), y)
            }
        }

        // Правая грань
        for (y in top.toInt()..bottom.toInt() step 5) {
            val fractalX = perlinNoise(right * 0.01f, y * 0.01f, user.auraSeed + 1000) * 5f
            val x = right + fractalX

            path.lineTo(x, y.toFloat())
        }

        // Нижняя грань
        for (x in right.toInt() downTo left.toInt() step 5) {
            val fractalY = perlinNoise(x * 0.01f, bottom * 0.01f, user.auraSeed + 2000) * 5f
            val y = bottom + fractalY

            path.lineTo(x.toFloat(), y)
        }

        // Левая грань
        for (y in bottom.toInt() downTo top.toInt() step 5) {
            val fractalX = perlinNoise(left * 0.01f, y * 0.01f, user.auraSeed + 3000) * 5f
            val x = left + fractalX

            path.lineTo(x, y.toFloat())
        }

        path.close()
        canvas.drawPath(path, paint)
    }

    private fun drawFractalCircle(canvas: Canvas, cx: Float, cy: Float, radius: Float, user: UserData, paint: Paint) {
        val path = Path()
        val points = 100 + (user.characteristics.focus * 10).toInt()

        for (i in 0..points) {
            val angle = 2 * Math.PI * i / points

            // Фрактальная вариация радиуса
            val fractalVar = perlinNoise(
                cos(angle.toFloat()) * 0.5f,
                sin(angle.toFloat()) * 0.5f,
                user.auraSeed + i
            ) * radius * 0.1f

            val r = radius + fractalVar
            val x = cx + cos(angle) * r
            val y = cy + sin(angle) * r

            if (i == 0) path.moveTo(x.toFloat(), y.toFloat()) else path.lineTo(x.toFloat(), y.toFloat())
        }
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun drawFractalArc(canvas: Canvas, left: Float, top: Float, right: Float, bottom: Float,
                               startAngle: Float, sweepAngle: Float, user: UserData, paint: Paint) {
        val path = Path()
        val centerX = (left + right) / 2
        val centerY = (top + bottom) / 2
        val radius = (right - left) / 2

        val points = 50 + (user.characteristics.focus * 5).toInt()

        for (i in 0..points) {
            val angle = Math.toRadians((startAngle + sweepAngle * i / points).toDouble())

            // Фрактальная вариация радиуса
            val fractalVar = perlinNoise(
                cos(angle).toFloat() * 0.3f,
                sin(angle).toFloat() * 0.3f,
                user.auraSeed + i
            ) * radius * 0.15f

            val r = radius + fractalVar
            val x = centerX + cos(angle) * r
            val y = centerY + sin(angle) * r

            if (i == 0) path.moveTo(x.toFloat(), y.toFloat()) else path.lineTo(x.toFloat(), y.toFloat())
        }

        canvas.drawPath(path, paint)
    }

    private fun drawFractalLine(canvas: Canvas, x1: Float, y1: Float, x2: Float, y2: Float, user: UserData, paint: Paint) {
        val path = Path()
        val segments = 10 + (user.characteristics.energy / 2).toInt()

        for (i in 0..segments) {
            val t = i.toFloat() / segments
            val x = x1 + (x2 - x1) * t
            val y = y1 + (y2 - y1) * t

            // Фрактальное смещение
            val fractalX = perlinNoise(x * 0.01f, y * 0.01f, user.auraSeed + i) * 3f
            val fractalY = perlinNoise(y * 0.01f, x * 0.01f, user.auraSeed + i + 1000) * 3f

            val finalX = x + fractalX
            val finalY = y + fractalY

            if (i == 0) path.moveTo(finalX, finalY) else path.lineTo(finalX, finalY)
        }

        canvas.drawPath(path, paint)
    }

    private fun mandelbrotIterations(cr: Double, ci: Double, maxIter: Int): Int {
        var zr = 0.0
        var zi = 0.0
        var i = 0

        while (i < maxIter && zr * zr + zi * zi < 4.0) {
            val temp = zr * zr - zi * zi + cr
            zi = 2 * zr * zi + ci
            zr = temp
            i++
        }

        return i
    }

    private fun perlinNoise(x: Float, y: Float, seed: Long): Float {
        val rnd = Random(seed + (x * 1000).toLong() + (y * 1000).toLong())
        return rnd.nextFloat() * 2 - 1
    }
}