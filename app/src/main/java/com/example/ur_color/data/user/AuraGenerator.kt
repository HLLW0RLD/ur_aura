package com.example.ur_color.data.user

import android.graphics.Bitmap
import android.graphics.*
import android.graphics.Shader
import kotlin.math.*
import kotlin.random.Random

object AuraGenerator {

    // =============================
    // 🎛 Основная функция генерации ауры
    // =============================
    fun generateAura(user: UserData, width: Int = 1080, height: Int = 1080): Bitmap {
        val base = generateBaseLayer(width, height)
        val shapes = generateDynamicShapes(width, height, user)
        val frame = generateZodiacFrame(width, height, user)
        val dots = generateEnergyDots(width, height, user)
        val lines = generateDynamicLines(width, height, user)
        val bigFrames = generateBigFrames(width, height, user)

        return combineLayers(base, listOf(shapes, frame, dots, lines, bigFrames))
    }

    fun updateAura(existing: Bitmap, user: UserData): Bitmap {
        val result = existing.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(result)
        val rnd = Random(user.auraSeed)

        addSymmetricShapes(canvas, user, rnd)
        addEnergyDots(canvas, user, rnd)
        addClockwiseLines(canvas, user, rnd)
        addBigFrames(canvas, user, rnd)

        return result
    }

    // =============================
    // 🌀 1. Базовый слой
    // =============================
    private fun generateBaseLayer(width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val gradient = LinearGradient(
            0f, 0f, width.toFloat(), height.toFloat(),
            intArrayOf(Color.BLACK, Color.DKGRAY),
            null,
            Shader.TileMode.CLAMP
        )
        val paint = Paint().apply { shader = gradient }
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        return bitmap
    }

    // =============================
    // 🔺 2. Динамические фигуры
    // =============================
    private val shapeTemplates = listOf("circle","polygon","star","spiral","hexagon","petal","wave","cross")

    private fun generateDynamicShapes(width: Int, height: Int, user: UserData): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        addSymmetricShapes(canvas, user, Random(user.auraSeed))
        return bitmap
    }

    private fun addSymmetricShapes(canvas: Canvas, user: UserData, rnd: Random) {
        val energyAvg = user.energyCapacity.average().toInt()
        val shapeCount = (6 + energyAvg).coerceAtMost(12)
        val positions = getSymmetricPositions(canvas, shapeCount)
        val activeShapes = mutableListOf<String>()
        if (user.dominantColor.lowercase() == "red") activeShapes.addAll(listOf("polygon","star","hexagon"))
        if (user.dominantColor.lowercase() == "blue") activeShapes.addAll(listOf("circle","wave"))
        if (user.dominantColor.lowercase() == "green") activeShapes.addAll(listOf("spiral","petal","cross"))
        if (user.element?.lowercase() == "fire") activeShapes.addAll(listOf("star","spiral"))
        if (user.element?.lowercase() == "water") activeShapes.addAll(listOf("wave","circle"))
        if (activeShapes.isEmpty()) activeShapes.add("circle")

        positions.forEach { (x, y) ->
            val shapeType = activeShapes[rnd.nextInt(activeShapes.size)]
            val size = 15 + energyAvg * 4 + rnd.nextInt(0, 15)
            val complexity = when(shapeType){
                "polygon","hexagon" -> 3 + energyAvg/2
                "star","petal" -> 5 + energyAvg/3
                "spiral","wave" -> 2 + energyAvg/2
                else -> 4
            }
            val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = getOverlayColor(user.dominantColor, energyAvg)
                alpha = 70 + rnd.nextInt(50)
                style = Paint.Style.STROKE
                strokeWidth = 2f + rnd.nextFloat()*2f
            }
            drawShapeByType(canvas, shapeType, x.toDouble(), y.toDouble(), size, complexity, paint)
        }
    }

    private fun getSymmetricPositions(canvas: Canvas, count: Int): List<Pair<Float, Float>> {
        val cx = canvas.width / 2f
        val cy = canvas.height / 2f
        val radius = min(cx, cy) * 0.7f
        return List(count) { i ->
            val angle = 2 * Math.PI * i / count
            (cx + cos(angle) * radius).toFloat() to (cy + sin(angle) * radius).toFloat()
        }
    }

    private fun drawShapeByType(
        canvas: Canvas,
        type: String,
        x: Double,
        y: Double,
        size: Int,
        complexity: Int,
        paint: Paint
    ) {
        when (type) {
            "circle" -> canvas.drawCircle(x.toFloat(), y.toFloat(), size.toFloat(), paint)
            "polygon" -> drawPolygon(canvas, x, y, complexity, size.toFloat(), paint)
            "hexagon" -> drawPolygon(canvas, x, y, 6, size.toFloat(), paint)
            "star" -> drawStar(canvas, x, y, complexity, paint)
            "spiral" -> drawSpiral(canvas, x, y, complexity, paint)
            "petal" -> drawPetal(canvas, x, y, complexity, size.toFloat(), paint)
            "wave" -> drawWave(canvas, x, y, complexity, size.toFloat(), paint)
            "cross" -> drawCross(canvas, x, y, size.toFloat(), paint)
        }
    }

    // =============================
    // ✨ Точки энергии
    // =============================
    private fun generateEnergyDots(width:Int,height:Int,user:UserData): Bitmap{
        val bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val rnd = Random(user.auraSeed + 999)
        val cx = width/2f
        val cy = height/2f
        val color = getOverlayColor(user.dominantColor,user.energyLevel)

        val dotCount = 15 + user.energyLevel*4
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        repeat(dotCount){ i ->
            val angle = 2*Math.PI*i/dotCount + rnd.nextDouble()*0.5
            val distance = width/4 + rnd.nextDouble()*width/4
            val x = cx + cos(angle)*distance
            val y = cy + sin(angle)*distance

            paint.color=color
            paint.alpha=50 + rnd.nextInt(50)
            canvas.drawCircle(x.toFloat(),y.toFloat(),3f + rnd.nextFloat()*(user.energyLevel/2f),paint)
        }

        return bitmap
    }

    private fun addEnergyDots(canvas: Canvas, user: UserData, rnd: Random) {
        val dotCount = 20 + user.energyLevel * 4
        val cx = canvas.width/2f
        val cy = canvas.height/2f
        val color = getOverlayColor(user.dominantColor, user.energyLevel)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        repeat(dotCount){ i ->
            val angle = 2*Math.PI*i/dotCount + rnd.nextDouble()
            val distance = rnd.nextDouble()*(canvas.width/2.0)
            val x = cx + cos(angle)*distance
            val y = cy + sin(angle)*distance
            paint.color=color
            paint.alpha=50 + rnd.nextInt(80)
            canvas.drawCircle(x.toFloat(),y.toFloat(),3f + rnd.nextFloat()*(user.energyLevel/2f),paint)
        }
    }

    // =============================
    // 🧩 Линии по часовой стрелке
    // =============================
    private fun generateDynamicLines(width:Int,height:Int,user:UserData): Bitmap {
        val bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        addClockwiseLines(canvas, user, Random(user.auraSeed))
        return bitmap
    }

    private fun addClockwiseLines(canvas: Canvas, user: UserData, rnd: Random, clockwise: Boolean = true) {
        val cx = canvas.width / 2f
        val cy = canvas.height / 2f
        val lineCount = 20 + user.energyLevel * 2
        val maxRadius = min(cx, cy) * 0.9f
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = getOverlayColor(user.dominantColor, user.energyLevel)
            alpha = 80
            strokeWidth = 2f
            style = Paint.Style.STROKE
        }

        for (i in 0 until lineCount) {
            val t = i.toFloat() / lineCount
            val angle = if(clockwise) 2*Math.PI*t else 2*Math.PI*(1-t)
            val rStart = maxRadius * 0.3f
            val rEnd = maxRadius
            val xStart = cx + cos(angle) * rStart
            val yStart = cy + sin(angle) * rStart
            val xEnd = cx + cos(angle) * rEnd
            val yEnd = cy + sin(angle) * rEnd
            canvas.drawLine(xStart.toFloat(), yStart.toFloat(), xEnd.toFloat(), yEnd.toFloat(), paint)
        }
    }

    // =============================
    // 🖼 Большие рамки
    // =============================
    private fun generateBigFrames(width:Int,height:Int,user:UserData): Bitmap {
        val bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        addBigFrames(canvas,user,Random(user.auraSeed+999))
        return bitmap
    }

    private fun addBigFrames(canvas: Canvas,user:UserData,rnd: Random){
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply{
            style=Paint.Style.STROKE
            strokeWidth=3f + rnd.nextFloat()*5f
            color=getOverlayColor(user.dominantColor,user.energyLevel)
            alpha=50 + rnd.nextInt(50)
        }
        val steps = 3 + user.energyLevel / 2
        for(i in 0 until steps){
            val margin = 20 + i * 60 + rnd.nextInt(0,40)
            canvas.drawRect(
                margin.toFloat(), margin.toFloat(),
                (canvas.width-margin).toFloat(), (canvas.height-margin).toFloat(),
                paint
            )
        }
    }

    // =============================
    // ♈ Рамка по зодиаку
    // =============================
    private fun generateZodiacFrame(width:Int,height:Int,user:UserData): Bitmap{
        val bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val rnd = Random(user.auraSeed + 777)
        val hueOffset = getZodiacHue(user.zodiacSign)
        val color = Color.HSVToColor(floatArrayOf(hueOffset,0.8f,1f))

        if((user.energyLevel+rnd.nextInt(100))%2==0){
            val margin = (width/10f)+rnd.nextInt(0,40)
            val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply{
                style = Paint.Style.STROKE
                strokeWidth = 2.5f + user.energyLevel*0.4f
                this.color=color
                alpha=90
            }
            canvas.drawRect(margin,margin,width-margin,height-margin,paint)
        }
        return bitmap
    }

    // =============================
    // 🧩 Комбинирование слоев
    // =============================
    private fun combineLayers(base: Bitmap,layers: List<Bitmap>): Bitmap{
        val result = base.copy(Bitmap.Config.ARGB_8888,true)
        val canvas = Canvas(result)
        layers.forEach{ canvas.drawBitmap(it,0f,0f,null) }
        return result
    }

    // =============================
    // ⚙️ Вспомогательные функции
    // =============================
    private fun getColorHueOffset(color:String):Float = when(color.lowercase()){
        "red"->0f;"orange"->30f;"yellow"->50f;"green"->120f;"blue"->210f;"purple","violet"->280f
        else->180f
    }

    private fun getZodiacHue(zodiac:String):Float = when(zodiac.lowercase()){
        "aries","leo","sagittarius"->15f
        "taurus","virgo","capricorn"->120f
        "gemini","libra","aquarius"->200f
        "cancer","scorpio","pisces"->260f
        else->180f
    }

    private fun getOverlayColor(color:String,energy:Int):Int{
        val hsv = floatArrayOf(getColorHueOffset(color),0.9f,1f)
        return Color.HSVToColor(100+energy*10,hsv)
    }

    // =============================
    // Рисование фигур
    // =============================
    private fun drawPolygon(canvas: Canvas,cx:Double,cy:Double,sides:Int,r:Float,paint: Paint){
        val path = Path()
        for(i in 0..sides){
            val a = Math.toRadians((i*360f/sides).toDouble())
            val x = cx + cos(a)*r
            val y = cy + sin(a)*r
            if(i==0) path.moveTo(x.toFloat(),y.toFloat()) else path.lineTo(x.toFloat(),y.toFloat())
        }
        path.close()
        canvas.drawPath(path,paint)
    }

    private fun drawStar(canvas: Canvas,cx:Double,cy:Double,points:Int,paint: Paint){
        val path = Path()
        for(i in 0 until points*2){
            val a = Math.toRadians((i*180f/points).toDouble())
            val r = if(i%2==0) 40f else 15f
            val x = cx + cos(a)*r
            val y = cy + sin(a)*r
            if(i==0) path.moveTo(x.toFloat(),y.toFloat()) else path.lineTo(x.toFloat(),y.toFloat())
        }
        path.close()
        canvas.drawPath(path,paint)
    }

    private fun drawSpiral(canvas: Canvas,cx:Double,cy:Double,complexity:Int,paint: Paint){
        val path = Path()
        val turns = 2 + complexity
        var a=0.0
        while(a<2*Math.PI*turns){
            val r = 10 + a*5
            val x = cx + cos(a)*r
            val y = cy + sin(a)*r
            if(a==0.0) path.moveTo(x.toFloat(),y.toFloat()) else path.lineTo(x.toFloat(),y.toFloat())
            a+=0.2
        }
        canvas.drawPath(path,paint)
    }

    private fun drawPetal(canvas: Canvas,cx:Double,cy:Double,points:Int,r:Float,paint: Paint){
        val path = Path()
        for(i in 0 until points){
            val angle = i*360.0/points
            val x = cx + cos(Math.toRadians(angle))*r
            val y = cy + sin(Math.toRadians(angle))*r
            if(i==0) path.moveTo(x.toFloat(),y.toFloat()) else path.lineTo(x.toFloat(),y.toFloat())
        }
        path.close()
        canvas.drawPath(path,paint)
    }

    private fun drawWave(canvas: Canvas,cx:Double,cy:Double,amplitude:Int,length:Float,paint: Paint){
        val path = Path()
        for(i in 0..360 step 10){
            val angle = Math.toRadians(i.toDouble())
            val x = cx + i/2
            val y = cy + sin(angle)*amplitude
            if(i==0) path.moveTo(x.toFloat(),y.toFloat()) else path.lineTo(x.toFloat(),y.toFloat())
        }
        canvas.drawPath(path,paint)
    }

    private fun drawCross(canvas: Canvas,cx:Double,cy:Double,r:Float,paint: Paint){
        canvas.drawLine((cx-r).toFloat(),cy.toFloat(),(cx+r).toFloat(),cy.toFloat(),paint)
        canvas.drawLine(cx.toFloat(),(cy-r).toFloat(),cx.toFloat(),(cy+r).toFloat(),paint)
    }
}


