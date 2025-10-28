package com.example.ur_color.data.local

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.ur_color.App
import com.example.ur_color.data.user.UserData
import com.example.ur_color.ui.theme.ThemeMode
import com.example.ur_color.ui.theme.ThemePalette
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val Context.dataStore by preferencesDataStore("user_prefs")

private val USER_KEY = stringPreferencesKey("user_json")
private val THEME_KEY = stringPreferencesKey("selected_theme")
private val PALETTE_KEY = stringPreferencesKey("palette")

private const val IMAGE_FILE = "user_aura.png"
private const val HISTORY_FILE = "aura_state_history.json"

object PrefCache {
    private val gson = Gson()

    private val _user = MutableStateFlow<UserData?>(null)
    val user: StateFlow<UserData?> = _user

    private val _aura = MutableStateFlow<Bitmap?>(null)
    val aura: StateFlow<Bitmap?> = _aura

    private var _selectedTheme: ThemeMode? = null
    var selectedTheme: ThemeMode
        get() = _selectedTheme ?: ThemeMode.SYSTEM
        private set(value) {
            _selectedTheme = value
            themeChanged.value++
        }

    private var _selectedPalette: ThemePalette? = null
    var selectedPalette: ThemePalette
        get() = _selectedPalette ?: ThemePalette.PINK
        private set(value) {
            _selectedPalette = value
            themeChanged.value++
        }

    val themeChanged = mutableStateOf(0)

    suspend fun saveTheme(context: Context, theme: ThemeMode) {
        context.dataStore.edit { it[THEME_KEY] = theme.name }
        selectedTheme = theme
    }

    suspend fun savePalette(context: Context, palette: ThemePalette) {
        context.dataStore.edit { it[PALETTE_KEY] = palette.name }
        selectedPalette = palette
    }


    suspend fun initialize(context: Context) {
        loadTheme(context)
        loadUser(context)
        _aura.value = loadAura(context)
    }

    suspend fun loadTheme(context: Context) {
        context.dataStore.data.firstOrNull()?.let { prefs ->
            _selectedTheme = prefs[THEME_KEY]?.let { ThemeMode.valueOf(it) }
            _selectedPalette = prefs[PALETTE_KEY]?.let { ThemePalette.valueOf(it) }
        }
    }

    private suspend fun loadUser(context: Context) {
        context.dataStore.data.first()[USER_KEY]?.let {
            _user.value = gson.fromJson(it, UserData::class.java)
        }
    }

    suspend fun saveUser(context: Context, userData: UserData, auraBitmap: Bitmap? = null) {
        context.dataStore.edit { it[USER_KEY] = gson.toJson(userData) }
        _user.value = userData
        auraBitmap?.let { saveAura(context, it) }
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    suspend fun updateDynamicUserState(
        context: Context,
        energyLevel: Int? = null,
        dominantColor: String? = null,
        element: String? = null
    ) {
        val current = _user.value ?: return
        val updated = current.copy(
            energyLevel = energyLevel ?: current.energyLevel,
            energyCapacity = if (energyLevel != null) (current.energyCapacity + energyLevel).takeLast(3) else current.energyCapacity,
            dominantColor = dominantColor ?: current.dominantColor,
            colorVector = if (dominantColor != null) (current.colorVector + dominantColor).takeLast(3) else current.colorVector,
            element = element ?: current.element,
        )
        saveUserStateHistory(context, updated)
        saveUser(context, updated, _aura.value)
    }

    fun updateAura(context: Context, newAura: Bitmap) {
        saveAura(context, newAura)
        _aura.value = newAura
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    private fun saveUserStateHistory(context: Context, user: UserData) {
        val file = File(context.filesDir, HISTORY_FILE)
        val list: MutableList<UserData> = if (file.exists()) {
            gson.fromJson(file.readText(), object : TypeToken<MutableList<UserData>>() {}.type)
        } else mutableListOf()
        list.add(user)
        if (list.size > 10) list.removeFirst()
        file.writeText(gson.toJson(list))
    }

    private fun saveAura(context: Context, bitmap: Bitmap) {
        File(context.filesDir, IMAGE_FILE).outputStream().use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }

    private fun loadAura(context: Context): Bitmap? {
        val file = File(context.filesDir, IMAGE_FILE)
        return if (file.exists()) BitmapFactory.decodeFile(file.absolutePath) else null
    }

    suspend fun deleteUser(context: Context) {
        context.dataStore.edit { it.clear() }
        File(context.filesDir, IMAGE_FILE).takeIf { it.exists() }?.delete()
        _user.value = null
        _aura.value = null
    }
}
