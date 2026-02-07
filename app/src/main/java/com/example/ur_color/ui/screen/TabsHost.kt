package com.example.ur_color.ui.screen

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
data object TabsHost : Screen

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
                    RootTab.BAZAR -> Bazar(Bazar)
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

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
            .height(92.dp)
    ) {

        Surface(
            shape = RoundedCornerShape(24.dp),
            color = AppColors.background,
            shadowElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .align(Alignment.BottomCenter)
        ) {

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            val tabs = listOf(
                RootTab.MAIN to R.drawable.ball_crystal,
                RootTab.LAB to R.drawable.card_trick,
                RootTab.BAZAR to R.drawable.cauldron_potion,
                RootTab.PROFILE to R.drawable.illusion_eye
            )

            tabs.forEach { (tab, iconRes) ->
                FloatingNavItem(
                    tab = tab,
                    iconRes = iconRes,
                    label = when (tab) {
                        RootTab.MAIN -> stringResource(R.string.bottom_menu_main)
                        RootTab.LAB -> stringResource(R.string.bottom_menu_lab)
                        RootTab.BAZAR -> stringResource(R.string.bottom_menu_bazar)
                        RootTab.PROFILE -> stringResource(R.string.bottom_menu_profile)
                    },
                    isSelected = currentTab == tab,
                    onClick = { onTabSelected(tab) }
                )
            }
        }
    }
}

@Composable
private fun FloatingNavItem(
    tab: RootTab,
    @DrawableRes iconRes: Int,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    val offsetY by animateDpAsState(
        targetValue = if (isSelected) (-24).dp else 0.dp,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing // Плавное начало и завершение
        ),
        label = "offsetY_$tab"
    )

    val iconScale by animateFloatAsState(
        targetValue = if (isSelected) 1.15f else 1f,
        animationSpec = tween(300, easing = FastOutSlowInEasing),
        label = "iconScale_$tab"
    )

    val color = AppColors.accentPrimary

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .offset(y = offsetY)
            .size(64.dp)
            .clickable(
                indication = ripple(bounded = false, radius = 36.dp),
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick()
            }
    ) {

        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(32.dp))
                .background(
                    color = if (isSelected) AppColors.accentPrimary else AppColors.background.copy(alpha = 0.6f)
                )
                .drawWithContent {
                    drawContent()
                    if (isSelected) {
                        drawCircle(
                            color = color.copy(alpha = 0.25f),
                            radius = (size.width * 0.65f),
                            center = center
                        )
                    }
                }
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(vertical = if (isSelected) 4.dp else 8.dp)
        ) {
            Icon(
                modifier = Modifier
                    .size(if (isSelected) 30.dp else 24.dp)
                    .graphicsLayer(scaleX = iconScale, scaleY = iconScale),
                painter = painterResource(iconRes),
                contentDescription = label,
                tint = if (isSelected) Color.White else AppColors.textSecondary
            )

            if (!isSelected) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = label,
                    color = AppColors.textSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Thin,
                    maxLines = 1
                )
            }
        }
    }
}


enum class RootTab(val index: Int) {
    MAIN(0),
    LAB(1),
    BAZAR(2),
    PROFILE(3)
}