package com.example.ur_color.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ur_color.R
import com.example.ur_color.ui.AuraDateTimePickerField
import com.example.ur_color.ui.AuraOutlinedTextField
import com.example.ur_color.ui.AuraPasswordField
import com.example.ur_color.ui.AuraTextButton
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.ui.screen.viewModel.LoginViewModel
import com.example.ur_color.ui.screen.viewModel.RegistrationViewModel
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.AppScaffold
import com.example.ur_color.utils.LocalNavController
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object Login : Screen

@Composable
fun Login(login: Login) {
    AppScaffold(
        showBottomBar = false,
        topBar = {
//            CustomAppBar(
//                title = stringResource(R.string.app_name_stylized),
//            )
        },
    ) {
        LoginScreen(
            modifier = Modifier
                .imePadding()
                .padding(it)
        )
    }
}

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val navController = LocalNavController.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.background)
            .imePadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp),
            text = stringResource(R.string.enter_title),
            fontSize = 36.sp,
            color = AppColors.textPrimary,
            textAlign = TextAlign.Start
        )
        Spacer(Modifier.size(36.dp))

        AuraOutlinedTextField(
            value = loginViewModel.email,
            onValueChange = { loginViewModel.email = it },
            label = stringResource(R.string.email_title),
            modifier = Modifier
                .imePadding()
                .fillMaxWidth()
        )
        Spacer(Modifier.size(8.dp))
        AuraPasswordField(
            value = loginViewModel.password,
            onValueChange = { loginViewModel.password = it },
            label = stringResource(R.string.password_title),
            modifier = Modifier
                .imePadding()
                .fillMaxWidth()
        )

        Spacer(Modifier.size(36.dp))
        AuraTextButton(
            text = stringResource(R.string.action_login),
            enabled = loginViewModel.isLoginValid,
//            enabled = true,
            modifier = Modifier.fillMaxWidth(),
        ) {
            loginViewModel.login {
                navController.nav(TabsHost)
            }
        }
    }
}