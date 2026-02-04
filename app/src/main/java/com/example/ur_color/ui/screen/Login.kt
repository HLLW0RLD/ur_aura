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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ur_color.R
import com.example.ur_color.data.model.user.CharacteristicData
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.data.model.user.ZodiacSign.Companion.calculateZodiac
import com.example.ur_color.ui.AuraDatePickerField
import com.example.ur_color.ui.AuraDateTimePickerField
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.ui.screen.viewModel.LoginViewModel
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.AppScaffold
import com.example.ur_color.utils.LocalNavController
import com.example.ur_color.utils.logDebug
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object Login : Screen

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun Login(login : Login) {
    AppScaffold(
        showBottomBar = false,
        topBar = {
            CustomAppBar(
                title = stringResource(R.string.app_name_stylized),
            )
        },
    ) {
        LoginScreen(
            modifier = Modifier
                .imePadding()
                .padding(it)
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val navController = LocalNavController.current

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var middleName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var birthTime by remember { mutableStateOf("") }
    var birthPlace by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("Мужской") }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.background)
            .verticalScroll(scrollState)
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

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
                label = { Text(stringResource(R.string.field_last_name)) },
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
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
                label = { Text(stringResource(R.string.field_first_name)) },
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
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
                label = { Text(stringResource(R.string.field_middle_name_optional)) },
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
            )
//            OutlinedTextField(
//                colors = TextFieldDefaults.colors(
//                    focusedTextColor = AppColors.textPrimary,
//                    unfocusedTextColor = AppColors.textPrimary,
//                    focusedContainerColor = AppColors.background,
//                    unfocusedContainerColor = AppColors.background,
//                    focusedLabelColor = AppColors.accentPrimary,
//                    unfocusedLabelColor = AppColors.accentPrimary,
//                ),
//                value = birthDate,
//                onValueChange = { input ->
////                    birthDate = formatDateInput(birthDate, input)
////
//////                    logDebug(input)
////                    logDebug(birthDate)
//
//                                },
//                label = { Text(stringResource(R.string.field_birth_date)) },
//                modifier = Modifier
//                    .imePadding()
//                    .fillMaxWidth()
//            )
            AuraDatePickerField(
                label = stringResource(R.string.field_birth_date),
                date = birthDate,
                color = AppColors.textPrimary,
                onDateChanged = { input ->
                    logDebug(input)
                    birthDate = input
                },
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
            )
            AuraDateTimePickerField(
                label = stringResource(R.string.field_birth_time),
                time = birthTime,
                color = AppColors.textPrimary,
                onTimeChanged = { input ->
                    logDebug(input)
                    birthTime = input
                },
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
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
                label = { Text(stringResource(R.string.field_birth_place)) },
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
            )

            // Пол
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    stringResource(R.string.gender_male),
                    stringResource(R.string.gender_female)
                ).forEach { option ->
                    Button(
                        onClick = { gender = option },
                        colors = ButtonDefaults.buttonColors(
                            if (gender == option) AppColors.accentPrimary
                            else AppColors.backgroundLight)
                    ) {
                        Text(option)
                    }
                }
            }

            Button(
                onClick = {
                    if (firstName.isNotBlank() && lastName.isNotBlank() && birthDate.length == 10) {
                        val fullName = "$lastName $firstName ${middleName.ifBlank { "" }}".trim()

                        // 1) парсим день/месяц для знака зодиака
                        val parts = birthDate.split(".")
                        val day = parts.getOrNull(0)?.toIntOrNull() ?: 1
                        val month = parts.getOrNull(1)?.toIntOrNull() ?: 1
                        val zodiac = calculateZodiac(day, month)

                        // 2) формируем модель
                        val user = UserData(
                            nickName = firstName,
                            firstName = firstName,
                            lastName = lastName,
                            middleName = middleName.ifBlank { null },
                            birthDate = birthDate,
                            birthTime = birthTime,
                            birthPlace = birthPlace,
                            gender = gender,
                            zodiacSign = zodiac.nameRu,
                            characteristics = CharacteristicData()
                        )

                        loginViewModel.saveUser(user) {
                            navController.nav(TabsHost)
                        }
                    }
                }
            ) {
                Text(stringResource(R.string.action_login))
            }
        }
    }
}