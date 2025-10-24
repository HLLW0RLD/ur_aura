package com.example.ur_color.ui.screen.viewModel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.local.PrefCache
import com.example.ur_color.data.model.Horoscope
import com.example.ur_color.data.repo.HoroscopeDate
import com.example.ur_color.data.repo.HoroscopeRepository
import com.example.ur_color.data.user.AuraGenerator
import com.example.ur_color.data.user.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


sealed class HoroscopeUiState {
    object Loading : HoroscopeUiState()
    data class Success(val horoscope: Horoscope) : HoroscopeUiState()
    data class Error(val message: String) : HoroscopeUiState()
}

class MainViewModel(
    private val horoscopeRepository: HoroscopeRepository
) : ViewModel() {

    private val _horoscopeState = MutableStateFlow<HoroscopeUiState>(HoroscopeUiState.Loading)
    val horoscopeState: StateFlow<HoroscopeUiState> = _horoscopeState

    fun loadDailyHoroscope(sign: String, day: HoroscopeDate = HoroscopeDate.TODAY) {
        _horoscopeState.value = HoroscopeUiState.Loading

        viewModelScope.launch {
            horoscopeRepository
                .getDailyHoroscope(sign, day)
                .fold(
                    onSuccess = { horoscope ->
                        _horoscopeState.value = HoroscopeUiState.Success(horoscope)
                    },
                    onFailure = { error ->
                        _horoscopeState.value = HoroscopeUiState.Error(error.localizedMessage ?: "Unknown error")
                    }
                )
        }
    }
}