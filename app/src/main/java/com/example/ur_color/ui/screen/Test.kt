package com.example.ur_color.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.ur_color.data.local.mocServece.LocalDailyTestService
import com.example.ur_color.ui.screen.viewModel.DailyTestViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import com.example.ur_color.R
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.ui.SwipeCard
import com.example.ur_color.ui.screen.viewModel.CustomTestViewModel
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.AppScaffold
import com.example.ur_color.utils.LocalNavController
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt

@Serializable
data class Test(
    val testId: String,
    val name: String = "Ежедневные тесты"
) : Screen

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun Test(test : Test) {
    AppScaffold(
        showBottomBar = false,
        topBar = {
            val navController = LocalNavController.current

            CustomAppBar(
                title = test.name,
                showBack = true,
                onBackClick = {
                    navController.popBack()
                },
                showDivider = true,
                isCentered = true,
                backgroundColor = AppColors.background,
            )
        },
    ) {
        when(test.testId) {
            "0" -> {
                DailyTestScreen(
                    modifier = Modifier.padding(it)
                )
            }
            else -> {
                CustomTestScreen(
                    modifier = Modifier.padding(it)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun CustomTestScreen(
    customTestViewModel: CustomTestViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val navController = LocalNavController.current

    val questions = remember { LocalDailyTestService().questionMods.toMutableStateList() }
    var currentIndex by remember { mutableStateOf(0) }
    val offsetX = remember { Animatable(0f) }

    LaunchedEffect(currentIndex) {
        if (currentIndex >= questions.size) {
//            customTestViewModel.updateAfterTest(context)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.background)
    ) {

        if (questions.isEmpty()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.all_test_done),
                color = AppColors.textPrimary,
            )
        } else {
            if (currentIndex >= questions.size) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.all_test_done),
                    style = MaterialTheme.typography.titleLarge,
                    color = AppColors.textPrimary,
                )
                return@Box
            }

            if (currentIndex + 1 < questions.size) {
                val nextCardAlpha by animateFloatAsState(
                    targetValue = 1f,
                    animationSpec = tween(800, easing = LinearOutSlowInEasing),
                    label = "nextCardAlpha"
                )
                val nextCardScale by animateFloatAsState(
                    targetValue = 0.96f,
                    animationSpec = tween(800, easing = LinearOutSlowInEasing),
                    label = "nextCardScale"
                )
                val nextCardTranslateY by animateFloatAsState(
                    targetValue = 20f,
                    animationSpec = tween(800, easing = LinearOutSlowInEasing),
                    label = "nextCardTranslateY"
                )

                SwipeCard(
                    text = questions[currentIndex + 1].text,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .graphicsLayer(
                            scaleX = nextCardScale,
                            scaleY = nextCardScale,
                            translationY = nextCardTranslateY,
                            alpha = nextCardAlpha
                        ),
                    textColor = AppColors.textSecondary
                )
            }

            val question = questions[currentIndex]

            val handleSwipeRight = {
                scope.launch {
                    offsetX.animateTo(1000f, tween(250))
//                    customTestViewModel.consumeAnswer(context, question, true)
                    currentIndex++
                    offsetX.snapTo(0f)
                }
            }

            val handleSwipeLeft = {
                scope.launch {
                    offsetX.animateTo(-1000f, tween(250))
//                    customTestViewModel.consumeAnswer(context, question, false)
                    currentIndex++
                    offsetX.snapTo(0f)
                }
            }

            SwipeCard(
                centerText = "${questions.size - currentIndex}",
                centerImg = painterResource(R.drawable.magic_sparkles),
                text = question.text,
                onSwipeLeft = { handleSwipeLeft() },
                onSwipeRight = { handleSwipeRight() },
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                    .graphicsLayer(rotationZ = offsetX.value / 30)
                    .clip(RoundedCornerShape(24.dp))
                    .shadow(8.dp, RoundedCornerShape(24.dp))
                    .pointerInput(currentIndex) {
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { _, dragAmount ->
                                scope.launch { offsetX.snapTo(offsetX.value + dragAmount) }
                            },
                            onDragEnd = {
                                scope.launch {
                                    when {
                                        offsetX.value > 250f -> handleSwipeRight()
                                        offsetX.value < -250f -> handleSwipeLeft()
                                        else -> offsetX.animateTo(0f, tween(200))
                                    }
                                }
                            }
                        )
                    },
                textColor = AppColors.textPrimary
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun DailyTestScreen(
    dailyTestViewModel: DailyTestViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val navController = LocalNavController.current

    val questions = remember { LocalDailyTestService().questionMods.toMutableStateList() }
    var currentIndex by remember { mutableStateOf(0) }
    val offsetX = remember { Animatable(0f) }

    LaunchedEffect(currentIndex) {
        if (currentIndex >= questions.size) {
            dailyTestViewModel.updateAfterTest()
        }
    }

    LaunchedEffect(Unit) {
        dailyTestViewModel.init()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.background)
    ) {

        if (questions.isEmpty()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(R.string.all_test_done),
                color = AppColors.textPrimary,
            )
        } else {
            if (currentIndex >= questions.size) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.all_test_done),
                    style = MaterialTheme.typography.titleLarge,
                    color = AppColors.textPrimary,
                )
                return@Box
            }

            if (currentIndex + 1 < questions.size) {
                val nextCardAlpha by animateFloatAsState(
                    targetValue = 1f,
                    animationSpec = tween(800, easing = LinearOutSlowInEasing),
                    label = "nextCardAlpha"
                )
                val nextCardScale by animateFloatAsState(
                    targetValue = 0.96f,
                    animationSpec = tween(800, easing = LinearOutSlowInEasing),
                    label = "nextCardScale"
                )
                val nextCardTranslateY by animateFloatAsState(
                    targetValue = 20f,
                    animationSpec = tween(800, easing = LinearOutSlowInEasing),
                    label = "nextCardTranslateY"
                )

                SwipeCard(
                    text = questions[currentIndex + 1].text,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .graphicsLayer(
                            scaleX = nextCardScale,
                            scaleY = nextCardScale,
                            translationY = nextCardTranslateY,
                            alpha = nextCardAlpha
                        ),
                    textColor = AppColors.textSecondary
                )
            }

            val question = questions[currentIndex]

            val handleSwipeRight = {
                scope.launch {
                    offsetX.animateTo(1000f, tween(250))
                    dailyTestViewModel.consumeAnswer(question, true)
                    currentIndex++
                    offsetX.snapTo(0f)
                }
            }

            val handleSwipeLeft = {
                scope.launch {
                    offsetX.animateTo(-1000f, tween(250))
                    dailyTestViewModel.consumeAnswer(question, false)
                    currentIndex++
                    offsetX.snapTo(0f)
                }
            }

            SwipeCard(
                centerText = "${questions.size - currentIndex}",
                centerImg = painterResource(R.drawable.magic_sparkles),
                text = question.text,
                onSwipeLeft = { handleSwipeLeft() },
                onSwipeRight = { handleSwipeRight() },
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                    .graphicsLayer(rotationZ = offsetX.value / 30)
                    .clip(RoundedCornerShape(24.dp))
                    .shadow(8.dp, RoundedCornerShape(24.dp))
                    .pointerInput(currentIndex) {
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { _, dragAmount ->
                                scope.launch { offsetX.snapTo(offsetX.value + dragAmount) }
                            },
                            onDragEnd = {
                                scope.launch {
                                    when {
                                        offsetX.value > 250f -> handleSwipeRight()
                                        offsetX.value < -250f -> handleSwipeLeft()
                                        else -> offsetX.animateTo(0f, tween(200))
                                    }
                                }
                            }
                        )
                    },
                textColor = AppColors.textPrimary
            )
        }
    }
}