package com.example.ur_color.ui.screen.viewModel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.model.Question
import com.example.ur_color.data.dataProcessor.dailyTest.DailyTestOperator
import kotlinx.coroutines.launch

class DailyTestViewModel() : BaseViewModel() {

    fun consumeAnswer(context: Context, question: Question, answer: Boolean) {
        viewModelScope.launch {
            val data = user.value ?: return@launch
            DailyTestOperator.consumeAnswer(question = question, answer = answer)
            DailyTestOperator.applyDailyResult(context, data)
        }
    }
}