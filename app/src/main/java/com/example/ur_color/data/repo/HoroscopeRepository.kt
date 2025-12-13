package com.example.ur_color.data.repo

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.ur_color.data.model.Horoscope
import com.example.ur_color.data.model.respoonse.toHoroscope
import com.example.ur_color.data.remote.HoroscopeApi
import java.util.*

class HoroscopeRepository(
    private val api: HoroscopeApi
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getDailyHoroscope(sign: String, day: HoroscopeDate): Result<Horoscope> {
        return try {
            val resp = api.getDailyHoroscope(sign, day.value)
            Result.success(resp.toHoroscope())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getDailyHoroscope(sign: String, date: Date): Result<Horoscope> {
        return try {
            val formatted = HoroscopeDate.fromDate(date)
            val resp = api.getDailyHoroscope(sign, formatted)
            Result.success(resp.toHoroscope())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

enum class HoroscopeDate(val value: String) {
    TODAY("TODAY"),
    TOMORROW("TOMORROW"),
    YESTERDAY("YESTERDAY");

    companion object {
        fun fromDate(date: Date): String {
            val format = java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return format.format(date)
        }
    }
}