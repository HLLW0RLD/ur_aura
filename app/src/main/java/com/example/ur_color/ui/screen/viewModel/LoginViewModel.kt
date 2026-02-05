package com.example.ur_color.ui.screen.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.dataProcessor.aura.AuraGenerator
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.model.user.CharacteristicData
import com.example.ur_color.data.model.user.UserAuth
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.data.model.user.ZodiacSign.Companion.calculateZodiac
import com.example.ur_color.utils.isValidEmail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : BaseViewModel() {

    var email by mutableStateOf("qwert@yu.com")
    var password by mutableStateOf("asdfg")

    val isLoginValid: Boolean
        get() = isValidEmail(email) &&
            password.isNotBlank()

    fun login(onSuccess: () -> Unit) {

        viewModelScope.launch(Dispatchers.Default) {

            val user = UserAuth(
                email = email,
                password = password
            )

            withContext(Dispatchers.Main) {
                onSuccess()
            }
        }
    }
}