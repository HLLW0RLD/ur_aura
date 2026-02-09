package com.example.ur_color.data.remote

import com.example.ur_color.data.model.user.CharacteristicData
import com.example.ur_color.data.model.user.UserAuth
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.UUID

interface UserApi {

    @POST("auth/register")
    suspend fun register(@Body user: RegisterRequest) : Response<RegisterData>

    @POST("auth/login")
    suspend fun login(@Body user: UserAuth)

    fun getUser()
}

data class RegisterData(
    val success: Boolean,
    val token: String,
    val user: UserReg,
)

data class UserReg(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
)

data class RegisterRequest(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val birthPlace: String,
    val gender: String,
    val zodiacSign: String
)

@Serializable
data class UserDataRequest(
    val id: String = UUID.randomUUID().toString(),
    val email: String,
    val password: String,
    val nickName: String,
    val firstName: String,
    val lastName: String,
    val middleName: String?,
    val about: String? = null,
    val birthDate: String,
    val birthTime: String?,
    val birthPlace: String,
    val gender: String,
    val zodiacSign: String,
    val avatarUri: String? = null,
    val birthTimestamp: Long = 0L,
    val characteristics: CharacteristicData,
    val userLevel: Int = 1,
)