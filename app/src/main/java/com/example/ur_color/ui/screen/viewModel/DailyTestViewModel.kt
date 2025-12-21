package com.example.ur_color.ui.screen.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.model.Question
import com.example.ur_color.data.dataProcessor.testOperator.DailyTestOperator
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.data.repo.UserRepository
import com.example.ur_color.utils.logDebug
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DailyTestViewModel(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _user = MutableStateFlow<UserData?>(null)
    private val _level = MutableStateFlow<Float>(1f)
    private val _aura = MutableStateFlow<Bitmap?>(null)

    @RequiresApi(Build.VERSION_CODES.O)
    private val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)

    @RequiresApi(Build.VERSION_CODES.O)
    fun init(context: Context) {
        viewModelScope.launch {
            PersonalDataManager.saveDailyTestDate(context, today)

            _user.value = userRepository.getUser(context)
            _level.value = userRepository.getLvl(context)
            _aura.value = userRepository.getAura(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun consumeAnswer(context: Context, question: Question, answer: Boolean) {
        viewModelScope.launch {
            DailyTestOperator.consumeAnswer(question = question, answer = answer)
        }
    }

    fun updateAfterTest(context: Context) {
        viewModelScope.launch {
            val data = _user.value ?: return@launch
            DailyTestOperator.applyDailyResult(context, data, _aura.value)
            PersonalDataManager.level(context, 0.2f)
        }
    }
}