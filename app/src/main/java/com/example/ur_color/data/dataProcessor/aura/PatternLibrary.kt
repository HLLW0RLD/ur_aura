package com.example.ur_color.data.dataProcessor.aura

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Shader
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

        val primary = AuraUtils.getColorFromEnum(user.characteristics.colorVector, 0, "#FFFFFF")
        val secondary = AuraUtils.adjustAlpha(primary, 0.6f)

        val shader = LinearGradient(
            0f, 0f, width.toFloat(), height.toFloat(),
            intArrayOf(primary, secondary),
            null,
            Shader.TileMode.CLAMP
        )

        val paint = Paint().apply { this.shader = shader }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

        return bmp
    }

    fun basePattern(width: Int, height: Int, user: UserData): Bitmap {
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        val rnd = AuraUtils.seededRandom(user.auraSeed)
        val chosen = ShapeType.values()[rnd.nextInt(ShapeType.values().size)]
        drawPatternChoice(canvas, chosen, width, height, user, rnd)
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
        drawPatternChoice(canvas, chosen, width, height, user, rnd, intensity = 0.6f)
        return bmp
    }

    fun symbolLayer(width: Int, height: Int, user: UserData): Bitmap {
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        val rnd = AuraUtils.seededRandom(user.auraSeed + 555)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = AuraUtils.getColorFromEnum(user.characteristics.colorVector, 1, "#FFFFFF")
            alpha = 90
        }

        val cx = width / 2f
        val cy = height / 2f
        repeat(8) { i ->
            val a = 2 * Math.PI * i / 8
            val r = min(width, height) * (0.18f + rnd.nextFloat() * 0.12f)
            val x = cx + cos(a) * r
            val y = cy + sin(a) * r
            canvas.drawCircle(x.toFloat(), y.toFloat(), 6f + rnd.nextFloat() * 8f, paint)
        }

        return bmp
    }

    private fun drawPatternChoice(
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
        val baseColor = AuraUtils.getColorFromEnum(user.characteristics.colorVector, 0, "#FFFFFF")

        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            color = baseColor
            strokeWidth = (2f + user.characteristics.energyLevel * 0.3f) * intensity
            alpha = (80 + user.characteristics.mood * 10).coerceAtMost(230)
        }

        when (shape) {
            ShapeType.CIRCLE -> {
                val rings = 3 + user.characteristics.energyLevel / 2
                for (i in 0 until rings) {
                    val r = min(width, height) * (0.12f + i * 0.06f)
                    canvas.drawCircle(cx, cy, r, paint)
                }
            }
            ShapeType.POLYGON, ShapeType.HEXAGON -> {
                val sides = if (shape == ShapeType.HEXAGON) 6 else (3 + user.characteristics.creativity / 3)
                val layers = 2 + user.characteristics.energyLevel / 3
                for (l in 0 until layers) {
                    drawPolygon(canvas, cx.toDouble(), cy.toDouble(), sides, (60 + l * 20).toFloat() * intensity, paint)
                }
            }
            ShapeType.STAR -> drawStar(canvas, cx.toDouble(), cy.toDouble(), 5 + user.characteristics.creativity / 4, paint)
            ShapeType.SPIRAL -> drawSpiral(canvas, cx.toDouble(), cy.toDouble(), (2 + user.characteristics.energyLevel / 2), paint)
            ShapeType.PETAL -> drawPetal(canvas, cx.toDouble(), cy.toDouble(), 6 + user.characteristics.creativity / 3, 80f * intensity, paint)
            ShapeType.WAVE -> drawWave(canvas, cx.toDouble(), cy.toDouble(), 20 + user.characteristics.energyLevel * 3, min(width, height).toFloat() * 0.8f, paint)
            ShapeType.CROSS -> drawCross(canvas, cx.toDouble(), cy.toDouble(), 40f + user.characteristics.energyLevel * 5, paint)
            ShapeType.RINGS -> for (i in 0 until 5) {
                val rr = 40f + i * 60f + rnd.nextFloat() * 30f
                canvas.drawCircle(cx, cy, rr, paint)
            }
            ShapeType.DOTS -> {
                val count = 50 + user.characteristics.energyLevel * 10
                val p = Paint(paint).apply { style = Paint.Style.FILL }
                repeat(count) {
                    val x = rnd.nextFloat() * width
                    val y = rnd.nextFloat() * height
                    canvas.drawCircle(x, y, 2f + rnd.nextFloat() * 6f, p)
                }
            }
        }
    }

    // --- вспомогательные функции для отрисовки ---
    private fun drawPolygon(canvas: Canvas, cx: Double, cy: Double, sides: Int, r: Float, paint: Paint) {
        if (sides < 3) return
        val path = Path()
        for (i in 0 until sides) {
            val angle = 2 * Math.PI * i / sides
            val x = cx + r * cos(angle)
            val y = cy + r * sin(angle)
            if (i == 0) path.moveTo(x.toFloat(), y.toFloat()) else path.lineTo(x.toFloat(), y.toFloat())
        }
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun drawStar(canvas: Canvas, cx: Double, cy: Double, points: Int, paint: Paint) {
        if (points < 2) return
        val path = Path()
        val outerR = 40.0
        val innerR = 20.0
        for (i in 0 until points * 2) {
            val r = if (i % 2 == 0) outerR else innerR
            val angle = Math.PI * i / points
            val x = cx + r * cos(angle)
            val y = cy + r * sin(angle)
            if (i == 0) path.moveTo(x.toFloat(), y.toFloat()) else path.lineTo(x.toFloat(), y.toFloat())
        }
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun drawSpiral(canvas: Canvas, cx: Double, cy: Double, complexity: Int, paint: Paint) {
        val path = Path()
        var angle = 0.0
        val step = 0.1
        val maxRadius = 100.0 + complexity * 20
        while (angle < Math.PI * complexity * 2) {
            val r = maxRadius * angle / (Math.PI * complexity * 2)
            val x = cx + r * cos(angle)
            val y = cy + r * sin(angle)
            if (angle == 0.0) path.moveTo(x.toFloat(), y.toFloat()) else path.lineTo(x.toFloat(), y.toFloat())
            angle += step
        }
        canvas.drawPath(path, paint)
    }

    private fun drawPetal(canvas: Canvas, cx: Double, cy: Double, points: Int, r: Float, paint: Paint) {
        val path = Path()
        for (i in 0 until points) {
            val angle = 2 * Math.PI * i / points
            val x = cx + r * cos(angle)
            val y = cy + r * sin(angle)
            if (i == 0) path.moveTo(cx.toFloat(), cy.toFloat())
            path.quadTo(x.toFloat(), y.toFloat(), cx.toFloat(), cy.toFloat())
        }
        path.close()
        canvas.drawPath(path, paint)
    }

    private fun drawWave(canvas: Canvas, cx: Double, cy: Double, amplitude: Int, length: Float, paint: Paint) {
        val path = Path()
        val width = canvas.width
        val points = 50
        for (i in 0..points) {
            val x = i * width / points.toFloat()
            val y = cy + sin(i.toDouble() * 2 * Math.PI / points) * amplitude
            if (i == 0) path.moveTo(x, y.toFloat()) else path.lineTo(x, y.toFloat())
        }
        canvas.drawPath(path, paint)
    }

    private fun drawCross(canvas: Canvas, cx: Double, cy: Double, r: Float, paint: Paint) {
        canvas.drawLine((cx - r).toFloat(), cy.toFloat(), (cx + r).toFloat(), cy.toFloat(), paint)
        canvas.drawLine(cx.toFloat(), (cy - r).toFloat(), cx.toFloat(), (cy + r).toFloat(), paint)
    }

}