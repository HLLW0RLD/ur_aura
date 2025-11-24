package com.example.ur_color.ui.screen.viewModel

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.model.Question
import com.example.ur_color.data.user.aura.DailyTestOperator
import kotlinx.coroutines.launch

class DailyTestViewModel() : BaseViewModel() {

    fun consumeAnswer(context: Context, question: Question, answer: Boolean) {
        viewModelScope.launch {
            val data = user.value ?: return@launch
            DailyTestOperator.consumeAnswer(question = question, answer = answer)
            DailyTestOperator.applyDailyResult(context, data)
        }
    }

    private fun updateVector(
        oldVector: List<Int>,
        value: Int,
        maxSize: Int = 10
    ): List<Int> {

        val newList = oldVector + value.coerceIn(1, 10)
        val result = if (newList.size > maxSize) newList.takeLast(maxSize) else newList

        return result
    }
}