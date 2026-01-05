package com.example.ur_color.data.model

import com.example.ur_color.ui.theme.AuraColors

data class AuraSection(
    val sectionTitle: String,
    val rowConfig: AuraRowConfig,
    val items: List<AuraItem>
)

data class AuraItem(
    val id: String,
    val title: String,
    val description: String,
    val type: AuraItemType
)

data class AuraRowConfig(
    val color: AuraColors,  // или строка типа "#4B0082"
    val topIconRes: Int,    // здесь ссылка для AsyncImage
    val bottomIconRes: Int  // здесь ссылка для AsyncImage
)

enum class AuraItemType {
    PSYCHOLOGY_TEST,
    COMPATIBILITY,
    HOROSCOPE,
    DIVINATION,
    COURSE
}