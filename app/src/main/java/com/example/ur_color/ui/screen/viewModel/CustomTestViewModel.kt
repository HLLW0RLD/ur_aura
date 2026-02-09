package com.example.ur_color.ui.screen.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.ur_color.App
import com.example.ur_color.data.dataProcessor.testOperator.DailyTestOperator
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.data.repo.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CustomTestViewModel(
    private val userRepository: UserRepository
) : BaseViewModel()  {

    private val _user = MutableStateFlow<UserData?>(null)

    // getting from db or api
    private val _questions = MutableStateFlow<Map<String, Pair<Int, Int>>>(mapOf())
    val questions = _questions.asStateFlow()

    init {
        viewModelScope.launch {
            _user.value = userRepository.getUser()
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun updateAfterTest() {
        viewModelScope.launch {
            val data = _user.value ?: return@launch
            PersonalDataManager.updateLevel(0.2f)
        }
    }
}