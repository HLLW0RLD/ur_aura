package com.example.ur_color.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ur_color.R
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.ui.screen.viewModel.OnboardingViewModel
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.AppScaffold
import com.example.ur_color.utils.LocalNavController
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object Onboarding : Screen

@Composable
fun Onboarding(onboarding: Onboarding) {
    AppScaffold(
        showBottomBar = false,
        topBar = {},
    ) {
        OnboardingScreen(
            modifier = Modifier
                .imePadding()
                .padding(it)
        )
    }
}

@Composable
fun OnboardingScreen(
    onboardingViewModel: OnboardingViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val navController = LocalNavController.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.background)

    ) {

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.onboarding_description),
                color = AppColors.textPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 32.dp)
            )

            Button(
                onClick = {
                    navController.nav(Login)
                },
                enabled = false,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.accentPrimary)
            ) {
                Text(stringResource(R.string.action_login))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    navController.nav(Registration)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.accentPrimary)
            ) {
                Text(stringResource(R.string.action_register))
            }
        }
    }
}
