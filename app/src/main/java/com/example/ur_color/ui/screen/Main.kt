package com.example.ur_color.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
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
import com.example.ur_color.data.model.Card
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.utils.LocalNavController
import kotlinx.serialization.Serializable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.lerp
import com.example.ur_color.R
import com.example.ur_color.data.model.SocialContent
import com.example.ur_color.data.user.ZodiacSign
import com.example.ur_color.ui.ExpandableFloatingBox
import com.example.ur_color.ui.ExpandableBox
import com.example.ur_color.ui.DynamicDoubleColumn
import com.example.ur_color.ui.GradientGraphBox
import com.example.ur_color.ui.MarketplaceContentCard
import com.example.ur_color.ui.WindowType
import com.example.ur_color.ui.screen.viewModel.HoroscopeUiState
import com.example.ur_color.ui.screen.viewModel.MainViewModel
import com.example.ur_color.ui.screen.viewModel.ProfileViewModel
import com.example.ur_color.ui.theme.AppColors
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

    val dailyCardService = remember { LocalDailyCardService() }

    val user = profileViewModel.user.collectAsState().value
    val aura by profileViewModel.aura.collectAsState()
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
    val expandedY = statusBarHeight + 170f                                  // полностью раскрытая подложка

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
            .background(AppColors.background)
    ) {
//        aura?.let {
//            Image(
//                bitmap = it.asImageBitmap(),
//                contentDescription = "",
//                modifier = Modifier
//                    .fillMaxSize()
//                    .graphicsLayer {
//                        translationY = -auraShift
//                    }
//                    .blur(80.dp),
//                contentScale = ContentScale.Crop
//            )
//        }

        CustomAppBar(
            title = "_a u r a_",
            showOptions = true,
            showDivider = true,
            optionsIcon = if (progress >= 0.95f) {
                painterResource(R.drawable.arrow_left)
            } else {
                painterResource(R.drawable.switcher_options)
            },
            onOptionsClick = {
                if (offsetY.value != collapsedY) {
                    scope.launch { offsetY.animateTo(collapsedY, tween(400)) }
                } else {
                    navController.nav(Profile().route())
                }
            },
            isCentered = true,
//            backgroundColor = AppColors.background,
            backgroundColor = AppColors.background.copy(alpha = 0.85f * progress),
        )

        aura?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "",
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.Center)
                    .graphicsLayer {
                        translationY = -auraShiftDp.toPx()
                    }
                    .clip(CircleShape)
                    .clickable(
                        indication = null,
                        interactionSource = null
                    ) {
                        navController.nav(AuraDetails().route())
                    }
            )
        }

        val cornerDp = lerp(24.dp, 12.dp, progress)
        val borderAlpha = 1f - progress
        Surface(
            modifier = Modifier
                .offset { IntOffset(0, offsetY.value.roundToInt()) }
                .fillMaxSize()
                .border(
                    width = 2.dp,
                    color = AppColors.surfaceLight.copy(alpha = borderAlpha),
                    shape = RoundedCornerShape(topStart = cornerDp, topEnd = cornerDp)
                )
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
            color = AppColors.background.copy(alpha = 0.95f * progress),
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .then(if (canScroll) Modifier.verticalScroll(scrollState) else Modifier)
            ) {
                Text(
                    color = AppColors.textSecondary,
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
                    color = AppColors.textPrimary
                )

                Spacer(modifier = Modifier.size(24.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(AppColors.surfaceDark
                            .copy(alpha = 0.2f)
                        )
                        .padding(vertical = 16.dp)
                ) {
                    LazyRow(
                        modifier = Modifier
                            .height(130.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        item { Spacer(modifier = Modifier.width(0.dp)) }
                        item {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(3.dp)
                                    .border(
                                        shape = RoundedCornerShape(24.dp),
                                        width = 2.dp,
                                        color = AppColors.surface
                                    )
                                    .padding(16.dp)
                            ) {
                                GradientGraphBox(
                                    values = user.energyCapacity,
                                    showStat = false,
                                    modifier = Modifier
                                        .width(150.dp)
                                        .height(60.dp)
                                )
                                Text("Energy Level", color = AppColors.textPrimary)
                            }
                        }
                        item {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(3.dp)
                                    .border(
                                        shape = RoundedCornerShape(24.dp),
                                        width = 2.dp,
                                        color = AppColors.surface
                                    )
                                    .padding(16.dp)
                            ) {
                                GradientGraphBox(
                                    values = user.moodVector,
                                    showStat = false,
                                    modifier = Modifier
                                        .width(150.dp)
                                        .height(60.dp)
                                )
                                Text("Mood", color = AppColors.textPrimary)
                            }
                        }
                        item {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(3.dp)
                                    .border(
                                        shape = RoundedCornerShape(24.dp),
                                        width = 2.dp,
                                        color = AppColors.surface
                                    )
                                    .padding(16.dp)
                            ) {
                                GradientGraphBox(
                                    values = user.stressVector,
                                    showStat = false,
                                    modifier = Modifier
                                        .width(150.dp)
                                        .height(60.dp)
                                )
                                Text("Stress Level", color = AppColors.textPrimary)
                            }
                        }
                        item {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(3.dp)
                                    .border(
                                        shape = RoundedCornerShape(24.dp),
                                        width = 2.dp,
                                        color = AppColors.surface
                                    )
                                    .padding(16.dp)
                            ) {
                                GradientGraphBox(
                                    values = user.motivationVector,
                                    showStat = false,
                                    modifier = Modifier
                                        .width(150.dp)
                                        .height(60.dp)
                                )
                                Text("Motivation", color = AppColors.textPrimary)
                            }
                        }
                        item {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(3.dp)
                                    .border(
                                        shape = RoundedCornerShape(24.dp),
                                        width = 2.dp,
                                        color = AppColors.surface
                                    )
                                    .padding(16.dp)
                            ) {
                                GradientGraphBox(
                                    values = user.creativityVector,
                                    showStat = false,
                                    modifier = Modifier
                                        .width(150.dp)
                                        .height(60.dp)
                                )
                                Text("Creativity", color = AppColors.textPrimary)
                            }
                        }
                        item {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(3.dp)
                                    .border(
                                        shape = RoundedCornerShape(24.dp),
                                        width = 2.dp,
                                        color = AppColors.surface
                                    )
                                    .padding(16.dp)
                            ) {
                                GradientGraphBox(
                                    values = user.emotionalBalanceVector,
                                    showStat = false,
                                    modifier = Modifier
                                        .width(150.dp)
                                        .height(60.dp)
                                )
                                Text("Emotional Balance", color = AppColors.textPrimary)
                            }
                        }
                        item {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(3.dp)
                                    .border(
                                        shape = RoundedCornerShape(24.dp),
                                        width = 2.dp,
                                        color = AppColors.surface
                                    )
                                    .padding(16.dp)
                            ) {
                                GradientGraphBox(
                                    values = user.physicalEnergyVector,
                                    showStat = false,
                                    modifier = Modifier
                                        .width(150.dp)
                                        .height(60.dp)
                                )
                                Text("Physical Energy", color = AppColors.textPrimary)
                            }
                        }
                        item {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(3.dp)
                                    .border(
                                        shape = RoundedCornerShape(24.dp),
                                        width = 2.dp,
                                        color = AppColors.surface
                                    )
                                    .padding(16.dp)
                            ) {
                                GradientGraphBox(
                                    values = user.sleepQualityVector,
                                    showStat = false,
                                    modifier = Modifier
                                        .width(150.dp)
                                        .height(60.dp)
                                )
                                Text("Sleep Quality", color = AppColors.textPrimary)
                            }
                        }
                        item {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(3.dp)
                                    .border(
                                        shape = RoundedCornerShape(24.dp),
                                        width = 2.dp,
                                        color = AppColors.surface
                                    )
                                    .padding(16.dp)
                            ) {
                                GradientGraphBox(
                                    values = user.intuitionVector,
                                    showStat = false,
                                    modifier = Modifier
                                        .width(150.dp)
                                        .height(60.dp)
                                )
                                Text("Intuition Level", color = AppColors.textPrimary)
                            }
                        }
                        item {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .padding(3.dp)
                                    .border(
                                        shape = RoundedCornerShape(24.dp),
                                        width = 2.dp,
                                        color = AppColors.surface
                                    )
                                    .padding(16.dp)
                            ) {
                                GradientGraphBox(
                                    values = user.socialVector,
                                    showStat = false,
                                    modifier = Modifier
                                        .width(150.dp)
                                        .height(60.dp)
                                )
                                Text("Social Energy", color = AppColors.textPrimary)
                            }
                        }
                        item { Spacer(modifier = Modifier.width(8.dp)) }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                    ) {
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
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text(
                                        color = AppColors.textPrimary,
                                        text = card?.name ?: "oops",
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Text(
                                        color = AppColors.textPrimary,
                                        text = "Стихия: ${card?.element}, Номер: ${card?.number}"
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Text(
                                        color = AppColors.textPrimary,
                                        text = card?.fullMeaning ?: "oops",
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Text(
                                        color = AppColors.textPrimary,
                                        text = "Совет: ${card?.advice}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Text(
                                        color = AppColors.textPrimary,
                                        text = "Ключевые слова: ${card?.keywords?.joinToString(", ")}"
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Text(
                                        color = AppColors.textPrimary,
                                        text = "Совместимые карты: ${
                                            card?.compatibleWith?.joinToString(
                                                ", "
                                            )
                                        }"
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
                                    ) {
                                        Text(
                                            color = AppColors.textPrimary,
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
                                        color = AppColors.textPrimary,
                                        text = uiState.message
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                DynamicDoubleColumn(
                    paddingHorizontal = 16.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(AppColors.surfaceDark
                            .copy(alpha = 0.2f)
                        )
                        .padding(vertical = 16.dp)
                ) {

                    demoCards.forEach {
                        item {
                            MarketplaceContentCard(
                                modifier = Modifier
                                    .padding(4.dp),
                                content = it,
                                onClick = {  }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(120.dp))
            }
        }
    }
}

val demoCards = listOf(
    SocialContent.Product(
        id = "1",
        title = "Apple AirPods Pro",
        price = "19 990 ₽",
        image = "https://picsum.photos/seed/airpods/600/600"
    ),
    SocialContent.Product(
        id = "2",
        title = "Xiaomi Robot Vacuum",
        price = "23 499 ₽",
        image = "https://picsum.photos/seed/vacuum/600/600"
    ),
    SocialContent.Product(
        id = "3",
        title = "Sony WH-1000XM5",
        price = "34 990 ₽",
        image = "https://picsum.photos/seed/headphones/600/600"
    ),
    SocialContent.Ad(
        id = "4",
        title = "🔥 Скидки до 70% на технику!",
        image = "https://picsum.photos/seed/sale/600/600",
        cta = "Открыть"
    ),
    SocialContent.Product(
        id = "5",
        title = "Nike Air Max 270",
        price = "12 499 ₽",
        image = "https://picsum.photos/seed/nike/600/600"
    ),
    SocialContent.User(
        id = "6",
        username = "Мария",
        avatar = "https://picsum.photos/seed/user1/300/300"
    ),
    SocialContent.Product(
        id = "7",
        title = "Logitech MX Master 3S",
        price = "8 490 ₽",
        image = "https://picsum.photos/seed/mouse/600/600"
    ),
    SocialContent.Product(
        id = "8",
        title = "Sony PlayStation 5",
        price = "69 999 ₽",
        image = "https://picsum.photos/seed/ps5/600/600"
    ),
    SocialContent.Ad(
        id = "9",
        title = "🎁 Бесплатная доставка от 999 ₽",
        image = "https://picsum.photos/seed/delivery/600/600",
        cta = "Подробнее"
    ),
    SocialContent.User(
        id = "10",
        username = "Алексей",
        avatar = "https://picsum.photos/seed/user2/300/300"
    ),
    SocialContent.Product(
        id = "11",
        title = "Canon EOS R50 Kit",
        price = "89 900 ₽",
        image = "https://picsum.photos/seed/camera/600/600"
    ),
    SocialContent.Product(
        id = "12",
        title = "MacBook Air M3",
        price = "124 990 ₽",
        image = "https://picsum.photos/seed/macbook/600/600"
    ),
    SocialContent.Product(
        id = "1",
        title = "Apple AirPods Pro",
        price = "19 990 ₽",
        image = "https://picsum.photos/seed/airpods/600/600"
    ),
    SocialContent.Product(
        id = "2",
        title = "Xiaomi Robot Vacuum",
        price = "23 499 ₽",
        image = "https://picsum.photos/seed/vacuum/600/600"
    ),
    SocialContent.Product(
        id = "3",
        title = "Sony WH-1000XM5",
        price = "34 990 ₽",
        image = "https://picsum.photos/seed/headphones/600/600"
    ),
    SocialContent.Ad(
        id = "4",
        title = "🔥 Скидки до 70% на технику!",
        image = "https://picsum.photos/seed/sale/600/600",
        cta = "Открыть"
    ),
    SocialContent.Product(
        id = "5",
        title = "Nike Air Max 270",
        price = "12 499 ₽",
        image = "https://picsum.photos/seed/nike/600/600"
    ),
    SocialContent.User(
        id = "6",
        username = "Мария",
        avatar = "https://picsum.photos/seed/user1/300/300"
    ),
    SocialContent.Product(
        id = "7",
        title = "Logitech MX Master 3S",
        price = "8 490 ₽",
        image = "https://picsum.photos/seed/mouse/600/600"
    ),
    SocialContent.Product(
        id = "8",
        title = "Sony PlayStation 5",
        price = "69 999 ₽",
        image = "https://picsum.photos/seed/ps5/600/600"
    ),
    SocialContent.Ad(
        id = "9",
        title = "🎁 Бесплатная доставка от 999 ₽",
        image = "https://picsum.photos/seed/delivery/600/600",
        cta = "Подробнее"
    ),
    SocialContent.User(
        id = "10",
        username = "Алексей",
        avatar = "https://picsum.photos/seed/user2/300/300"
    ),
    SocialContent.Product(
        id = "11",
        title = "Canon EOS R50 Kit",
        price = "89 900 ₽",
        image = "https://picsum.photos/seed/camera/600/600"
    ),
    SocialContent.Product(
        id = "12",
        title = "MacBook Air M3",
        price = "124 990 ₽",
        image = "https://picsum.photos/seed/macbook/600/600"
    )
)