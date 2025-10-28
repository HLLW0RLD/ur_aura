package com.example.ur_color.ui.screen.viewModel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.local.PrefCache
import com.example.ur_color.data.user.AuraGenerator
import com.example.ur_color.data.user.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel() : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSaveUser(context: Context, user: UserData, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.Default) {

            val bitmap = AuraGenerator.generateAura(user)
            PrefCache.saveUser(context, user, bitmap)

            withContext(Dispatchers.Main) {
                onSuccess()
            }
        }
    }
}