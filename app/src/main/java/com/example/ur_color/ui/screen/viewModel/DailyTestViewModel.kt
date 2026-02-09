package com.example.ur_color.ui.screen.viewModel

import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.model.question.Question
import com.example.ur_color.data.dataProcessor.testOperator.DailyTestOperator
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.data.repo.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DailyTestViewModel(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _user = MutableStateFlow<UserData?>(null)
    private val _aura = MutableStateFlow<Bitmap?>(null)

    @RequiresApi(Build.VERSION_CODES.O)
    private val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)

    fun init() {
        viewModelScope.launch {
            _user.value = userRepository.getUser()
            _aura.value = userRepository.getAura()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun consumeAnswer(question: Question, answer: Boolean) {
        viewModelScope.launch {
            DailyTestOperator.consumeAnswer(question = question, answer = answer)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateAfterTest() {
        viewModelScope.launch {
            val data = _user.value ?: return@launch
            DailyTestOperator.applyDailyResult(data, _aura.value)
            PersonalDataManager.updateLevel(0.2f)
            PersonalDataManager.saveDailyTestDate(today)
            // дать ачивку за первый тест
        }
    }
}