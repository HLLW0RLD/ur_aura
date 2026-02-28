package com.example.ur_color.data.repo

import android.graphics.Bitmap
import com.example.ur_color.data.dataProcessor.aura.AuraGenerator
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.data.remote.ApiResponse
import com.example.ur_color.data.remote.UserApi
import com.example.ur_color.utils.logDebug
import com.example.ur_color.utils.logError

class UserRepository(
    val api: UserApi
) {

    suspend fun getMe(useCache: Boolean = false): UserData? {
        return if (useCache) {
            PersonalDataManager.getUserFromCache()
        } else {
            fetchWithFallback(
                apiCall = { api.getMe() },
                onSuccess = { userData ->
                    PersonalDataManager.saveUserToCache(userData)
                    val bitmap = AuraGenerator.generateBaseAura(userData)
                    PersonalDataManager.saveAuraToCache(bitmap)
                    userData
                },
                onFallback = { PersonalDataManager.getUserFromCache() }
            )
        }
    }

    suspend fun getUserById(userId: String): UserData? {
        return fetchWithFallback(
            apiCall = { api.getUserById(userId) },
            onSuccess = { userData ->
                PersonalDataManager.saveUserToCache(userData)
                userData
            },
            onFallback = { PersonalDataManager.getUserFromCache() }
        )
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

    private suspend fun <T> fetchWithFallback(
        apiCall: suspend () -> retrofit2.Response<ApiResponse<T>>,
        onSuccess: suspend (T) -> Unit,
        onFallback: suspend () -> T?
    ): T? {
        return try {
            val retrofitResponse = apiCall.invoke()

            if (!retrofitResponse.isSuccessful) {
                val errorBody = retrofitResponse.errorBody()?.string()
                logError("HTTP ${retrofitResponse.code()}: $errorBody")
                return onFallback()
            }

            val apiResponse = retrofitResponse.body()
            if (apiResponse == null) {
                logError("Empty response body")
                return onFallback()
            }

            if (apiResponse.success) {
                val data = apiResponse.data
                if (data != null) {
                    onSuccess(data)
                    data
                } else {
                    logError("API success=true but data is null")
                    onFallback()
                }
            } else {
                logError("API error: success=false")
                onFallback()
            }

        } catch (e: retrofit2.HttpException) {
            logError("HttpException: ${e.code()} - ${e.message}")
            onFallback()
        } catch (e: java.io.IOException) {
            logError("Network error: ${e.message}")
            onFallback()
        } catch (e: Exception) {
            logError("Unexpected error: ${e.message}")
            onFallback()
        }
    }
}