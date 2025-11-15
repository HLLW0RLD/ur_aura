package com.example.ur_color.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.ur_color.data.local.dataManager.SystemDataManager
import com.example.ur_color.ui.AuraRadioButton
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.ThemeMode
import com.example.ur_color.ui.theme.ThemePalette
import com.example.ur_color.utils.LocalNavController
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
object Settings: Screen

@Composable
fun SettingsScreen() {
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
            title = "Settings",
            showBack = true,
            onBackClick = { navController.popBackStack() },
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

            // --- Тема ---
            Text(
                "Тема",
                color = AppColors.textPrimary,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            AuraRadioButton(
                selected = themeMode == ThemeMode.SYSTEM,
                text = "Системная",
                onClick = {
                    scope.launch { SystemDataManager.saveTheme(context, ThemeMode.SYSTEM) }
                }
            )
            AuraRadioButton(
                selected = themeMode == ThemeMode.LIGHT,
                text = "Светлая",
                onClick = {
                    scope.launch { SystemDataManager.saveTheme(context, ThemeMode.LIGHT) }
                }
            )
            AuraRadioButton(
                selected = themeMode == ThemeMode.DARK,
                text = "Тёмная",
                onClick = {
                    scope.launch { SystemDataManager.saveTheme(context, ThemeMode.DARK) }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Цветовая палитра",
                color = AppColors.textPrimary,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            AuraRadioButton(
                selected = palette == ThemePalette.PINK,
                text = "Розовая",
                color = Color(0xFFE91E63),
                onClick = {
                    scope.launch { SystemDataManager.savePalette(context, ThemePalette.PINK) }
                }
            )
            AuraRadioButton(
                selected = palette == ThemePalette.BLUE,
                text = "Синяя",
                color = Color(0xFF2196F3),
                onClick = {
                    scope.launch { SystemDataManager.savePalette(context, ThemePalette.BLUE) }
                }
            )
            AuraRadioButton(
                selected = palette == ThemePalette.YELLOW,
                text = "Жёлтая",
                color = Color(0xFFFFC107),
                onClick = {
                    scope.launch { SystemDataManager.savePalette(context, ThemePalette.YELLOW) }
                }
            )
            AuraRadioButton(
                selected = palette == ThemePalette.TURQUOISE,
                text = "Бирюзовая",
                color = Color(0xFF00BCD4),
                onClick = {
                    scope.launch { SystemDataManager.savePalette(context, ThemePalette.TURQUOISE) }
                }
            )
            AuraRadioButton(
                selected = palette == ThemePalette.GREEN,
                text = "Зелёная",
                color = Color(0xFF4CAF50),
                onClick = {
                    scope.launch { SystemDataManager.savePalette(context, ThemePalette.GREEN) }
                }
            )
            AuraRadioButton(
                selected = palette == ThemePalette.BURGUNDY,
                text = "Бордовая",
                color = Color(0xFF800020),
                onClick = {
                    scope.launch { SystemDataManager.savePalette(context, ThemePalette.BURGUNDY) }
                }
            )
        }
    }
}