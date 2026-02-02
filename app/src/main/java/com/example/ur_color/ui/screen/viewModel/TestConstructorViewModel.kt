package com.example.ur_color.ui.screen.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.model.question.CustomQuestion
import com.example.ur_color.data.model.question.QuestionValue
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.data.repo.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.mapOf

class TestConstructorViewModel(

) : BaseViewModel()  {

    private val _questions = MutableStateFlow<Map<String, Pair<Int, Int>>>(mapOf())
    val questions = _questions.asStateFlow()

    init {

    }

    fun addQuestion(text: String, value: Pair<Int, Int>) {
        _questions.value.toMutableMap()[text] = value
    }

    fun save() {
        val value = mutableListOf<QuestionValue>()

        _questions.value.forEach { v ->
            value.add(QuestionValue(
                text = v.key,
                agree = v.value.first,
                disAgree = v.value.second
            ))
        }
        val custom = CustomQuestion(
            value = value
        )
    }
}