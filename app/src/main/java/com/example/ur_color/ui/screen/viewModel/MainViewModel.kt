package com.example.ur_color.ui.screen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.model.AuraSection
import com.example.ur_color.data.model.Horoscope
import com.example.ur_color.data.model.SocialContent
import com.example.ur_color.data.repo.HoroscopeDate
import com.example.ur_color.data.repo.HoroscopeRepository
import com.example.ur_color.utils.auraSections
import com.example.ur_color.utils.feedCards
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class HoroscopeUiState {
    object Loading : HoroscopeUiState()
    data class Success(val horoscope: Horoscope) : HoroscopeUiState()
    data class Error(val message: String) : HoroscopeUiState()
}

class MainViewModel(
//    private val horoscopeRepository: HoroscopeRepository
) : BaseViewModel() {

    private val _horoscopeState = MutableStateFlow<HoroscopeUiState>(HoroscopeUiState.Loading)
    val horoscopeState: StateFlow<HoroscopeUiState> = _horoscopeState

    private val _feedCardsState = MutableStateFlow<List<SocialContent>>(feedCards)
    val feedCardsState = _feedCardsState.asStateFlow()


    fun loadDailyHoroscope(sign: String, day: HoroscopeDate = HoroscopeDate.TODAY) {
        _horoscopeState.value = HoroscopeUiState.Loading

//        viewModelScope.launch {
//            horoscopeRepository
//                .getDailyHoroscope(sign, day)
//                .fold(
//                    onSuccess = { horoscope ->
//                        _horoscopeState.value = HoroscopeUiState.Success(horoscope)
//                    },
//                    onFailure = { error ->
//                        _horoscopeState.value = HoroscopeUiState.Error(error.localizedMessage ?: "Unknown error")
//                    }
//                )
//        }
    }
}