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

object DynamicLayerLibrary {

    fun vectorHistoryLayer(width: Int, height: Int, user: UserData): Bitmap {
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        val cx = width / 2f
        val cy = height / 2f
        val rnd = AuraUtils.seededRandom(user.auraSeed + 1234)

        drawEnergyRings(canvas, cx, cy, min(width, height) * 0.45f, user.characteristics.energyVector, user)
        drawMoodWaves(canvas, user.characteristics.moodVector, user, offsetY = height * 0.25f)
        drawStressSpikes(canvas, cx, cy, min(width, height) * 0.42f, user.characteristics.stressVector, user)
        drawHistoryParticles(canvas, width, height, user)

        return bmp
    }

    fun particleLayer(width: Int, height: Int, user: UserData): Bitmap {
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        val rnd = AuraUtils.seededRandom(user.auraSeed + 7777)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }

        val count = (30 + user.characteristics.physicalEnergy * 5 + user.characteristics.socialEnergy * 5).toInt()
        repeat(count) { i ->
            val x = rnd.nextFloat() * width
            val y = rnd.nextFloat() * height
            val size = rnd.nextFloat() * (3f + user.characteristics.charisma / 2f)
            val color = AuraUtils.getColorFromEnum(user.characteristics.fatigueVector, i % user.characteristics.fatigueVector.size)
            paint.color = AuraUtils.adjustAlpha(color, 0.4f + rnd.nextFloat() * 0.6f)
            canvas.drawCircle(x, y, size, paint)
        }

        return bmp
    }

    // --- приватные функции рисования слоёв ---
    private fun drawEnergyRings(canvas: Canvas, cx: Float, cy: Float, maxR: Float, vector: List<Float>, user: UserData) {
        val count = vector.size.coerceAtLeast(1)
        val step = maxR / (count + 1)
        vector.forEachIndexed { idx, v ->
            val normalized = v.coerceIn(0f, 10f) / 10f
            val r = step * (idx + 1)
            val stroke = 2f + normalized * (2f + user.characteristics.focus * 0.5f)
            val alpha = (80 + (normalized * 175)).toInt().coerceIn(20, 255)
            val color = AuraUtils.getColorFromEnum(user.characteristics.fatigueVector, idx)
            val p = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.STROKE
                strokeWidth = stroke
                this.color = AuraUtils.adjustAlpha(color, 1f)
                this.alpha = alpha
            }
            canvas.drawCircle(cx, cy, r, p)
        }
    }

    private fun drawMoodWaves(canvas: Canvas, vector: List<Float>, user: UserData, offsetY: Float) {
        if (vector.isEmpty()) return
        val width = canvas.width
        for (layer in 0 until 3) {
            val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.STROKE
                strokeWidth = 3f + user.characteristics.charisma * 0.2f / (layer + 1)
                color = AuraUtils.getColorFromEnum(user.characteristics.fatigueVector, layer)
                alpha = (60 + (100 / (layer + 1))).coerceAtMost(220)
            }
            val path = Path()
            val step = width.toFloat() / (vector.size - 1).coerceAtLeast(1)
            vector.forEachIndexed { i, v ->
                val x = i * step
                val normal = v.coerceIn(0f, 10f) / 10f
                val phase = (layer * 0.6f + i * 0.15f)
                val amplitude = 20f + user.characteristics.mood * 3f + normal * 30f
                val y = offsetY + sin(phase) * amplitude * normal * (1f / (layer + 1))
                if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
            }
            canvas.drawPath(path, paint)
        }
    }

    private fun drawStressSpikes(canvas: Canvas, cx: Float, cy: Float, baseR: Float, vector: List<Float>, user: UserData) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 1f + user.characteristics.focus * 0.2f
            color = AuraUtils.adjustAlpha(Color.GRAY, 0.9f)
            alpha = 110
        }
        val count = vector.size.coerceAtLeast(6)
        for (i in 0 until count) {
            val normal = vector.getOrNull(i)?.coerceIn(0f, 10f)?.div(10f) ?: 0.5f
            val angle = 2 * Math.PI * i / count
            val spikeLength = 8f + normal * (30f + user.characteristics.stress * 2f)
            val xStart = cx + cos(angle).toFloat() * (baseR * (0.7f + i * 0.01f))
            val yStart = cy + sin(angle).toFloat() * (baseR * (0.7f + i * 0.01f))
            val xEnd = cx + cos(angle).toFloat() * (baseR + spikeLength)
            val yEnd = cy + sin(angle).toFloat() * (baseR + spikeLength)
            canvas.drawLine(xStart, yStart, xEnd, yEnd, paint)
        }
    }

    private fun drawHistoryParticles(canvas: Canvas, width: Int, height: Int, user: UserData) {
        val cnt = (10 + user.characteristics.socialEnergy.toInt() * 6)
        val rnd = AuraUtils.seededRandom(user.auraSeed + 9999)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        repeat(cnt.toInt()) { i ->
            val px = rnd.nextFloat() * width
            val py = rnd.nextFloat() * height
            val size = 1f + rnd.nextFloat() * (user.characteristics.physicalEnergy / 2f + 3f)
            paint.color = AuraUtils.getColorFromEnum(user.characteristics.fatigueVector, i % user.characteristics.fatigueVector.size)
            paint.alpha = (30 + rnd.nextInt(160)).coerceAtMost(200)
            canvas.drawCircle(px, py, size, paint)
        }
    }
}

