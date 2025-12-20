package com.example.ur_color.data.local.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.ur_color.data.model.user.UserData
import com.google.gson.Gson
import kotlinx.coroutines.flow.first

object UserStorage {
    private const val USER_PREFERENCES = "user_prefs"
    private val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES)

    private val USER_KEY = stringPreferencesKey("user_json")
    private val DAILY_TEST_KEY = stringPreferencesKey("daily_test_date")
    private val USER_LVL_KEY = stringPreferencesKey("user_lvl")

    private val gson = Gson()

    //USER
    suspend fun save(context: Context, user: UserData) {
        context.dataStore.edit { prefs ->
            prefs[USER_KEY] = gson.toJson(user)
        }
    }

    suspend fun delete(context: Context) {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_KEY)
            prefs.remove(DAILY_TEST_KEY)
            prefs.remove(USER_LVL_KEY)
        }
    }

    suspend fun load(context: Context): UserData? {
        val json = context.dataStore.data.first()[USER_KEY] ?: return null
        return gson.fromJson(json, UserData::class.java)
    }

    // DAILY TEST
    suspend fun saveDailyTestDate(context: Context, date: String) {
        context.dataStore.edit { prefs ->
            prefs[DAILY_TEST_KEY] = date
        }
    }

    suspend fun loadDailyTestDate(context: Context): String? {
        return context.dataStore.data.first()[DAILY_TEST_KEY]
    }

    // USER LVL
    suspend fun saveLvl(context: Context, lvl: Float) {
        context.dataStore.edit { prefs ->
            prefs[USER_LVL_KEY] = gson.toJson(lvl)
        }
    }

    suspend fun loadLvl(context: Context): String? {
        return context.dataStore.data.first()[USER_LVL_KEY]
    }
}