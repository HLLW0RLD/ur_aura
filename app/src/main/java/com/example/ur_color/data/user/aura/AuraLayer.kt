package com.example.ur_color.data.user.aura

import android.graphics.Bitmap
import android.graphics.Canvas

object AuraLayer {
    fun combine(layers: List<Bitmap>): Bitmap {
        require(layers.isNotEmpty())
        val result = layers.first().copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(result)
        layers.drop(1).forEach { canvas.drawBitmap(it, 0f, 0f, null) }
        return result
    }

    fun overlay(base: Bitmap, layers: List<Bitmap>): Bitmap {
        val result = base.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(result)
        layers.forEach { canvas.drawBitmap(it, 0f, 0f, null) }
        return result
    }
}