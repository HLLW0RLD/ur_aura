package com.example.ur_color.ui.screen.viewModel

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.example.ur_color.R
import com.example.ur_color.data.local.base.BaseViewModel
import com.example.ur_color.ui.theme.ThemePalette

class SettingsViewModel() : BaseViewModel() {

    val paletteItems = listOf(
        PaletteItem(
            ThemePalette.PINK,
            R.string.settings_palette_pink,
            Color(0xFFE91E63)
        ),
        PaletteItem(
            ThemePalette.BLUE,
            R.string.settings_palette_blue,
            Color(0xFF2196F3)
        ),
        PaletteItem(
            ThemePalette.YELLOW,
            R.string.settings_palette_yellow,
            Color(0xFFFFC107)
        ),
        PaletteItem(
            ThemePalette.TURQUOISE,
            R.string.settings_palette_turquoise,
            Color(0xFF00BCD4)
        ),
        PaletteItem(
            ThemePalette.GREEN,
            R.string.settings_palette_green,
            Color(0xFF4CAF50)
        ),
        PaletteItem(
            ThemePalette.BURGUNDY,
            R.string.settings_palette_burgundy,
            Color(0xFF800020)
        )
    )
}

data class PaletteItem(
    val palette: ThemePalette,
    @StringRes val titleRes: Int,
    val color: Color
)