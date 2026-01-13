package com.example.ur_color.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.ur_color.ui.theme.AppScaffold
import com.example.ur_color.ui.theme.ThemeMode
import com.example.ur_color.ui.theme.ThemePalette
import com.example.ur_color.utils.LocalNavController
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
object Settings: Screen

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun Settings(settings : Settings) {
    AppScaffold(
        showBottomBar = false,
        topBar = {
            val navController = LocalNavController.current

            CustomAppBar(
                title = stringResource(R.string.settings_title),
                showBack = true,
                onBackClick = { navController.popBack() },
                showDivider = true,
                isCentered = false,
                backgroundColor = AppColors.background,
            )
        },
    ) {
        SettingsScreen(
            modifier = Modifier.padding(it)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val navController = LocalNavController.current
    val profileViewModel: ProfileViewModel = koinViewModel()

    val themeMode by SystemDataManager.theme.collectAsState()
    val palette by SystemDataManager.palette.collectAsState()

    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.background),
    ) {

        Column(
            modifier = Modifier
                .background(AppColors.background)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            var expandedProfile by remember { mutableStateOf(false) }
            Text(
                text = stringResource(R.string.settings_palette_profile),
                color = AppColors.textPrimary,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        expandedProfile = !expandedProfile
                    }
                    .padding(top = 4.dp, start = 4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedVisibility(visible = expandedProfile) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                ) {
                    Text(
                        stringResource(R.string.profile_edit),
                        style = MaterialTheme.typography.bodyLarge,
                        color = AppColors.textPrimary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                scope.launch {
                                    navController.nav(EditProfile)
                                }
                            }
                            .padding(4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            var expandedTheme by remember { mutableStateOf(false) }
            Text(
                text = stringResource(R.string.settings_theme_title),
                color = AppColors.textPrimary,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandedTheme = !expandedTheme }
                    .padding(top = 4.dp, start = 4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedVisibility(visible = expandedTheme) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                ) {
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
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            var expandedPalette by remember { mutableStateOf(false) }
            Text(
                text = stringResource(R.string.settings_palette_title),
                color = AppColors.textPrimary,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandedPalette = !expandedPalette }
                    .padding(top = 4.dp, start = 4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedVisibility(visible = expandedPalette) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                ) {
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

            Spacer(modifier = Modifier.height(24.dp))

            var expandedAccount by remember { mutableStateOf(false) }
            Text(
                text = stringResource(R.string.settings_palette_account),
                color = AppColors.textPrimary,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expandedAccount = !expandedAccount }
                    .padding(top = 4.dp, start = 4.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedVisibility(visible = expandedAccount) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp)
                ) {
                    Text(
                        stringResource(R.string.profile_logout),
                        style = MaterialTheme.typography.bodyLarge,
                        color = AppColors.textPrimary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                scope.launch {
                                    profileViewModel.deleteUser(context)
                                    navController.nav(Login, true)
                                }
                            }
                            .padding(4.dp)
                    )
                    Text(
                        stringResource(R.string.profile_delete),
                        style = MaterialTheme.typography.bodyLarge,
                        color = AppColors.error,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                            }
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}