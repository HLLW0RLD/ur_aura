package com.example.ur_color.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ur_color.data.local.LocalDailyCardService
import com.example.ur_color.data.local.PrefCache
import com.example.ur_color.data.model.Card
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.utils.LocalNavController
import kotlinx.serialization.Serializable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import com.example.ur_color.data.user.ZodiacSign
import com.example.ur_color.ui.ExpandableFloatingBox
import com.example.ur_color.ui.WindowType
import com.example.ur_color.ui.screen.viewModel.HoroscopeUiState
import com.example.ur_color.ui.screen.viewModel.MainViewModel
import com.example.ur_color.ui.screen.viewModel.ProfileViewModel
import org.koin.androidx.compose.koinViewModel

@Serializable
object Main : Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = koinViewModel(),
    profileViewModel: ProfileViewModel = koinViewModel()
) {

    val navController = LocalNavController.current

    val user = PrefCache.user.collectAsState().value
    val zodiacSign = ZodiacSign.fromName(user!!.zodiacSign) ?: ZodiacSign.GEMINI
    val dailyCardService = remember { LocalDailyCardService() }

    LaunchedEffect(Unit) {
        mainViewModel.loadDailyHoroscope(sign = zodiacSign.value)
    }

    var card by remember { mutableStateOf<Card?>(null) }
    LaunchedEffect(Unit) {
        val result = dailyCardService.generateDailyCard(userName =  user?.firstName ?: "")
        result.onSuccess { card = it }
    }

    val horoscopeState by mainViewModel.horoscopeState.collectAsState()
    val aura by profileViewModel.aura.collectAsState()

    var widthCard = remember { 0.5f }
    var widthHoroscope = remember { 1f }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
    ) {
        Column {
            CustomAppBar(
//                modifier = Modifier.align(Alignment.TopCenter),
                title = "a u r a",
                showOptions = true,
                onOptionsClick = {},
                backgroundColor = Color.White,
                showBack = true,
                isCentered = true,
                onBackClick = {},
            )

            aura?.let {
                Box(
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = null
                        ) {
                            navController.navigate(AuraDetails().route())
                        }
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(16.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Аура фон",
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        modifier = Modifier
                            .padding(20.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.White)
                            .padding(8.dp)
                            .align(Alignment.BottomStart),
                        text = "${user.firstName}, ваша аура",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.Top
            ) {

                ExpandableFloatingBox(
                    width = widthCard,
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
                                text = card?.name ?: "oops",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(text = "Стихия: ${card?.element}, Номер: ${card?.number}")
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = card?.fullMeaning ?: "oops",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "Совет: ${card?.advice}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(text = "Ключевые слова: ${card?.keywords?.joinToString(", ")}")
                            Spacer(Modifier.height(8.dp))
                            Text(text = "Совместимые карты: ${card?.compatibleWith?.joinToString(", ")}")
                        }
                    }
                )

                when (val uiState = horoscopeState) {
                    is HoroscopeUiState.Success -> {
                        val horoscope = uiState.horoscope
                        ExpandableFloatingBox(
                            width = widthHoroscope,
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
                                    horoscope.horoscope,
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
                            Text(uiState.message)
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Главная",
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .clickable {
                        navController.navigate(Main.route())
                    }
            )
            Text(
                text = "Профиль",
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .clickable {
                        navController.navigate(Profile().route())
                    }
            )
        }
    }
}