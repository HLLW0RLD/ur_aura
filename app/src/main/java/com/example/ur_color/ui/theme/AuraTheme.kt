package com.example.ur_color.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.ur_color.data.local.PrefCache
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
fun AuraTheme(
    isSystemDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val view = LocalView.current
    val window = view.context.findActivity().window
    val insetsController = WindowCompat.getInsetsController(window, view)

    val selectedColorPalette = ThemePalette.TURQUOISE

    val dayNightColorScheme = remember(PrefCache.themeChanged.value, selectedColorPalette) {
        when (selectedColorPalette) {

            ThemePalette.PINK -> when (PrefCache.selectedTheme) {
                ThemeMode.SYSTEM -> if (isSystemDark) DarkColorSchemePink else LightColorSchemePink
                ThemeMode.DARK -> DarkColorSchemePink
                ThemeMode.LIGHT -> LightColorSchemePink
            }

            ThemePalette.YELLOW -> when (PrefCache.selectedTheme) {
                ThemeMode.SYSTEM -> if (isSystemDark) DarkYellowColorScheme else LightYellowColorScheme
                ThemeMode.DARK -> DarkYellowColorScheme
                ThemeMode.LIGHT -> LightYellowColorScheme
            }

            ThemePalette.BLUE -> when (PrefCache.selectedTheme) {
                ThemeMode.SYSTEM -> if (isSystemDark) DarkBlueColorScheme else LightBlueColorScheme
                ThemeMode.DARK -> DarkBlueColorScheme
                ThemeMode.LIGHT -> LightBlueColorScheme
            }

            ThemePalette.TURQUOISE -> when (PrefCache.selectedTheme) {
                ThemeMode.SYSTEM -> if (isSystemDark) DarkTurquoiseColorScheme else LightTurquoiseColorScheme
                ThemeMode.DARK -> DarkTurquoiseColorScheme
                ThemeMode.LIGHT -> LightTurquoiseColorScheme
            }

            ThemePalette.GREEN -> when (PrefCache.selectedTheme) {
                ThemeMode.SYSTEM -> if (isSystemDark) DarkGreenColorScheme else LightGreenColorScheme
                ThemeMode.DARK -> DarkGreenColorScheme
                ThemeMode.LIGHT -> LightGreenColorScheme
            }

            ThemePalette.BURGUNDY -> when (PrefCache.selectedTheme) {
                ThemeMode.SYSTEM -> if (isSystemDark) DarkBurgundyColorScheme else LightBurgundyColorScheme
                ThemeMode.DARK -> DarkBurgundyColorScheme
                ThemeMode.LIGHT -> LightBurgundyColorScheme
            }
            else -> {
                when (PrefCache.selectedTheme) {
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