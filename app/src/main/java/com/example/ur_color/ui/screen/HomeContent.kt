package com.example.ur_color.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.ur_color.AppBottomNavigation
import com.example.ur_color.RootTab
import kotlinx.serialization.Serializable

@Serializable
data object Home: Screen

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun TabsScreen() {

    var currentTab by rememberSaveable {
        mutableStateOf(RootTab.MAIN)
    }
    val bottomBarState = remember { BottomBarState() }

    Box(Modifier.fillMaxSize()) {

        AnimatedContent(
            targetState = currentTab,
            transitionSpec = {
                if (targetState.index > initialState.index) {
                    slideInHorizontally { it } togetherWith
                            slideOutHorizontally { -it }
                } else {
                    slideInHorizontally { -it } togetherWith
                            slideOutHorizontally { it }
                }
            },
            label = "Tabs"
        ) { tab ->
            when (tab) {
                RootTab.MAIN -> Main(Main)
                RootTab.TESTS -> Tests(Tests)
                RootTab.PROFILE -> Profile(Profile())
            }
        }

        AnimatedVisibility(
            visible = bottomBarState.enabled && bottomBarState.visible,
            enter = slideInVertically { it } + fadeIn(),
            exit = slideOutVertically { it } + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            AppBottomNavigation(
                currentTab = currentTab,
                onTabSelected = { currentTab = it }
            )
        }
    }
}