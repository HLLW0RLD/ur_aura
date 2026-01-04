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


enum class ShapeType { CIRCLE, POLYGON, STAR, SPIRAL, HEXAGON, PETAL, WAVE, CROSS, RINGS, DOTS }

object PatternLibrary {

    fun backgroundLayer(width: Int, height: Int, user: UserData): Bitmap {
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)

        val primary = AuraUtils.getColorFromEnum(user.characteristics.fatigueVector, 0)
        val secondary = AuraUtils.adjustAlpha(primary, 0.6f)

        // Фрактальный градиент вместо линейного
        drawFractalGradient(canvas, width, height, primary, secondary, user)

        return bmp
    }

    fun basePattern(width: Int, height: Int, user: UserData): Bitmap {
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        val rnd = AuraUtils.seededRandom(user.auraSeed)
        val chosen = ShapeType.values()[rnd.nextInt(ShapeType.values().size)]

        // Все фигуры теперь рисуются фрактально
        drawFractalPattern(canvas, chosen, width, height, user, rnd)
        return bmp
    }

    fun archetypeLayer(width: Int, height: Int, user: UserData): Bitmap {
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        val rnd = AuraUtils.seededRandom(user.auraSeed + (user.personalityType?.hashCode() ?: 0))
        val chosen = when (user.personalityType?.lowercase()) {
            "explorer" -> ShapeType.SPIRAL
            "thinker" -> ShapeType.POLYGON
            "artist" -> ShapeType.PETAL
            else -> ShapeType.RINGS
        }

        drawFractalPattern(canvas, chosen, width, height, user, rnd, intensity = 0.6f)
        return bmp
    }

    fun symbolLayer(width: Int, height: Int, user: UserData): Bitmap {
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        val rnd = AuraUtils.seededRandom(user.auraSeed + 555)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = AuraUtils.getColorFromEnum(user.characteristics.fatigueVector, 1)
            alpha = 90
        }

        val cx = width / 2f
        val cy = height / 2f

        // Фрактальное расположение символов
        repeat(8) { i ->
            // Используем углы золотого сечения для более естественного распределения
            val goldenAngle = 2.399963 * i // 137.508 градусов в радианах
            val r = min(width, height) * (0.15f + perlinNoise(i * 0.1f, 0f, user.auraSeed) * 0.1f)

            val x = cx + cos(goldenAngle) * r
            val y = cy + sin(goldenAngle) * r

            // Размер символа зависит от фрактальной сложности
            val size = 6f + mandelbrotIterations(
                x.toDouble() / width * 2 - 1,
                y.toDouble() / height * 2 - 1,
                30
            ).toFloat() / 30f * 8f

            canvas.drawCircle(x.toFloat(), y.toFloat(), size, paint)
        }

        return bmp
    }

    // Основная функция рисования фрактальных паттернов
    private fun drawFractalPattern(
        canvas: Canvas,
        shape: ShapeType,
        width: Int,
        height: Int,
        user: UserData,
        rnd: Random,
        intensity: Float = 1f
    ) {
        val cx = width / 2f
        val cy = height / 2f
        val baseColor = AuraUtils.getColorFromEnum(user.characteristics.fatigueVector, 0)

        when (shape) {
            ShapeType.CIRCLE -> {
                val rings = 3 + user.characteristics.energy.toInt() / 2
                for (i in 0 until rings) {
                    val r = min(width, height) * (0.12f + i * 0.06f)
                    // Фрактальные круги
                    drawFractalRing(canvas, cx, cy, r, baseColor, user, intensity)
                }
            }
            ShapeType.POLYGON, ShapeType.HEXAGON -> {
                val sides = if (shape == ShapeType.HEXAGON) 6 else (3 + user.characteristics.charisma.toInt() / 3)
                val layers = 2 + user.characteristics.energy.toInt() / 3
                for (l in 0 until layers) {
                    // Фрактальные многоугольники
                    drawFractalPolygon(canvas, cx, cy, sides, (60 + l * 20).toFloat() * intensity,
                        baseColor, user, intensity)
                }
            }
            ShapeType.STAR -> {
                // Фрактальная звезда
                drawFractalStar(canvas, cx, cy, 5 + user.characteristics.charisma.toInt() / 4,
                    baseColor, user, intensity)
            }
            ShapeType.SPIRAL -> {
                // Улучшенная фрактальная спираль
                drawFractalSpiral(canvas, cx, cy, (2 + user.characteristics.energy.toInt() / 2),
                    baseColor, user, intensity)
            }
            ShapeType.PETAL -> {
                // Фрактальные лепестки
                drawFractalPetal(canvas, cx, cy, 6 + user.characteristics.charisma.toInt() / 3,
                    80f * intensity, baseColor, user, intensity)
            }
            ShapeType.WAVE -> {
                // Фрактальные волны
                drawFractalWave(canvas, cx, cy, 20 + user.characteristics.energy.toInt() * 3,
                    min(width, height).toFloat() * 0.8f, baseColor, user, intensity)
            }
            ShapeType.CROSS -> {
                // Фрактальный крест
                drawFractalCross(canvas, cx, cy, 40f + user.characteristics.energy.toInt() * 5,
                    baseColor, user, intensity)
            }
            ShapeType.RINGS -> {
                for (i in 0 until 5) {
                    val rr = 40f + i * 60f + rnd.nextFloat() * 30f
                    drawFractalRing(canvas, cx, cy, rr, baseColor, user, intensity)
                }
            }
            ShapeType.DOTS -> {
                // Фрактальное распределение точек (Julia set)
                drawFractalDots(canvas, width, height, user, intensity)
            }
        }
    }

    // --- Фрактальные версии вспомогательных функций ---
    private fun drawFractalRing(canvas: Canvas, cx: Float, cy: Float, radius: Float,
                                baseColor: Int, user: UserData, intensity: Float) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = (2f + user.characteristics.energy * 0.3f) * intensity
            color = baseColor
            alpha = (80 + user.characteristics.mood.toInt() * 10).coerceAtMost(230)
        }

        // Вместо обычного круга - фрактальный контур
        val points = 200 + (user.characteristics.focus * 20).toInt()

        val path = Path()
        for (i in 0..points) {
            val angle = 2 * Math.PI * i / points

            // Фрактальная вариация радиуса
            val fractalVar = mandelbrotIterations(
                cos(angle) * 0.2,
                sin(angle) * 0.2,
                50
            ).toFloat() / 50f * (radius * 0.08f * intensity)

            val r = radius + fractalVar
            val x = cx + cos(angle).toFloat() * r
            val y = cy + sin(angle).toFloat() * r

            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun drawFractalPolygon(canvas: Canvas, cx: Float, cy: Float, sides: Int,
                                   radius: Float, baseColor: Int, user: UserData, intensity: Float) {
        if (sides < 3) return

        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = (2f + user.characteristics.energy * 0.3f) * intensity
            color = baseColor
            alpha = (80 + user.characteristics.mood.toInt() * 10).coerceAtMost(230)
        }

        val path = Path()
        for (i in 0..sides) {
            val angle = 2 * Math.PI * i / sides

            // Фрактальное смещение каждой вершины
            val fractalVarX = perlinNoise(cos(angle).toFloat(), sin(angle).toFloat(), user.auraSeed + i) *
                    radius * 0.1f * intensity
            val fractalVarY = perlinNoise(sin(angle).toFloat(), cos(angle).toFloat(), user.auraSeed + i + 1000) *
                    radius * 0.1f * intensity

            val x = cx + (cos(angle).toFloat() * radius) + fractalVarX
            val y = cy + (sin(angle).toFloat() * radius) + fractalVarY

            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun drawFractalStar(
        canvas: Canvas, cx: Float, cy: Float, points: Int,
        baseColor: Int, user: UserData, intensity: Float
    ) {
        if (points < 2) return

        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = (2f + user.characteristics.energy * 0.3f) * intensity
            color = baseColor
            alpha = (80 + user.characteristics.mood.toInt() * 10).coerceAtMost(230)
        }

        val path = Path()
        val outerR = 40.0 * intensity
        val innerR = 20.0 * intensity

        for (i in 0..points * 2) {
            val r = if (i % 2 == 0) outerR else innerR
            val angle = Math.PI * i / points

            // Фрактальное смещение точек звезды
            val iterations = mandelbrotIterations(
                cos(angle) * 0.3,
                sin(angle) * 0.3,
                30
            )
            val fractalVar = iterations.toFloat() / 30f * (r * 0.15f).toFloat()

            val x = cx + (r + fractalVar) * cos(angle)
            val y = cy + (r + fractalVar) * sin(angle)

            if (i == 0) path.moveTo(x.toFloat(), y.toFloat()) else path.lineTo(x.toFloat(), y.toFloat())
        }
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun drawFractalSpiral(
        canvas: Canvas, cx: Float, cy: Float, complexity: Int,
        baseColor: Int, user: UserData, intensity: Float
    ) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = (2f + user.characteristics.energy * 0.3f) * intensity
            color = baseColor
            alpha = (80 + user.characteristics.mood.toInt() * 10).coerceAtMost(230)
        }

        val path = Path()
        var angle = 0.0
        val step = 0.1
        val maxRadius = (100.0 + complexity * 20) * intensity

        while (angle < Math.PI * complexity * 2) {
            val baseR = maxRadius * angle / (Math.PI * complexity * 2)

            // Фрактальная вариация радиуса спирали
            val fractalVar = mandelbrotIterations(
                cos(angle) * 0.2,
                sin(angle) * 0.2,
                40
            ).toFloat() / 40f * (baseR * 0.1f).toFloat()

            val r = baseR + fractalVar
            val x = cx + r * cos(angle)
            val y = cy + r * sin(angle)

            if (angle == 0.0) path.moveTo(x.toFloat(), y.toFloat()) else path.lineTo(x.toFloat(), y.toFloat())
            angle += step
        }
        canvas.drawPath(path, paint)
    }

    private fun drawFractalPetal(
        canvas: Canvas, cx: Float, cy: Float, points: Int,
        radius: Float, baseColor: Int, user: UserData, intensity: Float
    ) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = (2f + user.characteristics.energy * 0.3f) * intensity
            color = baseColor
            alpha = (80 + user.characteristics.mood.toInt() * 10).coerceAtMost(230)
        }

        val path = Path()
        for (i in 0..points) {
            val angle = 2 * Math.PI * i / points

            // Фрактальная деформация лепестков
            val fractalVar = perlinNoise(
                cos(angle).toFloat() * 0.5f,
                sin(angle).toFloat() * 0.5f,
                user.auraSeed + i
            ) * radius * 0.15f

            val r = radius + fractalVar
            val x = cx + r * cos(angle)
            val y = cy + r * sin(angle)

            if (i == 0) {
                path.moveTo(cx.toFloat(), cy.toFloat())
                path.quadTo(x.toFloat(), y.toFloat(), cx.toFloat(), cy.toFloat())
            } else {
                path.quadTo(x.toFloat(), y.toFloat(), cx.toFloat(), cy.toFloat())
            }
        }
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun drawFractalWave(
        canvas: Canvas, cx: Float, cy: Float, amplitude: Int,
        length: Float, baseColor: Int, user: UserData, intensity: Float
    ) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = (2f + user.characteristics.energy * 0.3f) * intensity
            color = baseColor
            alpha = (80 + user.characteristics.mood.toInt() * 10).coerceAtMost(230)
        }

        val path = Path()
        val width = canvas.width
        val points = 100 + (user.characteristics.focus * 10).toInt()

        for (i in 0..points) {
            val x = i * width / points.toFloat()

            // Основная волна
            val baseY = sin(i.toDouble() * 2 * Math.PI / points * 5) * amplitude

            // Фрактальный шум
            val fractalNoise = perlinNoise(x * 0.01f, 0f, user.auraSeed) * amplitude * 0.5f

            val y = cy + baseY + fractalNoise

            if (i == 0) path.moveTo(x, y.toFloat()) else path.lineTo(x, y.toFloat())
        }
        canvas.drawPath(path, paint)
    }

    private fun drawFractalCross(
        canvas: Canvas, cx: Float, cy: Float, length: Float,
        baseColor: Int, user: UserData, intensity: Float
    ) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = (2f + user.characteristics.energy * 0.3f) * intensity
            color = baseColor
            alpha = (80 + user.characteristics.mood.toInt() * 10).coerceAtMost(230)
        }

        // Горизонтальная линия с фрактальными вариациями
        val hPath = Path()
        for (x in (cx - length).toInt()..(cx + length).toInt() step 5) {
            val fractalY = perlinNoise(x.toFloat() * 0.01f, 0f, user.auraSeed) * length * 0.05f
            val y = cy + fractalY

            if (x == (cx - length).toInt()) {
                hPath.moveTo(x.toFloat(), y.toFloat())
            } else {
                hPath.lineTo(x.toFloat(), y.toFloat())
            }
        }
        canvas.drawPath(hPath, paint)

        // Вертикальная линия с фрактальными вариациями
        val vPath = Path()
        for (y in (cy - length).toInt()..(cy + length).toInt() step 5) {
            val fractalX = perlinNoise(0f, y.toFloat() * 0.01f, user.auraSeed + 1000) * length * 0.05f
            val x = cx + fractalX

            if (y == (cy - length).toInt()) {
                vPath.moveTo(x.toFloat(), y.toFloat())
            } else {
                vPath.lineTo(x.toFloat(), y.toFloat())
            }
        }
        canvas.drawPath(vPath, paint)
    }

    private fun drawFractalDots(canvas: Canvas, width: Int, height: Int, user: UserData, intensity: Float) {
        val count = (50 + user.characteristics.energy.toInt() * 10) * intensity.toInt()
        val rnd = AuraUtils.seededRandom(user.auraSeed)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = AuraUtils.getColorFromEnum(user.characteristics.fatigueVector, 0)
        }

        // Используем фрактал Джулии для распределения точек
        val cReal = -0.4 + user.characteristics.mood * 0.05
        val cImag = 0.6 + user.characteristics.energy * 0.05

        repeat(count) {
            val x = rnd.nextFloat() * width
            val y = rnd.nextFloat() * height

            // Проверяем, попадает ли точка во множество Джулии
            val iterations = juliaIterations(
                (x / width * 3 - 1.5).toDouble(),
                (y / height * 3 - 1.5).toDouble(),
                cReal,
                cImag,
                30
            )

            if (iterations < 30) {
                paint.alpha = (30 + iterations * 7).coerceAtMost(200)
                val size = 2f + (iterations.toFloat() / 30f) * 6f * intensity
                canvas.drawCircle(x, y, size, paint)
            }
        }
    }

    private fun drawFractalGradient(canvas: Canvas, width: Int, height: Int,
                                    color1: Int, color2: Int, user: UserData) {
        val paint = Paint()

        // Фрактальное заполнение пикселей
        for (y in 0 until height step 2) {
            for (x in 0 until width step 2) {
                // Используем фрактальную функцию для определения цвета
                val iterations = mandelbrotIterations(
                    (x.toDouble() / width * 3 - 2.0),
                    (y.toDouble() / height * 3 - 1.5),
                    100
                )

                val interp = if (iterations < 100) {
                    iterations.toFloat() / 100f
                } else {
                    val noise = perlinNoise(x * 0.01f, y * 0.01f, user.auraSeed)
                    (noise + 1) / 2
                }

                val r = Color.red(color1) + (Color.red(color2) - Color.red(color1)) * interp
                val g = Color.green(color1) + (Color.green(color2) - Color.green(color1)) * interp
                val b = Color.blue(color1) + (Color.blue(color2) - Color.blue(color1)) * interp

                paint.color = Color.rgb(r.toInt(), g.toInt(), b.toInt())
                canvas.drawRect(x.toFloat(), y.toFloat(),
                    (x + 2).toFloat(), (y + 2).toFloat(), paint)
            }
        }
    }

    // Вспомогательные фрактальные функции (добавляем в этот же объект)
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

    private fun juliaIterations(zr: Double, zi: Double, cr: Double, ci: Double, maxIter: Int): Int {
        var zr = zr
        var zi = zi
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