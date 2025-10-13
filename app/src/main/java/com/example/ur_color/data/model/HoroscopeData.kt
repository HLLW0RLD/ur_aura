package com.example.ur_color.data.model

import com.google.gson.annotations.SerializedName

data class HoroscopeData(
    @SerializedName("date") val date: String,
    @SerializedName("horoscope_data") val horoscopeData: String
)