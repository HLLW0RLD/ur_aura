package com.example.ur_color.data.repo

import android.content.Context
import android.graphics.Bitmap
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.utils.logError
import okhttp3.Cache

class UserRepository {

    suspend fun getUser(useCache: Boolean = false): UserData? {
        return if (useCache) {
            PersonalDataManager.getUser()
        } else {
            // api
            var user: UserData? = null
            try {
                // api
                user = PersonalDataManager.getUser()
            } catch (e: Exception) {
                user = PersonalDataManager.getUser()
                logError(e)
            }
            PersonalDataManager.saveUser(user)
            user
        }
    }

    suspend fun getAura(useCache: Boolean = false): Bitmap? {
        return if (useCache) {
            PersonalDataManager.getAura()
        } else {
            var aura: Bitmap? = null
            try {
                // api
                aura = PersonalDataManager.getAura()
            } catch (e: Exception) {
                aura = PersonalDataManager.getAura()
                logError(e)
            }
            PersonalDataManager.saveAura(aura)
            aura
        }
    }

    suspend fun getLvl(useCache: Boolean = false): Float {
        return if (useCache) {
            PersonalDataManager.getLvl()
        } else {
            // api
            var lvl: Float? = 1f
            try {
                // api
                lvl = PersonalDataManager.getLvl()
            } catch (e: Exception) {
                lvl = PersonalDataManager.getLvl()
                logError(e)
            }
            lvl
        }
    }
}