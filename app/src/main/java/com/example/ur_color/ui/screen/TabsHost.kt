package com.example.ur_color.ui.screen

import android.os.Build
import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ur_color.R
import com.example.ur_color.ui.theme.AppColors
import kotlinx.serialization.Serializable

@Serializable
data object TabsHost: Screen

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun TabsHostScreen() {

    var currentTab by rememberSaveable {
        mutableStateOf(RootTab.MAIN)
    }
    val bottomBarState = remember { BottomBarState() }
    val scrollThreshold = 24f
    var accumulatedScroll by remember { mutableFloatStateOf(0f) }

    Box(Modifier.fillMaxSize()) {

        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    if (source != NestedScrollSource.Drag) return Offset.Zero
                    accumulatedScroll += available.y

                    when {
                        accumulatedScroll > scrollThreshold &&
                                !bottomBarState.visible -> {
                            bottomBarState.visible = true
                            accumulatedScroll = 0f
                        }

                        accumulatedScroll < -scrollThreshold &&
                                bottomBarState.visible -> {
                            bottomBarState.visible = false
                            accumulatedScroll = 0f
                        }
                    }

                    return Offset.Zero
                }

                override suspend fun onPostFling(
                    consumed: Velocity,
                    available: Velocity
                ): Velocity {
                    // сбрасываем после fling
                    accumulatedScroll = 0f
                    return Velocity.Zero
                }
            }
        }

        Box(
            Modifier
                .fillMaxSize()
                .nestedScroll(nestedScrollConnection)
        ) {
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
                    RootTab.LAB -> Lab(Lab)
                    RootTab.PROFILE -> Profile(Profile())
                }
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

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun AppBottomNavigation(
    modifier: Modifier = Modifier,
    currentTab: RootTab,
    onTabSelected: (RootTab) -> Unit
) {

    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
            .background(
                color = AppColors.background.copy(alpha = 0.8f),
                shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)
            )
            .border(
                shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp),
                color = AppColors.surface,
                width = 1.dp
            )
            .height(72.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        BottomNavItem(
            modifier = Modifier
                .weight(1f),
            icon = R.drawable.ball_crystal,
            text = stringResource(R.string.bottom_menu_main),
            selected = currentTab == RootTab.MAIN,
            onClick = {
                onTabSelected(RootTab.MAIN)
            }
        )

        BottomNavItem(
            modifier = Modifier
                .weight(1f),
            icon = R.drawable.card_trick,
            text = stringResource(R.string.bottom_menu_lab),
            selected = currentTab == RootTab.LAB,
            onClick = {
                onTabSelected(RootTab.LAB)
            }
        )

//        таб с "Ярмаркой" где находитсся контент создаваемый пользователями
//        BottomNavItem(
//            modifier = Modifier
//                .weight(1f),
//            icon = R.drawable.Ярмарка,
//            text = Ярмарка,
//            selected = currentTab == RootTab.Ярмарка,
//            onClick = {
//                onTabSelected(RootTab.Ярмарка)
//            }
//        )

        BottomNavItem(
            modifier = Modifier
                .weight(1f),
            icon = R.drawable.illusion_eye,
            text = stringResource(R.string.bottom_menu_profile),
            selected = currentTab == RootTab.PROFILE,
            onClick = {
                onTabSelected(RootTab.PROFILE)
            }
        )
    }
}

@Composable
fun BottomNavItem(
    text: String,
    @DrawableRes icon: Int,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = if (selected)
                AppColors.accentPrimary
            else
                AppColors.textSecondary
        )
        Text(
            text = text,
            color = if (selected)
                AppColors.accentPrimary
            else
                AppColors.textSecondary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Thin
        )
    }
}

enum class RootTab(val index: Int) {
    MAIN(0),
    LAB(1),
    PROFILE(2)
}