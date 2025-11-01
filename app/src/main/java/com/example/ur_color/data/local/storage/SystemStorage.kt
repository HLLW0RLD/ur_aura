package com.example.ur_color.data.local.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.ur_color.ui.theme.ThemeMode
import com.example.ur_color.ui.theme.ThemePalette
import kotlinx.coroutines.flow.firstOrNull

object SystemStorage {
    private const val SYSTEM_PREFERENCES = "system_preferences"
    private val Context.dataStore by preferencesDataStore(name = SYSTEM_PREFERENCES)

    private val THEME_KEY = stringPreferencesKey("selected_theme")
    private val PALETTE_KEY = stringPreferencesKey("selected_palette")

    suspend fun saveTheme(context: Context, theme: ThemeMode) {
        context.dataStore.edit { it[THEME_KEY] = theme.name }
    }

    suspend fun savePalette(context: Context, palette: ThemePalette) {
        context.dataStore.edit { it[PALETTE_KEY] = palette.name }
    }

    suspend fun loadTheme(context: Context): ThemeMode {
        val prefs = context.dataStore.data.firstOrNull()
        return prefs?.get(THEME_KEY)?.let { ThemeMode.valueOf(it) } ?: ThemeMode.SYSTEM
    }

    suspend fun loadPalette(context: Context): ThemePalette {
        val prefs = context.dataStore.data.firstOrNull()
        return prefs?.get(PALETTE_KEY)?.let { ThemePalette.valueOf(it) } ?: ThemePalette.PINK
    }

    suspend fun clear(context: Context) {
        context.dataStore.edit {
            it.remove(THEME_KEY)
            it.remove(PALETTE_KEY)
        }
    }
}
