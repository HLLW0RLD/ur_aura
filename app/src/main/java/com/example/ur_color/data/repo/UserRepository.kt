package com.example.ur_color.data.repo

import android.graphics.Bitmap
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.data.remote.UserApi
import com.example.ur_color.utils.logError

class UserRepository(
    val api: UserApi
) {

    suspend fun getUser(useCache: Boolean = false): UserData? {
        return if (useCache) {
            PersonalDataManager.getUserFromCache()
        } else {
            // api
            var user: UserData? = null
            try {
                // api
                user = PersonalDataManager.getUserFromCache()
            } catch (e: Exception) {
                user = PersonalDataManager.getUserFromCache()
                logError(e)
            }
            PersonalDataManager.saveUserToCache(user)
            user
        }
    }

    suspend fun getAura(useCache: Boolean = false): Bitmap? {
        return if (useCache) {
            PersonalDataManager.getAuraFromCache()
        } else {
            var aura: Bitmap? = null
            try {
                // api
                aura = PersonalDataManager.getAuraFromCache()
            } catch (e: Exception) {
                aura = PersonalDataManager.getAuraFromCache()
                logError(e)
            }
            PersonalDataManager.saveAuraToCache(aura)
            aura
        }
    }
}