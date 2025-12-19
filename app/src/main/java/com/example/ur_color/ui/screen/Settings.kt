package com.example.ur_color.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ur_color.R
import com.example.ur_color.data.local.dataManager.SystemDataManager
import com.example.ur_color.ui.AuraRadioButton
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.ui.screen.viewModel.ProfileViewModel
import com.example.ur_color.ui.screen.viewModel.SettingsViewModel
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.ThemeMode
import com.example.ur_color.ui.theme.ThemePalette
import com.example.ur_color.utils.LocalNavController
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object Settings: Screen

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val navController = LocalNavController.current

    val themeMode by SystemDataManager.theme.collectAsState()
    val palette by SystemDataManager.palette.collectAsState()

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.background),
    ) {
        CustomAppBar(
            title = stringResource(R.string.settings_title),
            showBack = true,
            onBackClick = { navController.popBack() },
            showDivider = true,
            isCentered = false,
            backgroundColor = AppColors.background,
//            modifier = Modifier.align(Alignment.TopCenter)
        )

        Column(
            modifier = Modifier
                .background(AppColors.background)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.settings_theme_title),
                color = AppColors.textPrimary,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            AuraRadioButton(
                selected = themeMode == ThemeMode.SYSTEM,
                text = stringResource(R.string.settings_theme_system),
                onClick = {
                    scope.launch { SystemDataManager.saveTheme(context, ThemeMode.SYSTEM) }
                }
            )
            AuraRadioButton(
                selected = themeMode == ThemeMode.LIGHT,
                text = stringResource(R.string.settings_theme_light),
                onClick = {
                    scope.launch { SystemDataManager.saveTheme(context, ThemeMode.LIGHT) }
                }
            )
            AuraRadioButton(
                selected = themeMode == ThemeMode.DARK,
                text = stringResource(R.string.settings_theme_dark),
                onClick = {
                    scope.launch { SystemDataManager.saveTheme(context, ThemeMode.DARK) }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.settings_palette_title),
                color = AppColors.textPrimary,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            settingsViewModel.paletteItems.forEach { item ->
                AuraRadioButton(
                    selected = palette == item.palette,
                    text = stringResource(item.titleRes),
                    color = item.color,
                    onClick = {
                        scope.launch {
                            SystemDataManager.savePalette(context, item.palette)
                        }
                    }
                )
            }
        }
    }
}