package com.example.ur_color.data.repo

import android.graphics.Bitmap
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.data.remote.RegisterRequest
import com.example.ur_color.data.remote.UserApi
import com.example.ur_color.utils.logError
import com.example.ur_color.utils.logSuccess

class UserRepository(
    val api: UserApi
) {

    suspend fun login() {

    }

    suspend fun register(): Result<Unit> {
        val request = RegisterRequest(
            email = "tejklnjmkt@ele.com",
            password = "44ddAAAAd",
            firstName = "Testhhhddd123",
            lastName = "Testdhhhhdd123",
            birthDate = "1990-01-01",
            birthPlace = "Москва",
            gender = "male",
            zodiacSign = "Aries"
        )

        return try {
            val response = api.register(request)

            if (response.isSuccessful) {
                logSuccess("Регистрация успешна: ${response.body()}")
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string()
                logError("Ошибка сервера: ${response.code()} - $errorBody")

                when (response.code()) {
                    400 -> Result.failure(Exception("Пароль должен содержать буквы разного регистра и цифры"))
                    409 -> Result.failure(Exception("Пользователь уже существует"))
                    else -> Result.failure(Exception("Ошибка сервера: ${response.code()}"))
                }
            }
        } catch (e: Exception) {
            logError("Сетевая ошибка: ${e.message}")
            Result.failure(Exception("Нет подключения к интернету"))
        }
    }

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