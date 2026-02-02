package com.example.ur_color.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.ui.screen.viewModel.TestConstructorViewModel
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.AppScaffold
import com.example.ur_color.utils.LocalNavController
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object TestConstructor : Screen

@Composable
fun TestConstructor(testConstructor : TestConstructor) {
    AppScaffold(
        showBottomBar = false,
        topBar = {
            val navController = LocalNavController.current

            CustomAppBar(
                title = stringResource(R.string.profile_daily_tests),
                showBack = true,
                onBackClick = {
                    navController.popBack()
                },
                showDivider = true,
                isCentered = false,
                backgroundColor = AppColors.background,
            )
        },
    ) {
        TestConstructorScreen(
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun TestConstructorScreen(
    testConstructorViewModel: TestConstructorViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val navController = LocalNavController.current

    val scrollState = rememberScrollState()

    var testText by remember { mutableStateOf("") }
    var agree by remember { mutableStateOf(0) }
    var disAgree by remember { mutableStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.background)
            .verticalScroll(scrollState)
            .imePadding(),
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
            value = testText,
            onValueChange = { testText = it },
            label = { Text("test text") },
            modifier = Modifier
                .imePadding()
                .fillMaxWidth()
        )

        Button(
            onClick = {

            }
        ) {
            Text(stringResource(R.string.action_login))
        }
    }
}