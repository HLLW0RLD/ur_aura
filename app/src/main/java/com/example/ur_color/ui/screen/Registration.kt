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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ur_color.R
import com.example.ur_color.ui.AuraDatePickerField
import com.example.ur_color.ui.AuraDateTimePickerField
import com.example.ur_color.ui.AuraOutlinedTextField
import com.example.ur_color.ui.AuraPasswordField
import com.example.ur_color.ui.AuraTextButton
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.ui.screen.viewModel.RegistrationViewModel
import com.example.ur_color.ui.screen.viewModel.ValidationError
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.AppScaffold
import com.example.ur_color.utils.LocalNavController
import com.example.ur_color.utils.toast
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object Registration : Screen

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun Registration(reg : Registration) {
    val  state = rememberPagerState(initialPage = 0, pageCount = { 3 })

    AppScaffold(
        showBottomBar = false,
        topBar = {
            CustomAppBar(
                title = when (state.currentPage) {
                    0 -> stringResource(R.string.registration_title)
                    1 -> stringResource(R.string.login_title)
                    2 -> stringResource(R.string.about_button)
                    else -> ""
                },
                height = 72f,
                textSize = 36f,
                isCentered = false,
                backgroundColor = AppColors.background,
                showDivider = false,
            )
        },
    ) {
        RegistrationScreen(
            state = state,
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegistrationScreen(
    state: PagerState,
    registrationViewModel: RegistrationViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.background),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
//            modifier = Modifier.fillMaxSize(),
            state = state,
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> PersonalInfoPage(state)
                1 -> AuthPage(state)
                2 -> AboutPage()
            }
        }
    }
}

@Composable
private fun PersonalInfoPage(
    state: PagerState,
    registrationViewModel: RegistrationViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val alertShown = registrationViewModel.alertShown

    Column(
        modifier = modifier
            .imePadding()
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Top
    )
    {
        AuraOutlinedTextField(
            value = registrationViewModel.nickName,
            onValueChange = { registrationViewModel.nickName = it },
            label = stringResource(R.string.field_nickname),
            isError = alertShown && !registrationViewModel.isNickNameValid,
            modifier = Modifier
                .fillMaxWidth()
        )
        AuraOutlinedTextField(
            value = registrationViewModel.firstName,
            onValueChange = { registrationViewModel.firstName = it },
            label = stringResource(R.string.field_first_name),
            isError = alertShown && !registrationViewModel.isFirstNameValid,
            modifier = Modifier
                .fillMaxWidth()
        )
        AuraOutlinedTextField(
            value = registrationViewModel.lastName,
            onValueChange = { registrationViewModel.lastName = it },
            label = stringResource(R.string.field_last_name),
            isError = alertShown && !registrationViewModel.isLastNameValid,
            modifier = Modifier
                .fillMaxWidth()
        )
        AuraOutlinedTextField(
            value = registrationViewModel.middleName,
            onValueChange = { registrationViewModel.middleName = it },
            label = stringResource(R.string.field_middle_name_optional),
            modifier = Modifier
                .fillMaxWidth()
        )

        AuraDatePickerField(
            label = stringResource(R.string.field_birth_date),
            date = registrationViewModel.birthDate,
            color = AppColors.textPrimary,
            onDateChanged = { registrationViewModel.birthDate = it },
            isError = alertShown && !registrationViewModel.isBirthDateValid,
            modifier = Modifier
                .fillMaxWidth()
        )
        AuraDateTimePickerField(
            label = stringResource(R.string.field_birth_time),
            time = registrationViewModel.birthTime,
            color = AppColors.textPrimary,
            onTimeChanged = { registrationViewModel.birthTime = it },
            isError = alertShown && !registrationViewModel.isBirthTimeValid,
            modifier = Modifier
                .fillMaxWidth()
        )
        AuraOutlinedTextField(
            value = registrationViewModel.birthPlace,
            onValueChange = { registrationViewModel.birthPlace = it },
            label = stringResource(R.string.field_birth_place),
            isError = alertShown && !registrationViewModel.isBirthPlaceValid,
            modifier = Modifier
                .fillMaxWidth()
        )

        val male = stringResource(R.string.gender_male)
        val female = stringResource(R.string.gender_female)
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = { registrationViewModel.gender = male },
                colors = ButtonDefaults.buttonColors(
                    if (registrationViewModel.gender == male) AppColors.accentPrimary
                    else AppColors.backgroundLight
                )
            ) {
                Text(male)
            }
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = { registrationViewModel.gender = female },
                colors = ButtonDefaults.buttonColors(
                    if (registrationViewModel.gender == female) AppColors.accentPrimary
                    else AppColors.backgroundLight
                )
            ) {
                Text(female)
            }
        }

        Spacer(Modifier.size(16.dp))

        val validationError = registrationViewModel.validateUser()
        val errorNickname = stringResource(R.string.error_required_nickname)
        val errorFirstName = stringResource(R.string.error_required_first_name)
        val errorLastName = stringResource(R.string.error_required_last_name)
        val errorBirthDate = stringResource(R.string.error_required_birth_date)
        val errorBirthTime = stringResource(R.string.error_required_birth_time)
        val errorBirthPlace = stringResource(R.string.error_required_birth_place)
        val errorGender = stringResource(R.string.error_required_gender)

        AuraTextButton(
            text = stringResource(R.string.action_registration),
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            if (validationError == null) {
                scope.launch {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                    registrationViewModel.clearErrors()
                    state.animateScrollToPage(1)
                }
            } else {
                if (!registrationViewModel.isNickNameValid) {
                    registrationViewModel.showError(errorNickname)
                }
                if (!registrationViewModel.isFirstNameValid) {
                    registrationViewModel.showError(errorFirstName)
                }
                if (!registrationViewModel.isLastNameValid) {
                    registrationViewModel.showError(errorLastName)
                }
                if (!registrationViewModel.isBirthDateValid) {
                    registrationViewModel.showError(errorBirthDate)
                }
                if (!registrationViewModel.isBirthTimeValid) {
                    registrationViewModel.showError(errorBirthTime)
                }
                if (!registrationViewModel.isBirthPlaceValid) {
                    registrationViewModel.showError(errorBirthPlace)
                }
                if (!registrationViewModel.isGenderValid) {
                    registrationViewModel.showError(errorGender)
                }
            }
        }
    }
}

@Composable
private fun AuthPage(
    pagerState: PagerState,
    registrationViewModel: RegistrationViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val  scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val navController = LocalNavController.current

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val alertShown = registrationViewModel.alertShown

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    )
    {
        AuraOutlinedTextField(
            value = registrationViewModel.email,
            onValueChange = { registrationViewModel.email = it },
            label = stringResource(R.string.email_title),
            isError = alertShown && !registrationViewModel.isEmailValid,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        AuraPasswordField(
            value = registrationViewModel.password,
            onValueChange = { registrationViewModel.password = it },
            label = stringResource(R.string.password_title),
            isError = alertShown && !registrationViewModel.isPasswordValid,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        AuraPasswordField(
            value = registrationViewModel.confirmPassword,
            onValueChange = { registrationViewModel.confirmPassword = it },
            label = stringResource(R.string.confirm_password_title),
            isError = alertShown && !registrationViewModel.isConfirmPasswordValid,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.size(24.dp))

        val validationError = registrationViewModel.validateLogin()
        val errorEmail = stringResource(R.string.error_email_invalid)
        val errorPasswordRequired = stringResource(R.string.error_password_required)
        val errorPasswordMismatch = stringResource(R.string.error_password_mismatch)

        AuraTextButton(
            text = stringResource(R.string.about_title),
            modifier = Modifier.fillMaxWidth(),
        ) {
            if (validationError == null) {
                scope.launch {
                    focusManager.clearFocus()
                    keyboardController?.hide()

                    registrationViewModel.clearErrors()
                    pagerState.animateScrollToPage(2)
                }
            } else {
                if (!registrationViewModel.isEmailValid) {
                    registrationViewModel.showError(errorEmail)
                }
                if (registrationViewModel.password.isBlank()) {
                    registrationViewModel.showError(errorPasswordRequired)
                } else if (registrationViewModel.password != registrationViewModel.confirmPassword) {
                    registrationViewModel.showError(errorPasswordMismatch)
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        AuraTextButton(
            text = stringResource(R.string.skip),
            modifier = Modifier.fillMaxWidth(),
        ) {
            if (validationError == null) {
                registrationViewModel.register {
                    focusManager.clearFocus()
                    keyboardController?.hide()

                    navController.nav(TabsHost)
                }
            } else {
                if (!registrationViewModel.isEmailValid) {
                    registrationViewModel.showError(errorEmail)
                }
                if (registrationViewModel.password.isBlank()) {
                    registrationViewModel.showError(errorPasswordRequired)
                } else if (registrationViewModel.password != registrationViewModel.confirmPassword) {
                    registrationViewModel.showError(errorPasswordMismatch)
                }
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun AboutPage(
    registrationViewModel: RegistrationViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val navController = LocalNavController.current
    val scrollState = rememberScrollState()

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    )
    {

        AuraOutlinedTextField(
            value = registrationViewModel.about,
            onValueChange = { registrationViewModel.about = it },
            label = stringResource(R.string.about_clue),
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))
        AuraTextButton(
            text = stringResource(R.string.action_login),
            modifier = Modifier.fillMaxWidth(),
        ) {
            registrationViewModel.register {
                focusManager.clearFocus()
                keyboardController?.hide()

                navController.nav(TabsHost)
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}