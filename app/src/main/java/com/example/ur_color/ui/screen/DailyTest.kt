package com.example.ur_color.ui.screen

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
import com.example.ur_color.data.local.LocalDailyTestService
import com.example.ur_color.ui.screen.viewModel.DailyTestViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.IntOffset
import com.example.ur_color.ui.SwipeCard
import com.example.ur_color.ui.theme.AppColors
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt

@Serializable
data object DailyTest : Screen

@Composable
fun DailyTestScreen(
    dailyTestViewModel: DailyTestViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val questions = remember { LocalDailyTestService().firstVarTest.toMutableStateList() }
    var currentIndex by remember { mutableStateOf(0) }
    val offsetX = remember { Animatable(0f) }

    if (questions.isEmpty()) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Все вопросы пройдены!")
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColors.background),
            contentAlignment = Alignment.Center
        ) {
            if (questions.isEmpty() || currentIndex >= questions.size) {
                Text("Все вопросы пройдены!", style = MaterialTheme.typography.titleLarge)
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
                    dailyTestViewModel.consumeAnswer(context, question, true)
                    currentIndex++
                    offsetX.snapTo(0f)
                }
            }

            val handleSwipeLeft = {
                scope.launch {
                    offsetX.animateTo(-1000f, tween(250))
                    dailyTestViewModel.consumeAnswer(context, question, false)
                    currentIndex++
                    offsetX.snapTo(0f)
                }
            }

            SwipeCard(
                text = question.text,
                onSwipeLeft = {
                    handleSwipeLeft()
                },
                onSwipeRight = {
                    handleSwipeRight()
                },
                modifier = Modifier
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
                                        offsetX.value > 250f -> {
//                                          right
                                            handleSwipeRight()
                                        }

                                        offsetX.value < -250f -> {
//                                          left
                                            handleSwipeLeft()
                                        }

                                        else -> {
                                            offsetX.animateTo(0f, tween(200))
                                        }
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