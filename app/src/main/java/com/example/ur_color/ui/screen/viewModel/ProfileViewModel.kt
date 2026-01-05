package com.example.ur_color.ui.screen.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.data.repo.UserRepository
import com.example.ur_color.utils.profileCards
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
class ProfileViewModel(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _isDailyTestAvailable = MutableStateFlow(true)
    val isDailyTestAvailable = _isDailyTestAvailable.asStateFlow()

    private val _profileCardsState = MutableStateFlow(profileCards)
    val profileCardsState = _profileCardsState.asStateFlow()

    private val _user = MutableStateFlow<UserData?>(null)
    val user = _user

    private val _aura = MutableStateFlow<Bitmap?>(null)
    val aura= _aura

    private val _level = MutableStateFlow<Float>(1f)
    val level = _level

    fun init(context: Context) {
        viewModelScope.launch {
            _user.value = userRepository.getUser(context)
            _level.value = userRepository.getLvl(context)
            _aura.value = userRepository.getAura(context)
        }
    }

    fun checkDailyTestAvailability(context: Context) {
        viewModelScope.launch {

            val today = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
            val lastDate = PersonalDataManager.loadDailyTestDate(context)

            if (lastDate != today) {
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