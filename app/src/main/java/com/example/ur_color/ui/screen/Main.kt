package com.example.ur_color.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.ur_color.data.local.LocalDailyCardService
import com.example.ur_color.data.local.PrefCache
import com.example.ur_color.data.model.Card
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.utils.LocalNavController
import kotlinx.serialization.Serializable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import com.example.ur_color.R
import com.example.ur_color.data.user.ZodiacSign
import com.example.ur_color.ui.ExpandableFloatingBox
import com.example.ur_color.ui.WindowType
import com.example.ur_color.ui.screen.viewModel.HoroscopeUiState
import com.example.ur_color.ui.screen.viewModel.MainViewModel
import com.example.ur_color.ui.screen.viewModel.ProfileViewModel
import com.example.ur_color.ui.theme.AuraColors
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt

@Serializable
object Main : Screen

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = koinViewModel(),
    profileViewModel: ProfileViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val navController = LocalNavController.current
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val user = PrefCache.user.collectAsState().value
    val aura by profileViewModel.aura.collectAsState()
    val dailyCardService = remember { LocalDailyCardService() }
    val zodiacSign = ZodiacSign.fromName(user!!.zodiacSign) ?: ZodiacSign.GEMINI

    var card by remember { mutableStateOf<Card?>(null) }
    val horoscopeState by mainViewModel.horoscopeState.collectAsState()

    LaunchedEffect(Unit) {
        mainViewModel.loadDailyHoroscope(sign = zodiacSign.value)
        val result = dailyCardService.generateDailyCard(userName =  user.firstName)
        result.onSuccess { card = it }
    }

    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val statusBarHeight = WindowInsets.statusBars.getTop(density).toFloat()
    val collapsedHeight = screenHeight / 1.8f
    val collapsedY = with(density) { collapsedHeight.toPx() }               // свернутая подложка
    val expandedY = statusBarHeight + 186f                                  // полностью раскрытая подложка

    val offsetY = remember { Animatable(collapsedY) }

    val progress = ((collapsedY - offsetY.value) / (collapsedY - expandedY)).coerceIn(0f, 1f)
    val canScroll = progress >= 0.999f

    val auraShift = with(density) { (screenHeight * 0.2f).toPx() }
    val auraShiftDp = screenHeight * 0.2f

    fun animateToExpanded() = scope.launch { offsetY.animateTo(expandedY, tween(400)) }
    fun animateToCollapsed() = scope.launch { offsetY.animateTo(collapsedY, tween(400)) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AuraColors.background)
    ) {
        aura?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        translationY = -auraShift
                    }
                    .blur(80.dp),
                contentScale = ContentScale.Crop
            )
        }

        CustomAppBar(
            title = "_a u r a_",
            showOptions = true,
            optionsIcon = if (progress >= 0.95f) {
                painterResource(R.drawable.arrow_left)
            } else {
                painterResource(R.drawable.switcher_options)
            },
            onOptionsClick = {
                if (offsetY.value != collapsedY) {
                    scope.launch { offsetY.animateTo(collapsedY, tween(400)) }
                } else {
                    navController.navigate(Profile().route())
                }
            },
            isCentered = true,
            backgroundColor = AuraColors.background,
//            backgroundColor = AuraColors.background.copy(alpha = 0.85f * progress),
            modifier = Modifier.statusBarsPadding()
        )

        aura?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "",
                modifier = Modifier
                    .size(250.dp)
                    .align(Alignment.Center)
                    .graphicsLayer {
                        translationY = -auraShiftDp.toPx()
                    }
                    .clip(CircleShape)
                    .clickable(
                        indication = null,
                        interactionSource = null
                    ) {
                        navController.navigate(AuraDetails().route())
                    }
            )
        }

        Surface(
            modifier = Modifier
                .offset { IntOffset(0, offsetY.value.roundToInt()) }
                .fillMaxSize()
                .pointerInput(canScroll) {
                    detectVerticalDragGestures(
                        onVerticalDrag = { change, dragAmount ->
                            change.consume()
                            scope.launch {
                                val newOffset =
                                    (offsetY.value + dragAmount).coerceIn(expandedY, collapsedY)
                                if (!canScroll || dragAmount > 0) {
                                    offsetY.snapTo(newOffset)
                                }
                            }
                        },
                        onDragEnd = {
                            val middle = (collapsedY + expandedY) / 2f
                            if (offsetY.value <= middle) animateToExpanded()
                            else animateToCollapsed()
                        }
                    )
                },
            shape = RoundedCornerShape(
                topStart = (28.dp * (1 - progress)).coerceAtLeast(0.dp),
                topEnd = (28.dp * (1 - progress)).coerceAtLeast(0.dp)
            ),
            color = AuraColors.background.copy(alpha = 0.95f * progress),
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .then(if (canScroll) Modifier.verticalScroll(scrollState) else Modifier)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Column {
                    Text(
                        color = AuraColors.textSecondary,
                        text = "Какой-то текст на главном экране\n" +
                                "Возможно воодушевляющая фраза\n" +
                                "или совет дня",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.size(16.dp))

                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        thickness = 0.5.dp,
                        color = AuraColors.textPrimary
                    )

                    Spacer(modifier = Modifier.size(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        // --- ВСЕ ТВОИ ExpandableFloatingBox остаются как есть ---
                        ExpandableFloatingBox(
                            width = 0.5f,
                            height = 100f,
                            expandWidth = 0.5f,
                            closedTitle = ("Ваша карта дня\n" + card?.name + "!"),
                            expandedTitle = card?.advice ?: "oops",
                            windowType = WindowType.Regular,
                            canShowFull = true,
                            content = {
                                Column(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        color = AuraColors.textPrimary,
                                        text = card?.name ?: "oops",
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Text(
                                        color = AuraColors.textPrimary,
                                        text = "Стихия: ${card?.element}, Номер: ${card?.number}"
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Text(
                                        color = AuraColors.textPrimary,
                                        text = card?.fullMeaning ?: "oops",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Text(
                                        color = AuraColors.textPrimary,
                                        text = "Совет: ${card?.advice}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Text(
                                        color = AuraColors.textPrimary,
                                        text = "Ключевые слова: ${card?.keywords?.joinToString(", ")}"
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Text(
                                        color = AuraColors.textPrimary,
                                        text = "Совместимые карты: ${card?.compatibleWith?.joinToString(", ")}"
                                    )
                                }
                            }
                        )

                        when (val uiState = horoscopeState) {
                            is HoroscopeUiState.Success -> {
                                val horoscope = uiState.horoscope
                                ExpandableFloatingBox(
                                    width = 1f,
                                    height = 100f,
                                    closedTitle = "Ваш гороскоп\nна ${horoscope.day}",
                                    expandedTitle = "Для ${user.zodiacSign}",
                                    windowType = WindowType.Regular,
                                    canShowFull = true,
                                ) {
                                    Column(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp)
                                    ) {
                                        Text(
                                            color = AuraColors.textPrimary,
                                            text = horoscope.horoscope,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }
                                }
                            }

                            is HoroscopeUiState.Loading -> {
                                ExpandableFloatingBox(
                                    closedTitle = "Загружается гороскоп...",
                                    expandedTitle = "Пожалуйста, подождите",
                                    expandHeight = 100f,
                                    canShowFull = false
                                ) {
                                    CircularProgressIndicator()
                                }
                            }

                            is HoroscopeUiState.Error -> {
                                ExpandableFloatingBox(
                                    closedTitle = "Ошибка загрузки",
                                    expandedTitle = "Попробуйте позже",
                                    windowType = WindowType.Slim,
                                    canShowFull = false
                                ) {
                                    Text(
                                        color = AuraColors.textPrimary,
                                        text = uiState.message
                                    )
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(32.dp))
                }
//
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp),
//                    horizontalArrangement = Arrangement.SpaceEvenly
//                ) {
//                    Text(
//                        text = "Главная",
//                        color = AuraColors.textPrimary,
//                        modifier = Modifier
//                            .padding(vertical = 16.dp)
//                            .clickable { navController.navigate(Main.route()) }
//                    )
//                    Text(
//                        text = "Профиль",
//                        color = AuraColors.textPrimary,
//                        modifier = Modifier
//                            .padding(vertical = 16.dp)
//                            .clickable { navController.navigate(Profile().route()) }
//                    )
//                }

                repeat(30) { i ->
                    Text(
                        color = AuraColors.textPrimary,
                        text = "да-да, этот ${i}й элемент просто так",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}