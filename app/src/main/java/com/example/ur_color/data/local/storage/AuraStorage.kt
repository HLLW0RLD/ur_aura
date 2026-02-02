package com.example.ur_color.data.local.storage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.ur_color.App
import java.io.File

object AuraStorage {
    val context = App.instance

    private const val IMAGE_FILE = "user_aura.png"

    fun save(bitmap: Bitmap) {
        File(context.filesDir, IMAGE_FILE).outputStream().use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }

    fun load(): Bitmap? {
        val file = File(context.filesDir, IMAGE_FILE)
        return if (file.exists()) BitmapFactory.decodeFile(file.absolutePath) else null
    }

    fun delete() {
        val file = File(context.filesDir, IMAGE_FILE)
        if (file.exists()) file.delete()
    }
}