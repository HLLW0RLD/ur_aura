package com.example.ur_color.ui.screen

import android.graphics.drawable.Animatable
import android.widget.Toast
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.ur_color.data.local.LocalDailyTestService
import com.example.ur_color.data.model.Question
import com.example.ur_color.ui.screen.viewModel.QuestionSwipeViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import androidx.compose.animation.core.*
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.ur_color.ui.theme.AppColors
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt

@Serializable
data object QuestionSwipe : Screen

@Composable
fun QuestionSwipeScreen(
    questionSwipeViewModel: QuestionSwipeViewModel = koinViewModel()
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

                QuestionSwipeCard(
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

            QuestionSwipeCard(
                text = question.text,
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
                                            offsetX.animateTo(1000f, tween(250))
                                            questionSwipeViewModel.consumeAnswer(context, question, true)
                                            currentIndex++
                                            offsetX.snapTo(0f)
                                        }
                                        offsetX.value < -250f -> {
                                            offsetX.animateTo(-1000f, tween(250))
                                            questionSwipeViewModel.consumeAnswer(context, question, false)
                                            currentIndex++
                                            offsetX.snapTo(0f)
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

@Composable
fun QuestionSwipeCard(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = AppColors.textPrimary,
    backgroundColor: Color = AppColors.surface,
    height: Dp = 620.dp,
    width: Dp = 370.dp,
    shadowElevation: Dp = 8.dp,
    fontSize: TextUnit = 20.sp,
    minLines: Int = 2,
    maxLines: Int = 2,
    fixedHeight: Dp = 60.dp,
    contentAlignment: Alignment = Alignment.Center
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(shadowElevation)
    ) {
        Box(
            modifier = Modifier
                .size(width = width, height = height)
                .fillMaxSize()
                .padding(3.dp)
                .border(
                    shape = RoundedCornerShape(24.dp),
                    width = 6.dp,
                    color = AppColors.background
                )
                .padding(24.dp),
            contentAlignment = contentAlignment
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = textColor,
                fontSize = fontSize,
                minLines = minLines,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.height(fixedHeight)
            )
        }
    }
}
