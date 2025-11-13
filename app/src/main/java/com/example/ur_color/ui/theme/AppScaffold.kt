package com.example.ur_color.ui.theme

import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ur_color.utils.findActivity

@Composable
fun AppScaffold(
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    navigationColor: Color = AppColors.surface,
    isFullscreen: Boolean = false,
    screenOrientation: Int = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = Modifier.windowInsetsPadding(
            if (!isFullscreen) WindowInsets.safeDrawing.only(WindowInsetsSides.Top)
            else WindowInsets(0, 0, 0, 0)
        ),
        topBar = topBar,
        bottomBar = {
            Column {
                bottomBar()
                if (!isFullscreen) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .windowInsetsBottomHeight(WindowInsets.safeDrawing)
                            .background(navigationColor)
                    )
                }
            }
        },
//        snackbarHost = { SnackbarHost(hostState = LocalSnackbarHostState.current) },
        content = { content(it) },
    )

    val context = LocalContext.current
    val view = LocalView.current
    LaunchedEffect(isFullscreen) {
        WindowCompat.getInsetsController(context.findActivity().window, view).apply {
            val isShown = WindowInsetsCompat.toWindowInsetsCompat(view.rootWindowInsets, view).let {
                it.isVisible(WindowInsetsCompat.Type.statusBars()) ||
                        it.isVisible(WindowInsetsCompat.Type.navigationBars()) ||
                        it.isVisible(WindowInsetsCompat.Type.captionBar())
            }
            val isHidden = (!WindowInsetsCompat.toWindowInsetsCompat(view.rootWindowInsets, view)
                .isVisible(WindowInsetsCompat.Type.systemBars()))

            if (isFullscreen && isShown) {
                hide(WindowInsetsCompat.Type.systemBars())
            } else if (!isFullscreen && isHidden) {
                show(WindowInsetsCompat.Type.systemBars())
            }
        }
    }

    LaunchedEffect(screenOrientation) {
        context.findActivity().requestedOrientation = screenOrientation
    }
}