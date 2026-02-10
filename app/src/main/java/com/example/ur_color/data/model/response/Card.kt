package com.example.ur_color.data.model.response

data class Card(
    val name: String,
    val shortMeaning: String, // краткое значение
    val fullMeaning: String,  // полное описание карты
    val advice: String,
    val element: String,      // стихия: огонь, вода, земля, воздух
    val number: Int,          // номер карты
    val keywords: List<String>,
    val compatibleWith: List<String>, // другие карты, с которыми совместима
    val imageUrl: String? = null     // можно добавить URL или ресурс
)