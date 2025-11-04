package com.example.ur_color.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.ur_color.data.user.UserData
import com.example.ur_color.data.user.ZodiacSign.Companion.calculateZodiac
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.ui.screen.viewModel.LoginViewModel
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.utils.LocalNavController
import com.example.ur_color.utils.formatDateInput
import com.example.ur_color.utils.formatTimeInput
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object Login : Screen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = koinViewModel()
) {

    val context = LocalContext.current
    val navController = LocalNavController.current
    val scope = rememberCoroutineScope()

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var middleName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf(TextFieldValue("")) }
    var birthTime by remember { mutableStateOf(TextFieldValue("")) }
    var birthPlace by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Мужской") }

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .imePadding()
            .fillMaxSize()
            .background(AppColors.background),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CustomAppBar(
            title = "-__a__u__r__a__-",
        )

        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                colors = TextFieldDefaults.colors(
                    focusedTextColor = AppColors.textPrimary,
                    unfocusedTextColor = AppColors.textPrimary,
                    focusedContainerColor = AppColors.background,
                    unfocusedContainerColor = AppColors.background,
                    focusedLabelColor = AppColors.accentPrimary,
                    unfocusedLabelColor = AppColors.accentPrimary,
                ),
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Фамилия") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                colors = TextFieldDefaults.colors(
                    focusedTextColor = AppColors.textPrimary,
                    unfocusedTextColor = AppColors.textPrimary,
                    focusedContainerColor = AppColors.background,
                    unfocusedContainerColor = AppColors.background,
                    focusedLabelColor = AppColors.accentPrimary,
                    unfocusedLabelColor = AppColors.accentPrimary,
                ),
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("Имя") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                colors = TextFieldDefaults.colors(
                    focusedTextColor = AppColors.textPrimary,
                    unfocusedTextColor = AppColors.textPrimary,
                    focusedContainerColor = AppColors.background,
                    unfocusedContainerColor = AppColors.background,
                    focusedLabelColor = AppColors.accentPrimary,
                    unfocusedLabelColor = AppColors.accentPrimary,
                ),
                value = middleName,
                onValueChange = { middleName = it },
                label = { Text("Отчество (опционально)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                colors = TextFieldDefaults.colors(
                    focusedTextColor = AppColors.textPrimary,
                    unfocusedTextColor = AppColors.textPrimary,
                    focusedContainerColor = AppColors.background,
                    unfocusedContainerColor = AppColors.background,
                    focusedLabelColor = AppColors.accentPrimary,
                    unfocusedLabelColor = AppColors.accentPrimary,
                ),
                value = birthDate,
                onValueChange = { input -> birthDate = formatDateInput(birthDate, input) },
                label = { Text("Дата рождения (ДД/ММ/ГГГГ)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                colors = TextFieldDefaults.colors(
                    focusedTextColor = AppColors.textPrimary,
                    unfocusedTextColor = AppColors.textPrimary,
                    focusedContainerColor = AppColors.background,
                    unfocusedContainerColor = AppColors.background,
                    focusedLabelColor = AppColors.accentPrimary,
                    unfocusedLabelColor = AppColors.accentPrimary,
                ),
                value = birthTime,
                onValueChange = { input -> birthTime = formatTimeInput(birthTime, input)},
                label = { Text("Время рождения (ЧЧ:ММ)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                colors = TextFieldDefaults.colors(
                    focusedTextColor = AppColors.textPrimary,
                    unfocusedTextColor = AppColors.textPrimary,
                    focusedContainerColor = AppColors.background,
                    unfocusedContainerColor = AppColors.background,
                    focusedLabelColor = AppColors.accentPrimary,
                    unfocusedLabelColor = AppColors.accentPrimary,
                ),
                value = birthPlace,
                onValueChange = { birthPlace = it },
                label = { Text("Место рождения") },
                modifier = Modifier.fillMaxWidth()
            )

            // Пол
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Мужской", "Женский").forEach { option ->
                    Button(
                        onClick = { gender = option },
                        colors = ButtonDefaults.buttonColors(if (gender == option) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary)
                    ) {
                        Text(option)
                    }
                }
            }

            Button(
                onClick = {
                    if (firstName.isNotBlank() && lastName.isNotBlank() && birthDate.text.length == 10) {
                        val fullName = "$lastName $firstName ${middleName.ifBlank { "" }}".trim()

                        // 1) парсим день/месяц для знака зодиака
                        val parts = birthDate.text.split("/")
                        val day = parts.getOrNull(0)?.toIntOrNull() ?: 1
                        val month = parts.getOrNull(1)?.toIntOrNull() ?: 1
                        val zodiac = calculateZodiac(day, month)

                        // 2) формируем модель
                        val user = UserData(
                            firstName = firstName,
                            lastName = lastName,
                            middleName = middleName.ifBlank { null },
                            birthDate = birthDate.text,
                            birthTime = birthTime.text,
                            birthPlace = birthPlace,
                            gender = gender,
                            zodiacSign = zodiac.nameRu
                        )

                        loginViewModel.saveUser(context, user) {
                            navController.navigate(Main.route())
                        }
                    }
                }
            ) {
                Text("войти")
            }
        }
    }
}