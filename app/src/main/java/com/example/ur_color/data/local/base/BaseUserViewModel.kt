package com.example.ur_color.data.local.base

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.local.dataManager.UserDataManager
import com.example.ur_color.data.user.UserData
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    val user: StateFlow<UserData?> = UserDataManager.user
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )

    val aura: StateFlow<Bitmap?> = UserDataManager.aura
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )
}