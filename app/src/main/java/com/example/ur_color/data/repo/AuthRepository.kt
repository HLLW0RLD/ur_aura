package com.example.ur_color.data.repo

import com.example.ur_color.data.model.request.UserAuth
import com.example.ur_color.data.model.request.UserRegistration
import com.example.ur_color.data.model.response.AuthData
import com.example.ur_color.data.remote.UserApi
import com.example.ur_color.utils.logError

class AuthRepository(
    val api: UserApi
){

    suspend fun login(
        email: String,
        password: String,
    ): Result<AuthData?>  {

        val request = UserAuth(email, password)

        return try {
            val response = api.login(request)

            if (response.isSuccessful) {
                Result.success(response.body())
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

    suspend fun register(
        email: String,
        password: String,
        nickName: String,
        firstName: String,
        lastName: String,
        middleName: String?,
        about: String? = null,
        birthDate: String,
        birthTime: String?,
        birthPlace: String,
        gender: String,
        zodiacSign: String,
        userLevel: Int = 1
    ): Result<AuthData?> {

        val request = UserRegistration(
//            id = UUID.randomUUID().toString(),
            email = email,
            password = password,
            nickName = nickName,
            firstName = firstName,
            lastName = lastName,
            middleName = middleName,
            about = about,
            birthDate = birthDate,
            birthTime = birthTime,
            birthPlace = birthPlace,
            gender = gender,
            zodiacSign = zodiacSign,
            userLevel = userLevel
        )

//        val request = RegisterRequest(
//            email = email,
//            password = password,
//            firstName = firstName,
//            lastName = lastName,
//            birthDate = birthDate,
//            birthPlace = birthPlace,
//            gender = gender,
//            zodiacSign = zodiacSign
//        )

        return try {
            val response = api.register(request)

            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                val errorBody = response.errorBody()?.string()
                logError("Ошибка сервера: ${response.code()} - $errorBody")

                Result.failure(Exception(errorBody))
            }
        } catch (e: Exception) {
            logError("Сетевая ошибка: ${e.message}")
            Result.failure(Exception("Нет подключения к интернету"))
        }
    }
}