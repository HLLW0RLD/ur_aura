package com.example.ur_color.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ur_color.R
import com.example.ur_color.ui.AuraDatePickerField
import com.example.ur_color.ui.AuraDateTimePickerField
import com.example.ur_color.ui.AuraOutlinedTextField
import com.example.ur_color.ui.AuraTextButton
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.ui.screen.viewModel.RegistrationViewModel
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.AppScaffold
import com.example.ur_color.utils.LocalNavController
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object Registration : Screen

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun Registration(reg : Registration) {
    AppScaffold(
        showBottomBar = false,
        topBar = {
//            CustomAppBar(
//                title = stringResource(R.string.app_name_stylized),
//            )
        },
    ) {
        RegistrationScreen(
            modifier = Modifier
                .imePadding()
                .padding(it)
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegistrationScreen(
    registrationViewModel: RegistrationViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {

    val navController = LocalNavController.current
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.background)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
//                .imePadding()
                .verticalScroll(scrollState)
                .align(Alignment.TopCenter),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AuraOutlinedTextField(
                value = registrationViewModel.nickName,
                onValueChange = { registrationViewModel.nickName = it },
                label = stringResource(R.string.field_nickname),
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
            )
            AuraOutlinedTextField(
                value = registrationViewModel.lastName,
                onValueChange = { registrationViewModel.lastName = it },
                label = stringResource(R.string.field_last_name),
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
            )
            AuraOutlinedTextField(
                value = registrationViewModel.firstName,
                onValueChange = { registrationViewModel.firstName = it },
                label = stringResource(R.string.field_first_name),
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
            )
            AuraOutlinedTextField(
                value = registrationViewModel.middleName,
                onValueChange = { registrationViewModel.middleName = it },
                label = stringResource(R.string.field_middle_name_optional),
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
            )

            AuraDatePickerField(
                label = stringResource(R.string.field_birth_date),
                date = registrationViewModel.birthDate,
                color = AppColors.textPrimary,
                onDateChanged = { registrationViewModel.birthDate = it },
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
            )
            AuraDateTimePickerField(
                label = stringResource(R.string.field_birth_time),
                time = registrationViewModel.birthTime,
                color = AppColors.textPrimary,
                onTimeChanged = { registrationViewModel.birthTime = it },
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
            )
            AuraOutlinedTextField(
                value = registrationViewModel.birthPlace,
                onValueChange = { registrationViewModel.birthPlace = it },
                label = stringResource(R.string.field_birth_place),
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth()
            )

            val male = stringResource(R.string.gender_male)
            val female = stringResource(R.string.gender_female)
            Row(
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(
                    onClick = { registrationViewModel.gender = male },
                    colors = ButtonDefaults.buttonColors(
                        if (registrationViewModel.gender == male) AppColors.accentPrimary
                        else AppColors.backgroundLight)
                ) {
                    Text(male)
                }
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = { registrationViewModel.gender = female },
                    colors = ButtonDefaults.buttonColors(
                        if (registrationViewModel.gender == female) AppColors.accentPrimary
                        else AppColors.backgroundLight)
                ) {
                    Text(female)
                }
            }

            Spacer(Modifier.size(16.dp))
            AuraTextButton(
                text = stringResource(R.string.action_login),
                enabled = registrationViewModel.isUserValid,
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
            ) {
                registrationViewModel.register {
                    navController.nav(Login)
                }
            }
        }
    }
}
