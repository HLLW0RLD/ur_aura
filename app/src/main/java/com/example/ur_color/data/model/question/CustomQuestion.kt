package com.example.ur_color.data.model.question

import org.w3c.dom.Text
import java.util.UUID

data class CustomQuestion(
    val id: String? = UUID.randomUUID().toString(),
    val value: List<QuestionValue>,
)

data class QuestionValue(
    val text: String,
    val agree: Int = 0,
    val disAgree: Int = 0,
)