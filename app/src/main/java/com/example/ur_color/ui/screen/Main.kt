package com.example.ur_color.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.ur_color.data.local.mocServece.LocalDailyCardService
import com.example.ur_color.data.model.response.Card
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.utils.LocalNavController
import kotlinx.serialization.Serializable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.lerp
import com.example.ur_color.R
import com.example.ur_color.data.local.mocServece.LocalMotivationService
import com.example.ur_color.data.model.user.ZodiacSign
import com.example.ur_color.ui.AutoScrollHorizontalPager
import com.example.ur_color.ui.ExpandableGradientGraphBox
import com.example.ur_color.ui.FeedContentCard
import com.example.ur_color.ui.FloatingBox
import com.example.ur_color.ui.screen.viewModel.LabViewModel
import com.example.ur_color.ui.screen.viewModel.MainViewModel
import com.example.ur_color.ui.screen.viewModel.ProfileViewModel
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.AppScaffold
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt

@Serializable
object Main : Screen

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun Main(main : Main) {
    AppScaffold(
        showBottomBar = true,
        topBar = {
            val navController = LocalNavController.current

            val labViewModel: LabViewModel = koinViewModel()
            val isDailyTestAvailable by labViewModel.isDailyTestAvailable.collectAsState()

            LaunchedEffect(Unit) {
                labViewModel.checkDailyTestAvailability()
            }

            Box {
                CustomAppBar(
                    showDivider = true,
                    isCentered = true,
                    backgroundColor = AppColors.background.copy(alpha = 0.85f),
                )

                if (isDailyTestAvailable) {
                    Text(
                        text = stringResource(R.string.daily_test_new),
                        color = AppColors.white,
                        modifier = Modifier
                            .padding(12.dp)
                            .align(Alignment.BottomEnd)
                            .clip(RoundedCornerShape(50.dp))
                            .background(AppColors.accentPrimary)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .clickable {
                                navController.nav(Test("0"))
                            }
                    )
                }
            }
        },
    ) {
        MainScreen(
            modifier = Modifier
                .padding(it)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val navController = LocalNavController.current
    val scrollState = rememberScrollState()

    val dailyCardService = remember { LocalDailyCardService() }
    val localMotivationService = remember { LocalMotivationService() }

    val user = mainViewModel.user.collectAsState().value
    val aura by mainViewModel.aura.collectAsState()
    val zodiacSign = ZodiacSign.fromName(user?.zodiacSign ?: "") ?: ZodiacSign.GEMINI

    var motivated by remember { mutableStateOf<String?>(null) }
    var card by remember { mutableStateOf<Card?>(null) }

    val feedCardsState by mainViewModel.feedCardsState.collectAsState()

    LaunchedEffect(Unit) {
        mainViewModel.loadDailyHoroscope(sign = zodiacSign.value)
        val result = dailyCardService.generateDailyCard(userName = user?.firstName ?: "")
        result.onSuccess { card = it }
        motivated = localMotivationService.getPhraseForToday()
    }

    val vectorMetrics = listOf(
        user?.characteristics?.energyVector to stringResource(R.string.metric_energy),
        user?.characteristics?.physicalEnergyVector to stringResource(R.string.metric_physical_energy),
        user?.characteristics?.sleepQualityVector to stringResource(R.string.metric_sleep_quality),

        user?.characteristics?.moodVector to stringResource(R.string.metric_mood),
        user?.characteristics?.motivationVector to stringResource(R.string.metric_motivation),
        user?.characteristics?.focusVector to stringResource(R.string.metric_focus),

        user?.characteristics?.charismaVector to stringResource(R.string.metric_charisma),
        user?.characteristics?.socialVector to stringResource(R.string.metric_social),
        user?.characteristics?.communicationVector to stringResource(R.string.metric_communication),

        user?.characteristics?.stressVector to stringResource(R.string.metric_stress),
        user?.characteristics?.anxietyVector to stringResource(R.string.metric_anxiety),
        user?.characteristics?.fatigueVector to stringResource(R.string.metric_fatigue),
    )

    val metrics = listOf(
        user?.characteristics?.energy,
        user?.characteristics?.physicalEnergy,
        user?.characteristics?.sleepQuality,

        user?.characteristics?.mood,
        user?.characteristics?.motivation,
        user?.characteristics?.focus,

        user?.characteristics?.charisma,
        user?.characteristics?.socialEnergy,
        user?.characteristics?.communication,

        user?.characteristics?.stress,
        user?.characteristics?.anxiety,
        user?.characteristics?.fatigue,
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.background)
    ) {
//        aura?.let {
//            Box(
//                modifier = Modifier
//                    .align(Alignment.Center)
//                    .fillMaxWidth()
//                    .height(1750.dp)
//                    .padding(16.dp),
//                contentAlignment = Alignment.Center
//            ) {
//                Image(
//                    bitmap = it.asImageBitmap(),
//                    contentDescription = "",
//                    contentScale = ContentScale.Fit,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .align(Alignment.Center)
//                        .clip(RoundedCornerShape(24.dp))
//                        .border(
//                            shape = RoundedCornerShape(24.dp),
//                            color = AppColors.divider,
//                            width = 0.5.dp
//                        )
//                        .clickable(
//                            indication = null,
//                            interactionSource = null
//                        ) {
//                            navController.nav(AuraDetails())
//                        }
//                )
//            }
//        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = AppColors.background.copy(alpha = 0.95f)
                )
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                color = AppColors.textSecondary,
                text = motivated ?: stringResource(R.string.motivation_fallback),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.size(8.dp))

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                thickness = 0.5.dp,
                color = AppColors.textPrimary
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp))
                    .background(
                        AppColors.backgroundDark
                            .copy(alpha = 0.2f)
                    )
                    .padding(top = 16.dp, bottom = 8.dp)
            ) {
                LazyRow(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .heightIn(max = 130.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    item { Spacer(modifier = Modifier.width(0.dp)) }

                    items(
                        count = vectorMetrics.size,
                        key = { index -> "metric_$index" }
                    ) { vector ->
                        val metric = vectorMetrics[vector]
                        val value = metric.first ?: listOf()
                        val label = metric.second

                        var exp by rememberSaveable { mutableStateOf(false) }

                        ExpandableGradientGraphBox(
                            label = label,
                            indicator = metrics[vector] ?: 0f,
                            values = value,
                            vector = vector,
                            expanded = exp,
                            onToggleExpanded = { exp = !exp },
                        )
                    }

                    item { Spacer(modifier = Modifier.width(8.dp)) }
                }

                Spacer(modifier = Modifier.size(16.dp))

                Box {
                    var autoScroll by rememberSaveable { mutableStateOf(true) }
                    AutoScrollHorizontalPager(
                        autoScroll = autoScroll,
                        isInfinite = true,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        item {
                            FloatingBox(
                                modifier = Modifier
                                    .padding(6.dp),
                                height = 200f,
                                width = 1f,
                                closedTitle = stringResource(
                                    R.string.card_today_title,
                                    user?.firstName ?: "",
                                    card?.name
                                        ?: stringResource(R.string.error_oops)
                                ),
                            )
                        }
                        item {
                            FloatingBox(
                                modifier = Modifier
                                    .padding(6.dp),
                                height = 200f,
                                width = 1f,
                                closedTitle = stringResource(
                                    R.string.horoscope_for_user,
                                    user?.firstName ?: ""
                                ),
                            ) {
                                // навигация на экран горосккопа дня
                            }
                        }
                        item {
                            FloatingBox(
                                modifier = Modifier
                                    .padding(6.dp),
                                height = 200f,
                                width = 1f,
                                closedTitle = "Аура! как понять?",
                            ) {
                                // навигация на экран мастер классов
                            }
                        }
                        item {
                            FloatingBox(
                                modifier = Modifier
                                    .padding(6.dp),
                                height = 200f,
                                width = 1f,
                                closedTitle = "Проверим кто вам подходит?",
                            ) {
                                // навигация на экран мастер классов
                            }
                        }

                        // дублируюттся
                        item {
                            FloatingBox(
                                modifier = Modifier
                                    .padding(6.dp),
                                height = 200f,
                                width = 1f,
                                closedTitle = stringResource(
                                    R.string.card_today_title,
                                    user?.firstName ?: "",
                                    card?.name
                                        ?: stringResource(R.string.error_oops)
                                ),
                            ) {
                                // навигация на экран карты дня
                            }
                        }
                        item {
                            FloatingBox(
                                modifier = Modifier
                                    .padding(6.dp),
                                height = 200f,
                                width = 1f,
                                closedTitle = stringResource(
                                    R.string.horoscope_for_user,
                                    user?.firstName ?: ""
                                ),
                            ) {
                                // навигация на экран горосккопа дня
                            }
//
                        }
                        item {
                            FloatingBox(
                                modifier = Modifier
                                    .padding(6.dp),
                                height = 200f,
                                width = 1f,
                                closedTitle = "Аура! как понять?",
                            ) {
                                // навигация на экран мастер классов
                            }
                        }
                        item {
                            FloatingBox(
                                modifier = Modifier
                                    .padding(6.dp),
                                height = 200f,
                                width = 1f,
                                closedTitle = "Проверим кто вам подходит?",
                            ) {
                                // навигация на экран мастер классов
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp))
                    .background(
                        AppColors.backgroundDark
                            .copy(alpha = 0.2f)
                    )
                    .padding(vertical = 16.dp)
            ) {
                feedCardsState.forEach {
                    FeedContentCard(
                        modifier = Modifier
//                                .heightIn(max = 400.dp)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        content = it,
                        onClick = { }
                    )
                }
            }

            Spacer(Modifier.height(120.dp))
        }

//        val cornerDp = lerp(24.dp, 12.dp, progress)
//        val borderAlpha = 1f - progress
//        Surface(
//            modifier = Modifier
//                .offset { IntOffset(0, offsetY.value.roundToInt()) }
//                .fillMaxSize()
//                .border(
//                    width = 0.5.dp,
//                    color = AppColors.divider.copy(alpha = borderAlpha),
//                    shape = RoundedCornerShape(topStart = cornerDp, topEnd = cornerDp)
//                )
//                .pointerInput(canScroll) {
//                    detectVerticalDragGestures(
//                        onVerticalDrag = { change, dragAmount ->
//                            change.consume()
//                            scope.launch {
//                                val newOffset =
//                                    (offsetY.value + dragAmount).coerceIn(expandedY, collapsedY)
//                                if (!canScroll || dragAmount > 0) {
//                                    offsetY.snapTo(newOffset)
//                                }
//                            }
//                        },
//                        onDragEnd = {
//                            val middle = (collapsedY + expandedY) / 2f
//                            if (offsetY.value <= middle) animateToExpanded()
//                            else animateToCollapsed()
//                        }
//                    )
//                },
//            shape = RoundedCornerShape(
//                topStart = (28.dp * (1 - progress)).coerceAtLeast(0.dp),
//                topEnd = (28.dp * (1 - progress)).coerceAtLeast(0.dp)
//            ),
//            color = AppColors.background.copy(alpha = 0.95f * progress),
//            tonalElevation = 8.dp
//        ) {
//
//
//        }

//        AnimatedVisibility(
//            visible = progress >= 0.95f,
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//        ) {
//            Box(
//                modifier = Modifier
//                    .align(Alignment.BottomEnd)
//                    .fillMaxWidth()
//                    .padding(end = 16.dp, bottom = 100.dp),
//                contentAlignment = Alignment.BottomEnd
//            ) {
//                FloatingActionButton(
//                    onClick = {
//                        scope.launch { offsetY.animateTo(collapsedY, tween(400)) }
//                    },
//                    containerColor = AppColors.accentPrimary.copy(alpha = 0.5f),
//                    contentColor = Color.White
//                ) {
//                    Icon(
//                        painter = painterResource(R.drawable.arrow_up),
//                        contentDescription = "Добавить"
//                    )
//                }
//            }
//        }
    }
}