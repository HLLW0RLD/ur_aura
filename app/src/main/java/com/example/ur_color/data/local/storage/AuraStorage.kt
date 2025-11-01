package com.example.ur_color.data.local.storage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File

object AuraStorage {
    private const val IMAGE_FILE = "user_aura.png"

    fun save(context: Context, bitmap: Bitmap) {
        File(context.filesDir, IMAGE_FILE).outputStream().use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }

    fun load(context: Context): Bitmap? {
        val file = File(context.filesDir, IMAGE_FILE)
        return if (file.exists()) BitmapFactory.decodeFile(file.absolutePath) else null
    }

    fun delete(context: Context) {
        val file = File(context.filesDir, IMAGE_FILE)
        if (file.exists()) file.delete()
    }
}