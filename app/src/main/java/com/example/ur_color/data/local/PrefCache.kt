package com.example.ur_color.data.local

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.ur_color.data.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import java.io.File
import java.io.FileOutputStream

val Context.dataStore by preferencesDataStore("user_prefs")

object PrefCache {

    private val KEY_FIRST = stringPreferencesKey("firstName")
    private val KEY_LAST = stringPreferencesKey("lastName")
    private val KEY_MIDDLE = stringPreferencesKey("middleName")
    private val KEY_DATE = stringPreferencesKey("birthDate")
    private val KEY_TIME = stringPreferencesKey("birthTime")
    private val KEY_PLACE = stringPreferencesKey("birthPlace")
    private val KEY_GENDER = stringPreferencesKey("gender")
    private val KEY_ZODIAC = stringPreferencesKey("zodiacSign")

    private const val IMAGE_FILE = "user_aura.png"

    private val _user = MutableStateFlow<UserData?>(null)
    val user: StateFlow<UserData?> = _user

    private val _aura = MutableStateFlow<Bitmap?>(null)
    val aura: StateFlow<Bitmap?> = _aura


    suspend fun initialize(context: Context) {
        val prefs = context.dataStore.data.first()
        val first = prefs[KEY_FIRST] ?: ""
        val last = prefs[KEY_LAST] ?: ""
        if (first.isBlank() || last.isBlank()) {
            _user.value = null
        } else {
            _user.value = UserData(
                firstName = first,
                lastName = last,
                middleName = prefs[KEY_MIDDLE]?.ifBlank { null },
                birthDate = prefs[KEY_DATE] ?: "",
                birthTime = prefs[KEY_TIME] ?: "",
                birthPlace = prefs[KEY_PLACE] ?: "",
                gender = prefs[KEY_GENDER] ?: "",
                zodiacSign = prefs[KEY_ZODIAC] ?: ""
            )
        }

        _aura.value = loadAuraFromFile(context)
    }

    suspend fun saveUser(context: Context, userData: UserData, auraBitmap: Bitmap?) {
        context.dataStore.edit { prefs ->
            prefs[KEY_FIRST] = userData.firstName
            prefs[KEY_LAST] = userData.lastName
            prefs[KEY_MIDDLE] = userData.middleName ?: ""
            prefs[KEY_DATE] = userData.birthDate
            prefs[KEY_TIME] = userData.birthTime ?: ""
            prefs[KEY_PLACE] = userData.birthPlace
            prefs[KEY_GENDER] = userData.gender
            prefs[KEY_ZODIAC] = userData.zodiacSign
        }

        if (auraBitmap != null) {
            saveAuraToFile(context, auraBitmap)
        } else {
            val f = File(context.filesDir, IMAGE_FILE)
            if (f.exists()) f.delete()
        }

        _user.value = userData
        _aura.value = auraBitmap ?: loadAuraFromFile(context)
    }

    private fun saveAuraToFile(context: Context, bitmap: Bitmap) {
        val file = File(context.filesDir, IMAGE_FILE)
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.flush()
        }
    }

    private fun loadAuraFromFile(context: Context): Bitmap? {
        val file = File(context.filesDir, IMAGE_FILE)
        return if (file.exists()) {
            BitmapFactory.decodeFile(file.absolutePath)
        } else {
            null
        }
    }
}