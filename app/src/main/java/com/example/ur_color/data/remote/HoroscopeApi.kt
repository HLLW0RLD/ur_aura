package com.example.ur_color.data.remote

import com.example.ur_color.data.model.respoonse.HoroscopeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HoroscopeApi {
    @GET("get-horoscope/daily")
    suspend fun getDailyHoroscope(
        @Query("sign") sign: String,
        @Query("day") day: String
    ): HoroscopeResponse
}