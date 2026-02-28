package com.example.ur_color.ui.screen.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.dataProcessor.aura.AuraGenerator
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.repo.AuthRepository
import com.example.ur_color.data.repo.UserRepository
import com.example.ur_color.utils.AlertManager
import com.example.ur_color.utils.isValidEmail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val alertManager: AlertManager
) : BaseViewModel() {

    var email by mutableStateOf("qwert@yu.com")
    var password by mutableStateOf("asdfg")

    val _tokenState = MutableStateFlow<RegisterState>(RegisterState.Loading)
    val tokenState = _tokenState.asStateFlow()

    val isLoginValid: Boolean
        get() = isValidEmail(email) &&
            password.isNotBlank()


    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.Default) {

            val result = authRepository.login(
                email = email,
                password = password
            )

            result.onSuccess {
                _tokenState.value = RegisterState.Success( result.getOrNull()?.token ?: "")
                PersonalDataManager.saveTokenToCache(result.getOrNull()?.token ?: "")

                userRepository.getMe()

                withContext(Dispatchers.Main) {
                    onSuccess()
                }
            }

            result.onFailure {
                withContext(Dispatchers.Main) {
                    showError("${result.exceptionOrNull()?.message}")
                }
            }
        }
    }

    fun showError(text: String, onActionClick: (() -> Unit)? = null) {
        alertManager.showError(text, onActionClick)
    }
}