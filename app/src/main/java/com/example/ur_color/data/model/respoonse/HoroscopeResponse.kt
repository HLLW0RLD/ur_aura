package com.example.ur_color.data.model.respoonse

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.ur_color.data.model.Horoscope
import com.example.ur_color.utils.formatDateToRussian
import com.google.gson.annotations.SerializedName

data class HoroscopeResponse(
    @SerializedName("data") val data: HoroscopeData,
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success: Boolean
)

data class HoroscopeData(
    @SerializedName("date") val date: String,
    @SerializedName("horoscope_data") val horoscopeData: String
)

@RequiresApi(Build.VERSION_CODES.O)
fun HoroscopeResponse.toHoroscope(): Horoscope {
    val dayRus = formatDateToRussian(this.data.date)
    return Horoscope(
        day = dayRus,
        horoscope = this.data.horoscopeData
    )
}