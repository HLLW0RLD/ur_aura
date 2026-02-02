package com.example.ur_color.ui.screen.viewModel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.ur_color.R
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.model.AuraSection
import com.example.ur_color.ui.theme.AuraColors
import com.example.ur_color.utils.auraSections
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LabViewModel : BaseViewModel()  {

    private val _auraSectionsState = MutableStateFlow<List<AuraSection>>(auraSections)
    val auraSectionsState = _auraSectionsState.asStateFlow()

    private val _isDailyTestAvailable = MutableStateFlow(true)
    val isDailyTestAvailable = _isDailyTestAvailable.asStateFlow()


    @RequiresApi(Build.VERSION_CODES.O)
    fun checkDailyTestAvailability() {
        viewModelScope.launch {

            val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
            val lastDate = PersonalDataManager.loadDailyTestDate()

            if (lastDate != today) {
                _isDailyTestAvailable.value = true
            } else {
                _isDailyTestAvailable.value = false
            }
        }
    }
}
