package com.example.ur_color.ui.theme

import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.ur_color.ui.screen.LocalBottomBarState
import com.example.ur_color.utils.findActivity

@Composable
fun AppScaffold(
    topBar: @Composable () -> Unit = {},
    showBottomBar: Boolean = false,
    bottomBar: (@Composable () -> Unit)? = null,
    navigationColor: Color = AppColors.background,
    isFullscreen: Boolean = false,
    screenOrientation: Int = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT,
    content: @Composable (PaddingValues) -> Unit,
) {
    val bottomBarState = LocalBottomBarState.current

    SideEffect {
        bottomBarState.visible = showBottomBar
    }

    Scaffold(
        topBar = topBar,
        bottomBar = {
            if (showBottomBar && bottomBar != null) {
                Column {
                    bottomBar()
                }
            }
        },
//        snackbarHost = { SnackbarHost(hostState = LocalSnackbarHostState.current) },
        content = { content(it) },
    )

    val context = LocalContext.current

    LaunchedEffect(screenOrientation) {
        context.findActivity().requestedOrientation = screenOrientation
    }
}