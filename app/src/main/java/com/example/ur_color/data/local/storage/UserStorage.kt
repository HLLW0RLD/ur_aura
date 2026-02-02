package com.example.ur_color.data.local.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.ur_color.App
import com.example.ur_color.data.model.user.UserData
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

object UserStorage {
    val context = App.instance

    private const val USER_PREFERENCES = "user_prefs"
    private val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES)

    private val USER_KEY = stringPreferencesKey("user_json")
    private val DAILY_TEST_KEY = stringPreferencesKey("daily_test_date")
    private val USER_LVL_KEY = floatPreferencesKey("user_lvl")
    private val USER_ACHIEVEMENTS_KEY = stringPreferencesKey("user_achievements")

    private val gson = Gson()

    //USER
    suspend fun save(user: UserData) {
        context.dataStore.edit { prefs ->
            prefs[USER_KEY] = gson.toJson(user)
        }
    }

    suspend fun delete() {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_KEY)
            prefs.remove(DAILY_TEST_KEY)
            prefs.remove(USER_LVL_KEY)
            prefs.remove(USER_ACHIEVEMENTS_KEY)
        }
    }

    suspend fun load(): UserData? {
        val json = context.dataStore.data.first()[USER_KEY] ?: return null
        return gson.fromJson(json, UserData::class.java)
    }

    // DAILY TEST
    suspend fun saveDailyTestDate(date: String) {
        context.dataStore.edit { prefs ->
            prefs[DAILY_TEST_KEY] = date
        }
    }

    suspend fun loadDailyTestDate(): String? {
        return context.dataStore.data.first()[DAILY_TEST_KEY]
    }

    // USER LVL
    suspend fun saveLvl(lvl: Float) {
        context.dataStore.edit { prefs ->
            prefs[USER_LVL_KEY] = lvl
        }
    }

    suspend fun loadLvl(): Float? {
        return context.dataStore.data.first()[USER_LVL_KEY]
    }

    // USER ACHIEVEMENTS
    suspend fun saveAchievementId(achievementId: String) {
        val current = loadAchievements() ?: emptyList()
        if (achievementId !in current) {
            val updated = current + achievementId
            saveAchievements(updated)
        }
    }

    suspend fun loadAchievements(): List<String>? {
        val json = context.dataStore.data.first()[USER_ACHIEVEMENTS_KEY]
        return if (json.isNullOrBlank()) {
            emptyList()
        } else {
            try {
                Json.decodeFromString(json)
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    private suspend fun saveAchievements(achievements: List<String>) {
        context.dataStore.edit { prefs ->
            prefs[USER_ACHIEVEMENTS_KEY] = Json.encodeToString(achievements)
        }
    }
}