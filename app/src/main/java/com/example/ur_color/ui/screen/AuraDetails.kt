package com.example.ur_color.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.ur_color.data.local.PrefCache
import com.example.ur_color.utils.formatDateInput
import com.example.ur_color.utils.generatePatternAura
import kotlinx.serialization.Serializable

@Serializable
data class AuraDetails(val color: String? = null) : Screen

@Composable
fun AuraDetailsScreen(cd: AuraDetails) {
    val bitmapState = PrefCache.aura.collectAsState(initial = null)


    Box(modifier = Modifier.fillMaxSize()) {
        bitmapState.value?.let { bmp ->
            Image(
                bitmap = bmp.asImageBitmap(),
                contentDescription = "Сгенерированная картина",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop // можно изменить на Fit, если нужно
            )
        }
    }
}