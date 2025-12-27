package com.example.ur_color.data.local.dataManager

import android.content.Context
import android.graphics.Bitmap
import com.example.ur_color.data.local.storage.AuraStorage
//import com.example.ur_color.data.local.HistoryStorage
import com.example.ur_color.data.local.storage.UserStorage
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.utils.logError

object PersonalDataManager {

    suspend fun getUser(context: Context): UserData? {
        return UserStorage.load(context)
    }

    suspend fun getLvl(context: Context): Float {
        return UserStorage.loadLvl(context)?.toFloat() ?: 1f
    }

    suspend fun getAura(context: Context): Bitmap? {
        return AuraStorage.load(context)
    }

    suspend fun saveUser(context: Context, user: UserData? = null) {
        user?.let {
            UserStorage.save(context, it)
        }
    }

    suspend fun saveAura(context: Context, aura: Bitmap? = null) {
        aura?.let {
            AuraStorage.save(context, it)
        }
    }

    suspend fun saveDailyTestDate(context: Context, date: String) {
        date.let { UserStorage.saveDailyTestDate(context, it) }
    }

    suspend fun loadDailyTestDate(context: Context): String? {
        return UserStorage.loadDailyTestDate(context)
    }

    suspend fun level(context: Context, lvl: Float = 0.1f) {
        try {
            val level = UserStorage.loadLvl(context)?.toFloat() ?: 1f
            UserStorage.saveLvl(context, level + lvl)
        } catch (e: Exception) {
            logError(e.message)
        }
    }

    suspend fun delete(context: Context) {
        UserStorage.delete(context)
        AuraStorage.delete(context)
    }
}