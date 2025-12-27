package com.example.ur_color.data.repo

import android.content.Context
import android.graphics.Bitmap
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.utils.logError
import okhttp3.Cache

class UserRepository {

    suspend fun getUser(context: Context, useCache: Boolean = false): UserData? {
        return if (useCache) {
            PersonalDataManager.getUser(context)
        } else {
            // api
            var user: UserData? = null
            try {
                // api
                user = PersonalDataManager.getUser(context)
            } catch (e: Exception) {
                user = PersonalDataManager.getUser(context)
                logError(e)
            }
            PersonalDataManager.saveUser(context, user)
            user
        }
    }

    suspend fun getAura(context: Context, useCache: Boolean = false): Bitmap? {
        return if (useCache) {
            PersonalDataManager.getAura(context)
        } else {
            var aura: Bitmap? = null
            try {
                // api
                aura = PersonalDataManager.getAura(context)
            } catch (e: Exception) {
                aura = PersonalDataManager.getAura(context)
                logError(e)
            }
            PersonalDataManager.saveAura(context, aura)
            aura
        }
    }

    suspend fun getLvl(context: Context, useCache: Boolean = false): Float {
        return if (useCache) {
            PersonalDataManager.getLvl(context)
        } else {
            // api
            var lvl: Float? = 1f
            try {
                // api
                lvl = PersonalDataManager.getLvl(context)
            } catch (e: Exception) {
                lvl = PersonalDataManager.getLvl(context)
                logError(e)
            }
            lvl
        }
    }
}