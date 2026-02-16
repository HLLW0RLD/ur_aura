package com.example.ur_color.ui.screen.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.dataProcessor.aura.AuraGenerator
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.repo.AuthRepository
import com.example.ur_color.utils.AlertManager
import com.example.ur_color.utils.isValidEmail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val alertManager: AlertManager
) : BaseViewModel() {

    var email by mutableStateOf("qwert@yu.com")
    var password by mutableStateOf("asdfg")

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