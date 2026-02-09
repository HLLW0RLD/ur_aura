package com.example.ur_color.data.local.dataManager

import android.graphics.Bitmap
import com.example.ur_color.data.local.storage.AuraStorage
//import com.example.ur_color.data.local.HistoryStorage
import com.example.ur_color.data.local.storage.UserStorage
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.utils.logError

object PersonalDataManager {

    suspend fun updateUserCache(
        about: String? = null,
        avatar: String? = null
    ) {
        val u = UserStorage.load()
        when {
            about != null && avatar != null -> {
                val current = u?.copy(about = about, avatarUri = avatar)
                current?.let {
                    UserStorage.save(it)
                }
            }
            about != null -> {
                val current = u?.copy(about = about)
                current?.let {
                    UserStorage.save(it)
                }
            }
            avatar != null -> {
                val current = u?.copy(avatarUri = avatar)
                current?.let {
                    UserStorage.save(it)
                }
            }
        }
    }

    suspend fun getUserFromCache(): UserData? {
        return UserStorage.load()
    }

    suspend fun getAuraFromCache(): Bitmap? {
        return AuraStorage.load()
    }

    suspend fun saveUserToCache(user: UserData? = null) {
        user?.let {
            UserStorage.save(it)
        }
    }

    suspend fun saveAuraToCache(aura: Bitmap? = null) {
        aura?.let {
            AuraStorage.save(it)
        }
    }

    suspend fun updateLevel(lvl: Float = 0.1f) {
        try {
            val level = UserStorage.loadLvl()?.toFloat() ?: 1f
            UserStorage.saveLvl(level + lvl)
        } catch (e: Exception) {
            logError(e.message)
        }
    }

    suspend fun delete() {
        UserStorage.delete()
        AuraStorage.delete()
    }


    // need to be from api
    suspend fun saveDailyTestDate(date: String) {
        date.let { UserStorage.saveDailyTestDate(it) }
    }
    suspend fun loadDailyTestDate(): String? {
        return UserStorage.loadDailyTestDate()
    }

    suspend fun setAchievement(achievement: String) {
        UserStorage.saveAchievementId(achievement)
    }

    suspend fun getAchievements(): List<String>? {
        return UserStorage.loadAchievements()
    }
}