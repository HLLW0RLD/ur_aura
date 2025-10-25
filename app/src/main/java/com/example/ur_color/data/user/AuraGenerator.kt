package com.example.ur_color.data.user

import android.content.Context
import android.graphics.Bitmap
import android.graphics.*
import android.graphics.BlurMaskFilter
import android.graphics.Shader
import com.example.ur_color.data.local.PrefCache
import kotlin.math.*
import kotlin.random.Random

object AuraGenerator {

    suspend fun generateBaseAura(context: Context, user: UserData): Bitmap {
        val bitmap = generateKuznetsovPattern(user)
        PrefCache.updateAura(context, bitmap)
        return bitmap
    }

    suspend fun generateDynamicAura(context: Context): Bitmap {
        val user = PrefCache.user.value ?: error("User not initialized")
        val base = PrefCache.aura.value ?: generateBaseAura(context, user)
        val updated = applyDynamicLayer(base, user)

        PrefCache.updateAura(context, updated)
        PrefCache.saveAuraToHistory(context, updated)

        return updated
    }

    private fun applyDynamicLayer(base: Bitmap, user: UserData): Bitmap {
        val result = base.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(result)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        // Более мягкое влияние энергии
        val hueShift = (user.energyLevel - 5) * 3f // раньше 10f
        val overlayColor = user.dominantColor?.let { Color.parseColor(it) }
            ?: Color.HSVToColor(floatArrayOf(hueShift, 0.5f, 1f))

        paint.shader = RadialGradient(
            result.width / 2f,
            result.height / 2f,
            result.width / 1.6f,
            overlayColor,
            Color.TRANSPARENT,
            Shader.TileMode.CLAMP
        )
        paint.alpha = (user.energyLevel * 15 + 50).coerceIn(60, 200)
        canvas.drawCircle(
            result.width / 2f,
            result.height / 2f,
            result.width / 2f,
            paint
        )

        // При высокой энергии — внутреннее сияние
        if (user.energyLevel > 7) {
            val glow = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = overlayColor
                maskFilter = BlurMaskFilter(result.width / 8f, BlurMaskFilter.Blur.OUTER)
                alpha = 80
            }
            canvas.drawCircle(result.width / 2f, result.height / 2f, result.width / 3f, glow)
        }

        // Добавляем мягкие блики от dominantColor, но не перекрашиваем ауру
        user.dominantColor?.let { hex ->
            val accent = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.parseColor(hex)
                alpha = 40
                style = Paint.Style.STROKE
                strokeWidth = 3f
            }
            val step = 30
            for (i in 0 until 360 step step) {
                val angle = Math.toRadians(i.toDouble())
                val r = result.width / 2.3f + (sin(angle) * 10)
                val x = result.width / 2f + cos(angle) * r
                val y = result.height / 2f + sin(angle) * r
                canvas.drawCircle(x.toFloat(), y.toFloat(), 4f, accent)
            }
        }

        return result
    }

    private fun generateKuznetsovPattern(user: UserData): Bitmap {
        val size = 1080
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        // Цветовая база по знаку зодиака
        val baseHue = when (user.zodiacSign) {
            "Aries", "Leo", "Sagittarius" -> 15f
            "Taurus", "Virgo", "Capricorn" -> 120f
            "Gemini", "Libra", "Aquarius" -> 200f
            "Cancer", "Scorpio", "Pisces" -> 260f
            else -> 180f
        }

        val energy = user.energyLevel.coerceIn(1, 10)
        val brightness = 0.5f + (energy * 0.04f)
        val saturation = 0.65f + (energy * 0.03f)

        // Мягкий фоновый градиент
        val bgShader = LinearGradient(
            0f, 0f, size.toFloat(), size.toFloat(),
            Color.HSVToColor(floatArrayOf(baseHue, saturation, brightness)),
            Color.HSVToColor(floatArrayOf((baseHue + 120f) % 360, saturation, brightness * 0.9f)),
            Shader.TileMode.MIRROR
        )
        paint.shader = bgShader
        canvas.drawRect(0f, 0f, size.toFloat(), size.toFloat(), paint)

        val rnd = Random(user.hashCode().toLong())
        paint.shader = null

        // --- Симметричные узоры ---
        val cx = size / 2f
        val cy = size / 2f

        // Кольцевые дуги
        for (r in 100..size / 2 step 80) {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 2f
            paint.color = Color.HSVToColor(
                (80..150).random(rnd),
                floatArrayOf((baseHue + r / 4f) % 360, 0.8f, 1f)
            )
            canvas.drawCircle(cx, cy, r.toFloat(), paint)
        }

        // Радиальные линии
        for (angle in 0 until 360 step 12) {
            val rad = Math.toRadians(angle.toDouble())
            val x = cx + cos(rad) * size / 2
            val y = cy + sin(rad) * size / 2
            paint.strokeWidth = 1.5f
            paint.alpha = 80
            canvas.drawLine(cx, cy, x.toFloat(), y.toFloat(), paint)
        }

        // Симметричные волновые орнаменты
        for (layer in 0 until 6) {
            val wavePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.HSVToColor(100, floatArrayOf((baseHue + layer * 20) % 360, 0.8f, 1f))
                alpha = 70
                style = Paint.Style.STROKE
                strokeWidth = 2f
            }
            val path = Path()
            val radius = size / 2.5f - layer * 60f
            for (a in 0..360 step 15) {
                val rad = Math.toRadians(a.toDouble())
                val offset = sin(rad * 3 + layer) * 20
                val x = cx + cos(rad) * (radius + offset)
                val y = cy + sin(rad) * (radius + offset)
                if (a == 0) path.moveTo(x.toFloat(), y.toFloat())
                else path.lineTo(x.toFloat(), y.toFloat())
            }
            path.close()
            canvas.drawPath(path, wavePaint)
        }

        // Центральное сияние
        val core = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            shader = RadialGradient(
                cx, cy, size / 3f,
                Color.HSVToColor(255, floatArrayOf(baseHue, 0.7f, 1f)),
                Color.TRANSPARENT,
                Shader.TileMode.CLAMP
            )
        }
        canvas.drawCircle(cx, cy, size / 3f, core)

        return bitmap
    }

    // V1_chaotic
//    /**
//     * Генерирует базовую ауру — используется при первом создании профиля.
//     * Основывается на статичных данных: имя, дата, знак, элемент и т.п.
//     */
//    suspend fun generateBaseAura(context: Context, user: UserData): Bitmap {
//        val bitmap = generateAuraPattern(user)
//        PrefCache.updateAura(context, bitmap)
//        return bitmap
//    }
//
//    /**
//     * Генерирует динамическую ауру — обновляет существующую в зависимости от текущих состояний.
//     */
//    suspend fun generateDynamicAura(context: Context): Bitmap {
//        val user = PrefCache.user.value ?: error("User not initialized")
//        val base = PrefCache.aura.value ?: generateBaseAura(context, user)
//        val updated = applyDynamicLayer(base, user)
//
//        PrefCache.updateAura(context, updated)
//        PrefCache.saveAuraToHistory(context, updated)
//
//        return updated
//    }
//
//    /**
//     * Применяет поверх базового изображения новые слои, отражающие текущее состояние.
//     */
//    private fun applyDynamicLayer(base: Bitmap, user: UserData): Bitmap {
//        val result = base.copy(Bitmap.Config.ARGB_8888, true)
//        val canvas = Canvas(result)
//        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
//
//        val hueShift = (user.energyLevel - 5) * 10f
//        val overlayColor = user.dominantColor?.let { Color.parseColor(it) }
//            ?: Color.HSVToColor(floatArrayOf(hueShift, 0.7f, 1f))
//
//        paint.shader = RadialGradient(
//            result.width / 2f,
//            result.height / 2f,
//            result.width / 1.5f,
//            overlayColor,
//            Color.TRANSPARENT,
//            Shader.TileMode.CLAMP
//        )
//        paint.alpha = (user.energyLevel * 25).coerceIn(60, 255)
//
//        canvas.drawCircle(
//            result.width / 2f,
//            result.height / 2f,
//            result.width / 2f,
//            paint
//        )
//
//        // Пульсирующий эффект при высокой энергии
//        if (user.energyLevel > 7) {
//            val pulsePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
//                color = overlayColor
//                maskFilter = BlurMaskFilter(result.width / 10f, BlurMaskFilter.Blur.OUTER)
//                alpha = 90
//            }
//            canvas.drawCircle(result.width / 2f, result.height / 2f, result.width / 3f, pulsePaint)
//        }
//
//        return result
//    }
//
//    /**
//     * Генерация базового геометрического узора (в стиле “Кузнецовское письмо”)
//     */
//    private fun generateAuraPattern(user: UserData): Bitmap {
//        val size = 1080
//        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(bitmap)
//        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
//
//        // Цветовая палитра по знаку зодиака и элементу
//        val baseHue = when (user.zodiacSign.lowercase()) {
//            "овен", "стрелец", "лев" -> 15f
//            "телец", "дева", "козерог" -> 120f
//            "близнецы", "весы", "водолей" -> 200f
//            "рак", "скорпион", "рыбы" -> 260f
//            else -> 180f
//        }
//
//        val energy = user.energyLevel.coerceIn(1, 10)
//        val brightness = 0.5f + (energy * 0.05f)
//        val saturation = 0.6f + (energy * 0.04f)
//
//        // Фон с мягким линейным градиентом
//        val bgShader = LinearGradient(
//            0f, 0f, size.toFloat(), size.toFloat(),
//            Color.HSVToColor(floatArrayOf(baseHue, saturation, brightness)),
//            Color.HSVToColor(floatArrayOf((baseHue + 90f) % 360, saturation, brightness * 0.8f)),
//            Shader.TileMode.MIRROR
//        )
//        paint.shader = bgShader
//        canvas.drawRect(0f, 0f, size.toFloat(), size.toFloat(), paint)
//
//        // Геометрические узоры в стиле "Кузнецовское письмо"
//        val rnd = Random(user.hashCode().toLong())
//        paint.shader = null
//        for (i in 0 until 120) {
//            val alpha = (50..130).random(rnd)
//            val stroke = (1..3).random(rnd).toFloat()
//            val color = Color.HSVToColor(alpha, floatArrayOf((baseHue + rnd.nextInt(180)) % 360, 0.9f, 1f))
//            paint.color = color
//            paint.strokeWidth = stroke
//
//            val x = rnd.nextInt(size)
//            val y = rnd.nextInt(size)
//            val radius = rnd.nextInt(size / 3).toFloat()
//
//            when (rnd.nextInt(3)) {
//                0 -> canvas.drawCircle(x.toFloat(), y.toFloat(), radius / 4, paint)
//                1 -> canvas.drawLine(x.toFloat(), y.toFloat(), x + radius / 2, y + radius / 2, paint)
//                2 -> canvas.drawArc(
//                    x - radius / 2, y - radius / 2, x + radius / 2, y + radius / 2,
//                    rnd.nextInt(360).toFloat(), rnd.nextInt(90).toFloat(), false, paint
//                )
//            }
//        }
//
//        // Радиальные точки, формирующие "молитвенный" узор
//        val dotPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE }
//        for (a in 0 until 360 step 5) {
//            val r = size / 2.5f
//            val x = size / 2f + cos(Math.toRadians(a.toDouble())) * r
//            val y = size / 2f + sin(Math.toRadians(a.toDouble())) * r
//            canvas.drawCircle(x.toFloat(), y.toFloat(), (2..5).random(rnd).toFloat(), dotPaint)
//        }
//
//        // Центр — "ядро ауры"
//        val corePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
//            shader = RadialGradient(
//                size / 2f, size / 2f, size / 4f,
//                Color.HSVToColor(255, floatArrayOf(baseHue, 0.8f, 1f)),
//                Color.TRANSPARENT,
//                Shader.TileMode.CLAMP
//            )
//        }
//        canvas.drawCircle(size / 2f, size / 2f, size / 4f, corePaint)
//
//        return bitmap
//    }
}


