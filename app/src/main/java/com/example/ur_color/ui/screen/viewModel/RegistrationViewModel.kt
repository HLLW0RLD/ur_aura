package com.example.ur_color.ui.screen.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.ur_color.utils.isValidEmail
import com.example.ur_color.utils.toIsoDate
import com.example.ur_color.utils.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationViewModel(
    private val authRepository: AuthRepository,
): BaseViewModel() {

    var nickName by mutableStateOf("")
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var middleName by mutableStateOf("")
    var birthDate by mutableStateOf("")
    var birthTime by mutableStateOf("")
    var birthPlace by mutableStateOf("")
    var gender by mutableStateOf("Мужской")

    var email by mutableStateOf("@mail.com")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    var about by mutableStateOf("")

    var showErrors by mutableStateOf(false)
        private set

    val isNickNameValid get() = nickName.isNotBlank()
    val isFirstNameValid get() = firstName.isNotBlank()
    val isLastNameValid get() = lastName.isNotBlank()
    val isBirthDateValid get() = birthDate.isNotBlank()
    val isBirthTimeValid get() = birthTime.isNotBlank()
    val isBirthPlaceValid get() = birthPlace.isNotBlank()
    val isGenderValid get() = gender.isNotBlank()

    val isUserValid: Boolean
        get() = isNickNameValid &&
                isFirstNameValid &&
                isLastNameValid &&
                isBirthDateValid &&
                isBirthTimeValid &&
                isBirthPlaceValid &&
                isGenderValid

    val isEmailValid: Boolean
        get() = isValidEmail(email) &&
                email.isNotBlank()

    val isPasswordValid: Boolean
        get() = password.isNotBlank() &&
                confirmPassword.isNotBlank() &&
                password == confirmPassword

    val isLoginValid: Boolean
        get() = isEmailValid && isPasswordValid


    fun validate() {
        showErrors = true
    }

    fun clearErrors() {
        showErrors = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun register(onSuccess: () -> Unit) {

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
