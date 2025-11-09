package com.example.ur_color.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.ur_color.data.local.dataManager.SystemDataManager
import com.example.ur_color.utils.findActivity

enum class ThemeMode {
    SYSTEM, LIGHT, DARK
}

enum class ThemePalette {
    PINK,
    YELLOW,
    BLUE,
    TURQUOISE,
    GREEN,
    BURGUNDY
}

@Composable
fun AppTheme(
    isSystemDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val view = LocalView.current
    val window = view.context.findActivity().window
    val insetsController = WindowCompat.getInsetsController(window, view)

    val selectedColorPalette = ThemePalette.BURGUNDY

    val dayNightColorScheme = remember(SystemDataManager.theme, selectedColorPalette) {
        when (selectedColorPalette) {

            ThemePalette.PINK -> when (SystemDataManager.theme.value) {
                ThemeMode.SYSTEM -> if (isSystemDark) DarkColorSchemePink else LightColorSchemePink
                ThemeMode.DARK -> DarkColorSchemePink
                ThemeMode.LIGHT -> LightColorSchemePink
            }

            ThemePalette.YELLOW -> when (SystemDataManager.theme.value) {
                ThemeMode.SYSTEM -> if (isSystemDark) DarkYellowColorScheme else LightYellowColorScheme
                ThemeMode.DARK -> DarkYellowColorScheme
                ThemeMode.LIGHT -> LightYellowColorScheme
            }

            ThemePalette.BLUE -> when (SystemDataManager.theme.value) {
                ThemeMode.SYSTEM -> if (isSystemDark) DarkBlueColorScheme else LightBlueColorScheme
                ThemeMode.DARK -> DarkBlueColorScheme
                ThemeMode.LIGHT -> LightBlueColorScheme
            }

            ThemePalette.TURQUOISE -> when (SystemDataManager.theme.value) {
                ThemeMode.SYSTEM -> if (isSystemDark) DarkTurquoiseColorScheme else LightTurquoiseColorScheme
                ThemeMode.DARK -> DarkTurquoiseColorScheme
                ThemeMode.LIGHT -> LightTurquoiseColorScheme
            }

            ThemePalette.GREEN -> when (SystemDataManager.theme.value) {
                ThemeMode.SYSTEM -> if (isSystemDark) DarkGreenColorScheme else LightGreenColorScheme
                ThemeMode.DARK -> DarkGreenColorScheme
                ThemeMode.LIGHT -> LightGreenColorScheme
            }

            ThemePalette.BURGUNDY -> when (SystemDataManager.theme.value) {
                ThemeMode.SYSTEM -> if (isSystemDark) DarkBurgundyColorScheme else LightBurgundyColorScheme
                ThemeMode.DARK -> DarkBurgundyColorScheme
                ThemeMode.LIGHT -> LightBurgundyColorScheme
            }
            else -> {
                when (SystemDataManager.theme.value) {
                    ThemeMode.SYSTEM -> if (isSystemDark) DarkColorSchemePink else LightColorSchemePink
                    ThemeMode.DARK -> DarkColorSchemePink
                    ThemeMode.LIGHT -> LightColorSchemePink
                }
            }
        }
    }

    SideEffect {
        window.statusBarColor = dayNightColorScheme.background.toArgb()
        insetsController.isAppearanceLightStatusBars = dayNightColorScheme != DarkColorSchemePink
        window.navigationBarColor = dayNightColorScheme.background.toArgb()
    }

    MaterialTheme(
        colorScheme = dayNightColorScheme,
        content = content
    )
}