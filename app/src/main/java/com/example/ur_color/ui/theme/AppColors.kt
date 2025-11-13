package com.example.ur_color.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object AppColors {

    val white: Color
        @Composable get() = Color.White
    val black: Color
        @Composable get() = Color.Black

    val tertiary: Color
        @Composable get() = MaterialTheme.colorScheme.tertiary

    val textPrimary: Color
        @Composable get() = MaterialTheme.colorScheme.onBackground
    val textSecondary: Color
        @Composable get() = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)

    val accentPrimary: Color
        @Composable get() = MaterialTheme.colorScheme.primary
    val accentSecondary: Color
        @Composable get() = MaterialTheme.colorScheme.secondary

    val background: Color
        @Composable get() = MaterialTheme.colorScheme.background
    val surface: Color
        @Composable get() = MaterialTheme.colorScheme.surface

    val error: Color
        @Composable get() = MaterialTheme.colorScheme.error
    val success: Color
        @Composable get() = Color(0xFF4CAF50) // Зеленый для успеха

    val divider: Color
        @Composable get() = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
}

// Основные акцентные цвета — пастельно-розовая гамма
val AccentLightPrimaryPink = Color(0xFFFFE0E5)
val AccentLightSecondaryPink = Color(0xFFFFB6C1)
val AccentLightTertiaryPink = Color(0xFFD46A8C)

val AccentDarkPrimaryPink = Color(0xFFB49898)
val AccentDarkSecondaryPink = Color(0xFFCC9AA2)
val AccentDarkTertiaryPink = Color(0xFF995566)

val LightColorSchemePink = lightColorScheme(
    primary = AccentLightPrimaryPink,
    secondary = AccentLightSecondaryPink,
    primaryContainer = AccentLightTertiaryPink,
    secondaryContainer = AccentLightTertiaryPink,
    surface = Color(0xFFFFD6DC),
    tertiary = Color(0xFFBDBDBD),
    background = Color(0xFFFFF9FA),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFF000000),
    onSurface = Color(0xFF000000),
    onError = Color(0xFFFFFFFF)
)

val DarkColorSchemePink = darkColorScheme(
    primary = AccentDarkPrimaryPink,
    secondary = AccentDarkSecondaryPink,
    primaryContainer = AccentDarkTertiaryPink,
    secondaryContainer = AccentDarkTertiaryPink,
    surface = Color(0xFFCF6679),
    tertiary = Color(0xFF5A4A4A),
    background = Color(0xFF1E1A1A),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF),
    onError = Color(0xFF000000)
)


val AccentYellowLightPrimary = Color(0xFFFFF4D6)
val AccentYellowLightSecondary = Color(0xFFFFE69A)
val AccentYellowLightTertiary = Color(0xFFD4B860)

val AccentYellowDarkPrimary = Color(0xFFB8A46A)
val AccentYellowDarkSecondary = Color(0xFFD4C080)
val AccentYellowDarkTertiary = Color(0xFF8C7840)

val LightYellowColorScheme = lightColorScheme(
    primary = AccentYellowLightPrimary,
    secondary = AccentYellowLightSecondary,
    primaryContainer = AccentYellowLightTertiary,
    secondaryContainer = AccentYellowLightTertiary,
    surface = Color(0xFFFFEFC2),
    tertiary = Color(0xFFBDBDBD),
    background = Color(0xFFFFFCF6),
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
    surface = Color(0xFFC4A54D),
    tertiary = Color(0xFF5A4A4A),
    background = Color(0xFF1E1C14),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF),
    onError = Color(0xFF000000)
)


val AccentBlueLightPrimary = Color(0xFFE0F0FF)
val AccentBlueLightSecondary = Color(0xFF9ACDFF)
val AccentBlueLightTertiary = Color(0xFF4A90E2)

val AccentBlueDarkPrimary = Color(0xFF8AA8CC)
val AccentBlueDarkSecondary = Color(0xFF6B8FBF)
val AccentBlueDarkTertiary = Color(0xFF365A8C)

val LightBlueColorScheme = lightColorScheme(
    primary = AccentBlueLightPrimary,
    secondary = AccentBlueLightSecondary,
    primaryContainer = AccentBlueLightTertiary,
    secondaryContainer = AccentBlueLightTertiary,
    surface = Color(0xFFD6E9FF),
    tertiary = Color(0xFFBDBDBD),
    background = Color(0xFFF8FBFF),
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
    surface = Color(0xFF3A6FA8),
    tertiary = Color(0xFF364050),
    background = Color(0xFF141A1E),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF),
    onError = Color(0xFF000000)
)


val AccentTurquoiseLightPrimary = Color(0xFFD6FFF8)
val AccentTurquoiseLightSecondary = Color(0xFF9AF2E4)
val AccentTurquoiseLightTertiary = Color(0xFF2FB7A6)

val AccentTurquoiseDarkPrimary = Color(0xFF80C1B7)
val AccentTurquoiseDarkSecondary = Color(0xFF66B2A6)
val AccentTurquoiseDarkTertiary = Color(0xFF2A7A6F)

val LightTurquoiseColorScheme = lightColorScheme(
    primary = AccentTurquoiseLightPrimary,
    secondary = AccentTurquoiseLightSecondary,
    primaryContainer = AccentTurquoiseLightTertiary,
    secondaryContainer = AccentTurquoiseLightTertiary,
    surface = Color(0xFFCCF5ED),
    tertiary = Color(0xFFBDBDBD),
    background = Color(0xFFF6FFFD),
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
    surface = Color(0xFF2FB7A6),
    tertiary = Color(0xFF40504A),
    background = Color(0xFF14201E),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF),
    onError = Color(0xFF000000)
)


val AccentGreenLightPrimary = Color(0xFFE3FCD6)
val AccentGreenLightSecondary = Color(0xFFC1EFA0)
val AccentGreenLightTertiary = Color(0xFF6BBF4E)

val AccentGreenDarkPrimary = Color(0xFF99B98A)
val AccentGreenDarkSecondary = Color(0xFF88A977)
val AccentGreenDarkTertiary = Color(0xFF4E7340)

val LightGreenColorScheme = lightColorScheme(
    primary = AccentGreenLightPrimary,
    secondary = AccentGreenLightSecondary,
    primaryContainer = AccentGreenLightTertiary,
    secondaryContainer = AccentGreenLightTertiary,
    surface = Color(0xFFD6F5C2),
    tertiary = Color(0xFFBDBDBD),
    background = Color(0xFFF9FFF6),
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
    surface = Color(0xFF548C45),
    tertiary = Color(0xFF40483C),
    background = Color(0xFF1A1E14),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF),
    onError = Color(0xFF000000)
)


val AccentBurgundyLightPrimary = Color(0xFFFFD6DA)
val AccentBurgundyLightSecondary = Color(0xFFFF9CA9)
val AccentBurgundyLightTertiary = Color(0xFF7A1C2F)

val AccentBurgundyDarkPrimary = Color(0xFFB4777F)
val AccentBurgundyDarkSecondary = Color(0xFF995A62)
val AccentBurgundyDarkTertiary = Color(0xFF5A1C25)

val LightBurgundyColorScheme = lightColorScheme(
    primary = AccentBurgundyLightPrimary,
    secondary = AccentBurgundyLightSecondary,
    primaryContainer = AccentBurgundyLightTertiary,
    secondaryContainer = AccentBurgundyLightTertiary,
    surface = Color(0xFFDB7986),
    tertiary = Color(0xFFBDBDBD),
    background = Color(0xFFFFF5F6),
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
    surface = Color(0xFF7A1C2F),
    tertiary = Color(0xFF4A3C3C),
    background = Color(0xFF1E1414),
    error = Color(0xFFB00020),
    onPrimary = Color(0xFF000000),
    onSecondary = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    onSurface = Color(0xFFFFFFFF),
    onError = Color(0xFF000000)
)
