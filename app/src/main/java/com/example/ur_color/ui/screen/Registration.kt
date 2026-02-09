package com.example.ur_color.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ur_color.R
import com.example.ur_color.ui.AuraDatePickerField
import com.example.ur_color.ui.AuraDateTimePickerField
import com.example.ur_color.ui.AuraOutlinedTextField
import com.example.ur_color.ui.AuraPasswordField
import com.example.ur_color.ui.AuraTextButton
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.ui.screen.viewModel.RegistrationViewModel
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.AppScaffold
import com.example.ur_color.utils.LocalNavController
import kotlinx.coroutines.launch
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

    val  scope = rememberCoroutineScope()
    val  state = rememberPagerState(initialPage = 0, pageCount = { 3 })

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.background),
        contentAlignment = Alignment.Center
    ) {
        HorizontalPager(
            state = state,
            userScrollEnabled = false
        ) { page ->
            when (page) {

                0 -> {
                    Box(
                        modifier = modifier
                            .background(AppColors.background)
                    )
                    {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .verticalScroll(scrollState)
                                .align(Alignment.TopCenter),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(36.dp),
                                text = stringResource(R.string.registration_title),
                                fontSize = 36.sp,
                                color = AppColors.textPrimary,
                                textAlign = TextAlign.Start
                            )
                            Spacer(Modifier.size(36.dp))

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
                            AuraTextButton(
                                text = stringResource(R.string.action_registration),
                                enabled = registrationViewModel.isUserValid,
                                modifier = Modifier
                                    .padding(24.dp)
                                    .fillMaxWidth(),
                            ) {
                                scope.launch {
                                    state.animateScrollToPage(1)
                                }
                            }
                        }
                    }
                }

                1 -> {
                    Box(
                        modifier = modifier
                            .background(AppColors.background)
                    ) {
                        Column(
                            modifier = modifier
                                .background(AppColors.background)
                                .imePadding()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                text = stringResource(R.string.login_title),
                                color = AppColors.textPrimary,
                                textAlign = TextAlign.Start
                            )
                            Spacer(Modifier.size(36.dp))

                            AuraOutlinedTextField(
                                value = registrationViewModel.email,
                                onValueChange = { registrationViewModel.email = it },
                                label = stringResource(R.string.email_title),
                                modifier = Modifier
                                    .imePadding()
                                    .fillMaxWidth()
                            )
                            Spacer(Modifier.size(8.dp))
                            AuraPasswordField(
                                value = registrationViewModel.password,
                                onValueChange = { registrationViewModel.password = it },
                                label = stringResource(R.string.password_title),
                                modifier = Modifier
                                    .imePadding()
                                    .fillMaxWidth()
                            )

                            Spacer(Modifier.size(36.dp))


                            AuraTextButton(
                                text = stringResource(R.string.about_button),
                                enabled = registrationViewModel.isLoginValid,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                scope.launch {
                                    state.animateScrollToPage(2)
                                }
                            }
                            Spacer(Modifier.size(12.dp))
                            AuraTextButton(
                                text = stringResource(R.string.action_login),
                                enabled = registrationViewModel.isLoginValid,
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                registrationViewModel.register {
                                    navController.nav(TabsHost)
                                }
                            }
                        }
                    }
                }

                2 -> {
                    Box(
                        modifier = modifier
                            .background(AppColors.background)
                    ) {
                        Column(
                            modifier = modifier
                                .background(AppColors.background)
                                .imePadding()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            AuraOutlinedTextField(
                                value = registrationViewModel.about,
                                onValueChange = { registrationViewModel.about = it },
                                label = stringResource(R.string.about_title),
                                modifier = Modifier
                                    .height(300.dp)
                                    .imePadding()
                                    .fillMaxWidth()
                            )
                            Spacer(Modifier.size(36.dp))

                            Spacer(Modifier.size(12.dp))
                            AuraTextButton(
                                text = stringResource(R.string.action_login),
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                registrationViewModel.register {
                                    navController.nav(TabsHost)
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(4.dp))

        DotsIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            totalDots = 3,
            selectedIndex = state.currentPage,
        )
    }
}

@Composable
fun DotsIndicator(
    totalDots : Int,
    selectedIndex : Int,
    selectedColor: Color = AppColors.accentPrimary,
    unSelectedColor: Color = AppColors.textPrimary,
    modifier: Modifier = Modifier
){

    LazyRow(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()

    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}
