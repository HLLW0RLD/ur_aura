package com.example.ur_color.data.local.dataManager

import android.content.Context
import com.example.ur_color.App
import com.example.ur_color.data.local.storage.SystemStorage
import com.example.ur_color.ui.theme.ThemeMode
import com.example.ur_color.ui.theme.ThemePalette
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object SystemDataManager {
    private var initialized = false

    private val _theme = MutableStateFlow(ThemeMode.SYSTEM)
    val theme: StateFlow<ThemeMode> = _theme

    private val _palette = MutableStateFlow(ThemePalette.PINK)
    val palette: StateFlow<ThemePalette> = _palette

    suspend fun initialize() {
        if (initialized) return
        _theme.value = SystemStorage.loadTheme()
        _palette.value = SystemStorage.loadPalette()
        initialized = true
    }

    suspend fun saveTheme(theme: ThemeMode) {
        SystemStorage.saveTheme(theme)
        _theme.value = theme
    }

    suspend fun savePalette(palette: ThemePalette) {
        SystemStorage.savePalette(palette)
        _palette.value = palette
    }

    suspend fun clear() {
        SystemStorage.clear()
        _theme.value = ThemeMode.SYSTEM
        _palette.value = ThemePalette.PINK
        initialized = false
    }
}