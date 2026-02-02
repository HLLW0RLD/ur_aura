package com.example.ur_color.data.local.dataManager

import android.content.Context
import android.graphics.Bitmap
import com.example.ur_color.data.local.storage.AuraStorage
//import com.example.ur_color.data.local.HistoryStorage
import com.example.ur_color.data.local.storage.UserStorage
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.utils.logDebug
import com.example.ur_color.utils.logError

object PersonalDataManager {

    suspend fun updateUser(
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

    suspend fun getUser(): UserData? {
        return UserStorage.load()
    }

    suspend fun getLvl(): Float {
        return UserStorage.loadLvl()?.toFloat() ?: 1f
    }

    suspend fun getAura(): Bitmap? {
        return AuraStorage.load()
    }

    suspend fun saveUser(user: UserData? = null) {
        user?.let {
            UserStorage.save(it)
        }
    }

    suspend fun saveAura(aura: Bitmap? = null) {
        aura?.let {
            AuraStorage.save(it)
        }
    }

    suspend fun saveDailyTestDate(date: String) {
        date.let { UserStorage.saveDailyTestDate(it) }
    }

    suspend fun loadDailyTestDate(): String? {
        return UserStorage.loadDailyTestDate()
    }

    suspend fun updateLevel(lvl: Float = 0.1f) {
        try {
            val level = UserStorage.loadLvl()?.toFloat() ?: 1f
            UserStorage.saveLvl(level + lvl)
        } catch (e: Exception) {
            logError(e.message)
        }
    }

    suspend fun setAchievement(achievement: String) {
        UserStorage.saveAchievementId(achievement)
    }

    suspend fun getAchievements(): List<String>? {
        return UserStorage.loadAchievements()
    }

    suspend fun delete() {
        UserStorage.delete()
        AuraStorage.delete()
    }
}