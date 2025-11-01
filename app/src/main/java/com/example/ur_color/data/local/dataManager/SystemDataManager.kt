package com.example.ur_color.data.local.dataManager

import android.content.Context
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

    suspend fun initialize(context: Context) {
        if (initialized) return
        _theme.value = SystemStorage.loadTheme(context)
        _palette.value = SystemStorage.loadPalette(context)
        initialized = true
    }

    suspend fun saveTheme(context: Context, theme: ThemeMode) {
        SystemStorage.saveTheme(context, theme)
        _theme.value = theme
    }

    suspend fun savePalette(context: Context, palette: ThemePalette) {
        SystemStorage.savePalette(context, palette)
        _palette.value = palette
    }

    suspend fun clear(context: Context) {
        SystemStorage.clear(context)
        _theme.value = ThemeMode.SYSTEM
        _palette.value = ThemePalette.PINK
        initialized = false
    }
}