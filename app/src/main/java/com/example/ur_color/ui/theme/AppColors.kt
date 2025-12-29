package com.example.ur_color.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

object AppColors {

    val white: Color
        @Composable get() = Color.White
    val black: Color
        @Composable get() = Color.Black


    val textPrimary: Color
        @Composable get() = MaterialTheme.colorScheme.onBackground
    val textSecondary: Color
        @Composable get() = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
    val textAuto: (Color) -> Color
        @Composable get() = { bg ->
            if (bg.luminance() > 0.5f) Color.Black else Color.White
        }


    val accentPrimary: Color
        @Composable get() = MaterialTheme.colorScheme.primary
    val accentSecondary: Color
        @Composable get() = MaterialTheme.colorScheme.secondary


    val background: Color
        @Composable get() = MaterialTheme.colorScheme.background
    val backgroundLight: Color
        @Composable get() = background.lighten(0.1f)
    val backgroundDark: Color
        @Composable get() = background.darken(0.1f)


    val surface: Color
        @Composable get() = MaterialTheme.colorScheme.surface
    val surfaceLight: Color
        @Composable get() = surface.lighten(0.1f)
    val surfaceDark: Color
        @Composable get() = surface.darken(0.1f)


    val error: Color
        @Composable get() = MaterialTheme.colorScheme.error
    val success: Color
        @Composable get() = Color(0xFF4CAF50) // Зеленый для успеха


    val icon: Color @Composable get() = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)
    val divider: Color @Composable get() = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
    val shadow: Color @Composable get() = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.3f)

    fun Color.lighten(fraction: Float): Color {
        return Color(
            red = red + (1f - red) * fraction,
            green = green + (1f - green) * fraction,
            blue = blue + (1f - blue) * fraction,
            alpha = alpha
        )
    }

    fun Color.darken(fraction: Float): Color {
        return Color(
            red = red * (1f - fraction),
            green = green * (1f - fraction),
            blue = blue * (1f - fraction),
            alpha = alpha
        )
    }
}

// Основные акцентные цвета — пастельно-розовая гамма
val AccentLightPrimaryPink = Color(0xFFB49898)
val AccentLightSecondaryPink = Color(0xFFCC9AA2)
val AccentLightTertiaryPink = Color(0xFF995566)

val AccentDarkPrimaryPink = Color(0xFFB49898)
val AccentDarkSecondaryPink = Color(0xFFCC9AA2)
val AccentDarkTertiaryPink = Color(0xFF995566)

val LightColorSchemePink = lightColorScheme(
    primary = AccentLightPrimaryPink,
    secondary = AccentLightSecondaryPink,
    primaryContainer = AccentLightTertiaryPink,
    secondaryContainer = AccentLightTertiaryPink,
    background = Color(0xFFECECEC),
    surface = Color(0xFFDEDEDE),
    tertiary = Color(0xFFBDBDBD),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onTertiary = Color(0xFF000000),
    onBackground = Color(0xFF000000),
    onSurface = Color(0xFF000000),
    onError = Color(0xFFFFFFFF)
)

val DarkColorSchemePink = darkColorScheme(
    primary = AccentDarkPrimaryPink,
    secondary = AccentDarkSecondaryPink,
    primaryContainer = AccentDarkTertiaryPink,
    secondaryContainer = AccentDarkTertiaryPink,
    background = Color(0xFF1E1E1E), // однотонно
    surface = Color(0xFF2A2A2A), // однотонно
    tertiary = Color(0xFF5A4A4A),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF),
    onError = Color(0xFF000000)
)


val AccentYellowLightPrimary = Color(0xFFB8A46A)
val AccentYellowLightSecondary = Color(0xFFD4C080)
val AccentYellowLightTertiary = Color(0xFF8C7840)

val AccentYellowDarkPrimary = Color(0xFFB8A46A)
val AccentYellowDarkSecondary = Color(0xFFD4C080)
val AccentYellowDarkTertiary = Color(0xFF8C7840)

val LightYellowColorScheme = lightColorScheme(
    primary = AccentYellowLightPrimary,
    secondary = AccentYellowLightSecondary,
    primaryContainer = AccentYellowLightTertiary,
    secondaryContainer = AccentYellowLightTertiary,
    background = Color(0xFFECECEC),
    surface = Color(0xFFDEDEDE),
    tertiary = Color(0xFFBDBDBD),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFF000000),
    onSurface = Color(0xFF000000),
    onError = Color(0xFFFFFFFF)
)

val DarkYellowColorScheme = darkColorScheme(
    primary = AccentYellowDarkPrimary,
    secondary = AccentYellowDarkSecondary,
    primaryContainer = AccentYellowDarkTertiary,
    secondaryContainer = AccentYellowDarkTertiary,
    tertiary = Color(0xFF5A4A4A),
    background = Color(0xFF1E1E1E),
    surface = Color(0xFF2A2A2A),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF),
    onError = Color(0xFF000000)
)


val AccentBlueLightPrimary = Color(0xFF8AA8CC)
val AccentBlueLightSecondary = Color(0xFF6B8FBF)
val AccentBlueLightTertiary = Color(0xFF365A8C)

val AccentBlueDarkPrimary = Color(0xFF8AA8CC)
val AccentBlueDarkSecondary = Color(0xFF6B8FBF)
val AccentBlueDarkTertiary = Color(0xFF365A8C)

val LightBlueColorScheme = lightColorScheme(
    primary = AccentBlueLightPrimary,
    secondary = AccentBlueLightSecondary,
    primaryContainer = AccentBlueLightTertiary,
    secondaryContainer = AccentBlueLightTertiary,
    surface = Color(0xFFDEDEDE),
    tertiary = Color(0xFFBDBDBD),
    background = Color(0xFFECECEC),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFF000000),
    onSurface = Color(0xFF000000),
    onError = Color(0xFFFFFFFF)
)

val DarkBlueColorScheme = darkColorScheme(
    primary = AccentBlueDarkPrimary,
    secondary = AccentBlueDarkSecondary,
    primaryContainer = AccentBlueDarkTertiary,
    secondaryContainer = AccentBlueDarkTertiary,
    surface = Color(0xFF2A2A2A),
    tertiary = Color(0xFF364050),
    background = Color(0xFF1E1E1E),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF),
    onError = Color(0xFF000000)
)


val AccentTurquoiseLightPrimary = Color(0xFF80C1B7)
val AccentTurquoiseLightSecondary = Color(0xFF66B2A6)
val AccentTurquoiseLightTertiary = Color(0xFF2A7A6F)

val AccentTurquoiseDarkPrimary = Color(0xFF80C1B7)
val AccentTurquoiseDarkSecondary = Color(0xFF66B2A6)
val AccentTurquoiseDarkTertiary = Color(0xFF2A7A6F)

val LightTurquoiseColorScheme = lightColorScheme(
    primary = AccentTurquoiseLightPrimary,
    secondary = AccentTurquoiseLightSecondary,
    primaryContainer = AccentTurquoiseLightTertiary,
    secondaryContainer = AccentTurquoiseLightTertiary,
    background = Color(0xFFECECEC),
    surface = Color(0xFFDEDEDE),
    tertiary = Color(0xFFBDBDBD),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFF000000),
    onSurface = Color(0xFF000000),
    onError = Color(0xFFFFFFFF)
)

val DarkTurquoiseColorScheme = darkColorScheme(
    primary = AccentTurquoiseDarkPrimary,
    secondary = AccentTurquoiseDarkSecondary,
    primaryContainer = AccentTurquoiseDarkTertiary,
    secondaryContainer = AccentTurquoiseDarkTertiary,
    background = Color(0xFF1E1E1E),
    surface = Color(0xFF2A2A2A),
    tertiary = Color(0xFF40504A),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF),
    onError = Color(0xFF000000)
)


val AccentGreenLightPrimary = Color(0xFF99B98A)
val AccentGreenLightSecondary = Color(0xFF88A977)
val AccentGreenLightTertiary = Color(0xFF4E7340)

val AccentGreenDarkPrimary = Color(0xFF99B98A)
val AccentGreenDarkSecondary = Color(0xFF88A977)
val AccentGreenDarkTertiary = Color(0xFF4E7340)

val LightGreenColorScheme = lightColorScheme(
    primary = AccentGreenLightPrimary,
    secondary = AccentGreenLightSecondary,
    primaryContainer = AccentGreenLightTertiary,
    secondaryContainer = AccentGreenLightTertiary,
    surface = Color(0xFFDEDEDE),
    tertiary = Color(0xFFBDBDBD),
    background = Color(0xFFECECEC),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFF000000),
    onSurface = Color(0xFF000000),
    onError = Color(0xFFFFFFFF)
)

val DarkGreenColorScheme = darkColorScheme(
    primary = AccentGreenDarkPrimary,
    secondary = AccentGreenDarkSecondary,
    primaryContainer = AccentGreenDarkTertiary,
    secondaryContainer = AccentGreenDarkTertiary,
    background = Color(0xFF1E1E1E),
    surface = Color(0xFF2A2A2A),
    tertiary = Color(0xFF40483C),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF),
    onError = Color(0xFF000000)
)


val AccentBurgundyLightPrimary = Color(0xFFB4777F)
val AccentBurgundyLightSecondary = Color(0xFF995A62)
val AccentBurgundyLightTertiary = Color(0xFF5A1C25)

val AccentBurgundyDarkPrimary = Color(0xFFB4777F)
val AccentBurgundyDarkSecondary = Color(0xFF995A62)
val AccentBurgundyDarkTertiary = Color(0xFF5A1C25)

val LightBurgundyColorScheme = lightColorScheme(
    primary = AccentBurgundyLightPrimary,
    secondary = AccentBurgundyLightSecondary,
    primaryContainer = AccentBurgundyLightTertiary,
    secondaryContainer = AccentBurgundyLightTertiary,
    background = Color(0xFFECECEC),
    surface = Color(0xFFDEDEDE),
    tertiary = Color(0xFFBDBDBD),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFF000000),
    onSurface = Color(0xFF000000),
    onError = Color(0xFFFFFFFF)
)

val DarkBurgundyColorScheme = darkColorScheme(
    primary = AccentBurgundyDarkPrimary,
    secondary = AccentBurgundyDarkSecondary,
    primaryContainer = AccentBurgundyDarkTertiary,
    secondaryContainer = AccentBurgundyDarkTertiary,
    background = Color(0xFF1E1E1E),
    surface = Color(0xFF2A2A2A),
    tertiary = Color(0xFF4A3C3C),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF),
    onError = Color(0xFF000000)
)
