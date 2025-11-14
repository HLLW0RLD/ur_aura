package com.example.ur_color.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.ur_color.data.local.dataManager.SystemDataManager
import com.example.ur_color.utils.findActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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

    val context = LocalContext.current
    val view = LocalView.current
    val window = view.context.findActivity().window
    val insetsController = WindowCompat.getInsetsController(window, view)

//    для смены цвета: раскомментировать -> поменять -> закомментировать
//    LaunchedEffect(Unit) {
//        SystemDataManager.savePalette(context, ThemePalette.TURQUOISE)
//    }

    val currentTheme by SystemDataManager.theme.collectAsState()
    val paletteTheme by SystemDataManager.palette.collectAsState()

    val dayNightColorScheme = remember(currentTheme, paletteTheme) {
        when (paletteTheme) {

            ThemePalette.PINK -> when (currentTheme) {
                ThemeMode.SYSTEM -> if (isSystemDark) DarkColorSchemePink else LightColorSchemePink
                ThemeMode.DARK -> DarkColorSchemePink
                ThemeMode.LIGHT -> LightColorSchemePink
            }

            ThemePalette.YELLOW -> when (currentTheme) {
                ThemeMode.SYSTEM -> if (isSystemDark) DarkYellowColorScheme else LightYellowColorScheme
                ThemeMode.DARK -> DarkYellowColorScheme
                ThemeMode.LIGHT -> LightYellowColorScheme
            }

            ThemePalette.BLUE -> when (currentTheme) {
                ThemeMode.SYSTEM -> if (isSystemDark) DarkBlueColorScheme else LightBlueColorScheme
                ThemeMode.DARK -> DarkBlueColorScheme
                ThemeMode.LIGHT -> LightBlueColorScheme
            }

            ThemePalette.TURQUOISE -> when (currentTheme) {
                ThemeMode.SYSTEM -> if (isSystemDark) DarkTurquoiseColorScheme else LightTurquoiseColorScheme
                ThemeMode.DARK -> DarkTurquoiseColorScheme
                ThemeMode.LIGHT -> LightTurquoiseColorScheme
            }

            ThemePalette.GREEN -> when (currentTheme) {
                ThemeMode.SYSTEM -> if (isSystemDark) DarkGreenColorScheme else LightGreenColorScheme
                ThemeMode.DARK -> DarkGreenColorScheme
                ThemeMode.LIGHT -> LightGreenColorScheme
            }

            ThemePalette.BURGUNDY -> when (currentTheme) {
                ThemeMode.SYSTEM -> if (isSystemDark) DarkBurgundyColorScheme else LightBurgundyColorScheme
                ThemeMode.DARK -> DarkBurgundyColorScheme
                ThemeMode.LIGHT -> LightBurgundyColorScheme
            }
            else -> {
                when (currentTheme) {
                    ThemeMode.SYSTEM -> if (isSystemDark) DarkColorSchemePink else LightColorSchemePink
                    ThemeMode.DARK -> DarkColorSchemePink
                    ThemeMode.LIGHT -> LightColorSchemePink
                }
            }
        }
    }

    SideEffect {
        val window = (view.context as Activity).window

        window.statusBarColor = Color.Transparent.toArgb()
        window.navigationBarColor = Color.Transparent.toArgb()

        val controller = WindowCompat.getInsetsController(window, view)

        val lightIcons = dayNightColorScheme.background.luminance() > 0.5f

        controller.isAppearanceLightStatusBars = lightIcons
        controller.isAppearanceLightNavigationBars = lightIcons
    }

    MaterialTheme(
        colorScheme = dayNightColorScheme,
        content = content
    )
}