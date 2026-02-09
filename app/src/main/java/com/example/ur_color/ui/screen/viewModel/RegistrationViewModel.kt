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
import com.example.ur_color.data.model.user.CharacteristicData
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.data.model.user.UserDataRequest
import com.example.ur_color.data.model.user.ZodiacSign.Companion.calculateZodiac
import com.example.ur_color.data.model.user.toUserData
import com.example.ur_color.utils.isValidEmail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationViewModel : BaseViewModel() {

    var nickName by mutableStateOf("")
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var middleName by mutableStateOf("")
    var birthDate by mutableStateOf("")
    var birthTime by mutableStateOf("")
    var birthPlace by mutableStateOf("")
    var gender by mutableStateOf("Мужской")

    var email by mutableStateOf("qwert@yu.com")
    var password by mutableStateOf("asdfg")

    var about by mutableStateOf("")

    val isUserValid: Boolean
        get() = nickName.isNotBlank() &&
                firstName.isNotBlank() &&
                lastName.isNotBlank() &&
                birthTime.isNotBlank() &&
                birthPlace.isNotBlank() &&
                gender.isNotBlank() &&
                birthDate.isNotBlank()

    val isLoginValid: Boolean
        get() = isValidEmail(email) &&
                password.isNotBlank()


    @RequiresApi(Build.VERSION_CODES.O)
    fun register(onSuccess: () -> Unit) {

        viewModelScope.launch(Dispatchers.Default) {
            val parts = birthDate.split(".")
            val day = parts.getOrNull(0)?.toIntOrNull() ?: 1
            val month = parts.getOrNull(1)?.toIntOrNull() ?: 1
            val zodiac = calculateZodiac(day, month)

            val user = UserDataRequest(
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
                characteristics = CharacteristicData()
            )

            val bitmap = AuraGenerator.generateBaseAura(user.toUserData())

            PersonalDataManager.saveUser(user.toUserData())
            PersonalDataManager.saveAura(bitmap)

            withContext(Dispatchers.Main) {
                onSuccess()
            }
        }
    }
}
