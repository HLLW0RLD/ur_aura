package com.example.ur_color.ui.screen.viewModel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
class ProfileViewModel() : BaseViewModel() {

    private val _isDailyTestAvailable = MutableStateFlow(true)
    val isDailyTestAvailable = _isDailyTestAvailable.asStateFlow()

    fun checkDailyTestAvailability(context: Context) {
        viewModelScope.launch {

            val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
            val lastDate = PersonalDataManager.dailyTestAvailable(context)

            if (lastDate != today) {
                PersonalDataManager.dailyTestAvailable(context, today)
                _isDailyTestAvailable.value = true
            } else {
                _isDailyTestAvailable.value = false
            }
        }
    }

    fun deleteUser(context: Context) {
        viewModelScope.launch {
            PersonalDataManager.delete(context)
        }
    }
}