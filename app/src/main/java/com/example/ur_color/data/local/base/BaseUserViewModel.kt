package com.example.ur_color.data.local.base

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.model.user.UserData
import kotlinx.coroutines.flow.*

abstract class BaseViewModel : ViewModel() {

//    val cachedUser: StateFlow<UserData?> = PersonalDataManager.user
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.Eagerly,
//            initialValue = null
//        )
//
//    val cachedAura: StateFlow<Bitmap?> = PersonalDataManager.aura
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.Eagerly,
//            initialValue = null
//        )
//
//    val cachedLevel: StateFlow<Float?> = PersonalDataManager.level
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.Eagerly,
//            initialValue = null
//        )
}