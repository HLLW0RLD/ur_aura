package com.example.ur_color.data.local.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.ur_color.data.user.UserData
import com.google.gson.Gson
import kotlinx.coroutines.flow.first

object UserStorage {
    private const val USER_PREFERENCES = "user_prefs"
    private val Context.dataStore by preferencesDataStore(name = USER_PREFERENCES)

    private val USER_KEY = stringPreferencesKey("user_json")

    private val gson = Gson()

    suspend fun save(context: Context, user: UserData) {
        context.dataStore.edit { prefs ->
            prefs[USER_KEY] = gson.toJson(user)
        }
    }

    suspend fun load(context: Context): UserData? {
        val json = context.dataStore.data.first()[USER_KEY] ?: return null
        return gson.fromJson(json, UserData::class.java)
    }

    suspend fun delete(context: Context) {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_KEY)
        }
    }
}