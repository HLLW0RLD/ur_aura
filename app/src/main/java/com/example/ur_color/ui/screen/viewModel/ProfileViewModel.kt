package com.example.ur_color.ui.screen.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.user.UserData
import com.example.ur_color.data.local.PrefCache
import com.example.ur_color.data.user.AuraGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class ProfileViewModel() : ViewModel() {

    private val _user = MutableStateFlow<UserData?>(null)
    val user: StateFlow<UserData?> = _user

    private val _aura = MutableStateFlow<Bitmap?>(null)
    val aura: StateFlow<Bitmap?> = _aura

    init {
        viewModelScope.launch {
            _user.value = PrefCache.user.value
            _aura.value = PrefCache.aura.value
        }
    }

    fun saveUser(context: Context, userData: UserData, auraBitmap: Bitmap?) {
        viewModelScope.launch {
            PrefCache.saveUser(context, userData, auraBitmap)
            _user.value = userData
            _aura.value = auraBitmap ?: PrefCache.aura.value
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun update(
        context: Context,
        energyLevel: Int? = null,
        dominantColor: String? = null,
        element: String? = null,
    ) {
        viewModelScope.launch(Dispatchers.Default) {

            PrefCache.updateDynamicUserState(
                context,
                energyLevel = energyLevel,
                dominantColor = dominantColor,
                element = element,
            )
            AuraGenerator.generateDynamicAura(context)
        }
    }

    suspend fun randomizeDynamicUserState(context: Context) {
        val current = _user.value ?: return
        val rnd = Random(System.currentTimeMillis())

        // 🎲 Энергия — от 1 до 7
        val energyLevel = rnd.nextInt(1, 8)

        // 🎨 Базовые цвета
        val colorPalette = listOf(
           "red",
           "green",
           "blue",
           "yellow",
           "magenta",
           "cyan",
        )
        val dominantColor = colorPalette.random(rnd)

        // 🔹 Элемент (пока просто случайный, можно позже связать со знаком)
        val elements = listOf("fire", "water", "air", "earth", "aether", "metal")
        val element = elements.random(rnd)

        val updated = current.copy(
            energyLevel = energyLevel,
            dominantColor = dominantColor,
            element = element
        )

        saveUser(context, updated, _aura.value)
    }

    fun deleteUser(context: Context) {
        viewModelScope.launch {
            PrefCache.deleteUser(context)
            _user.value = null
            _aura.value = null
        }
    }
}