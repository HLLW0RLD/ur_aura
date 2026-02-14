package com.example.ur_color.ui.screen.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.ur_color.data.dataProcessor.aura.AuraGenerator
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.data.model.request.UserRegistration
import com.example.ur_color.data.model.user.ZodiacSign.Companion.calculateZodiac
import com.example.ur_color.data.model.user.toUserData
import com.example.ur_color.data.repo.AuthRepository
import com.example.ur_color.utils.AlertManager
import com.example.ur_color.utils.isValidEmail
import com.example.ur_color.utils.toIsoDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationViewModel(
    private val authRepository: AuthRepository,
    private val alertManager: AlertManager
): BaseViewModel()
{

    var nickName by mutableStateOf("")
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var middleName by mutableStateOf("")
    var birthDate by mutableStateOf("")
    var birthTime by mutableStateOf("")
    var birthPlace by mutableStateOf("")
    var gender by mutableStateOf("Мужской")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var about by mutableStateOf("")


    var alertShown by mutableStateOf(false)
        private set
    val isNickNameValid get() = nickName.isNotBlank()
    val isFirstNameValid get() = firstName.isNotBlank()
    val isLastNameValid get() = lastName.isNotBlank()
    val isBirthDateValid get() = birthDate.isNotBlank()
    val isBirthTimeValid get() = birthTime.isNotBlank()
    val isBirthPlaceValid get() = birthPlace.isNotBlank()
    val isGenderValid get() = gender.isNotBlank()
    val isEmailValid get() = isValidEmail(email) && email.isNotBlank()
    val isPasswordValid get() = password.isNotBlank()
    val isConfirmPasswordValid get() = confirmPassword.isNotBlank() && password == confirmPassword

    fun validateUser(): ValidationError? = when {
        nickName.isBlank() -> ValidationError.NicknameRequired
        firstName.isBlank() -> ValidationError.FirstNameRequired
        lastName.isBlank() -> ValidationError.LastNameRequired
        birthDate.isBlank() -> ValidationError.BirthDateRequired
        birthTime.isBlank() -> ValidationError.BirthTimeRequired
        birthPlace.isBlank() -> ValidationError.BirthPlaceRequired
        gender.isBlank() -> ValidationError.GenderRequired
        else -> null
    }

    fun validateLogin(): ValidationError? = when {
        email.isBlank() || !isValidEmail(email) -> ValidationError.EmailInvalid
        password.isBlank() -> ValidationError.PasswordRequired
        confirmPassword.isBlank() || password != confirmPassword -> ValidationError.PasswordMismatch
        else -> null
    }

    fun showError(text: String, onActionClick: (() -> Unit)? = null) {
        alertShown = true
        alertManager.showError(text, onActionClick)
    }

    fun clearErrors() {
        alertShown = true
    }

    fun register(onSuccess: () -> Unit) {
        val error = validateUser() ?: validateLogin()
        if (error != null) {
            alertShown = true
            return
        }

        viewModelScope.launch(Dispatchers.Default) {
            val parts = birthDate.split(".")
            val day = parts.getOrNull(0)?.toIntOrNull() ?: 1
            val month = parts.getOrNull(1)?.toIntOrNull() ?: 1
            val zodiac = calculateZodiac(day, month)


            val result = authRepository.register(
                email = email,
                password = password,
                nickName = nickName,
                firstName = firstName,
                lastName = lastName,
                middleName = middleName,
                about = about,
                birthDate = toIsoDate(birthDate) ?: "",
                birthTime = birthTime,
                birthPlace = birthPlace,
                gender = gender,
                zodiacSign = zodiac.nameRu
            )

//            when {
//                result.isSuccess -> {

                    val user = UserRegistration(
                        nickName = nickName,
                        firstName = firstName,
                        email = email,
                        password = password,
                        lastName = lastName,
                        middleName = middleName.ifBlank { null },
                        birthDate = birthDate,
                        birthTime = birthTime,
                        birthPlace = birthPlace,
                        gender = gender,
                        about = about,
                        zodiacSign = zodiac.nameRu,
                    )

                    val bitmap = AuraGenerator.generateBaseAura(user.toUserData())

                    PersonalDataManager.saveUserToCache(user.toUserData())
                    PersonalDataManager.saveAuraToCache(bitmap)

                    withContext(Dispatchers.Main) {
                        onSuccess()
                    }
//                }

//                result.isFailure -> {
//                    withContext(Dispatchers.Main) {
//                        toast("${result.exceptionOrNull()?.message}")
//                    }
//                }
//            }
        }
    }
}

sealed class ValidationError {
    object NicknameRequired : ValidationError()
    object FirstNameRequired : ValidationError()
    object LastNameRequired : ValidationError()
    object BirthDateRequired : ValidationError()
    object BirthTimeRequired : ValidationError()
    object BirthPlaceRequired : ValidationError()
    object GenderRequired : ValidationError()
    object EmailInvalid : ValidationError()
    object PasswordRequired : ValidationError()
    object PasswordMismatch : ValidationError()
}
