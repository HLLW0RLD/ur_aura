package com.example.ur_color.ui.screen.viewModel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.UserData
import com.example.ur_color.data.local.PrefCache
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel() : ViewModel() {

    private val _user = MutableStateFlow<UserData?>(null)
    val user: StateFlow<UserData?> = _user

    private val _aura = MutableStateFlow<Bitmap?>(null)
    val aura: StateFlow<Bitmap?> = _aura

    init {
        viewModelScope.launch {
            _user.value = PrefCache.user.value
            _aura.value = PrefCache.aura.value
        }
    }

    fun saveUser(context: Context, userData: UserData, auraBitmap: Bitmap?) {
        viewModelScope.launch {
            PrefCache.saveUser(context, userData, auraBitmap)
            _user.value = userData
            _aura.value = auraBitmap ?: PrefCache.aura.value
        }
    }

    fun deleteUser(context: Context) {
        viewModelScope.launch {
            PrefCache.deleteUser(context)
            _user.value = null
            _aura.value = null
        }
    }
}