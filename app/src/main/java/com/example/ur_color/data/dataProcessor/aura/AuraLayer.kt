package com.example.ur_color.data.dataProcessor.aura

import android.graphics.Bitmap
import android.graphics.Canvas

object AuraLayer {
    fun combine(layers: List<Bitmap>): Bitmap {
        require(layers.isNotEmpty()) { "Список слоёв не может быть пустым" }

        val base = layers.first().copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(base)
        layers.drop(1).forEach { layer ->
            canvas.drawBitmap(layer, 0f, 0f, null)
        }
        return base
    }

    fun overlay(base: Bitmap, layers: List<Bitmap>): Bitmap {
        val result = base.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(result)
        layers.forEach { layer ->
            canvas.drawBitmap(layer, 0f, 0f, null)
        }
        return result
    }
}