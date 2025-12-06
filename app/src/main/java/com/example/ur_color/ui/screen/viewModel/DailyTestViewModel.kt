package com.example.ur_color.ui.screen.viewModel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.model.Question
import com.example.ur_color.data.dataProcessor.dailyTest.DailyTestOperator
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DailyTestViewModel() : BaseViewModel() {

    fun consumeAnswer(context: Context, question: Question, answer: Boolean) {
        viewModelScope.launch {
            val data = user.value ?: return@launch
            val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
            DailyTestOperator.consumeAnswer(question = question, answer = answer)
            DailyTestOperator.applyDailyResult(context, data)
            PersonalDataManager.saveDailyTestDate(context, today)
        }
    }
}