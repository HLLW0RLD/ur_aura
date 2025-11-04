package com.example.ur_color.ui.screen.viewModel

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.local.dataManager.UserDataManager
import com.example.ur_color.data.user.aura.AuraGenerator
import com.example.ur_color.data.user.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel() : BaseViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveUser(context: Context, user: UserData, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.Default) {

            val bitmap = AuraGenerator.generateBaseAura(user)
            UserDataManager.save(context, user, bitmap)

            withContext(Dispatchers.Main) {
                onSuccess()
            }
        }
    }
}