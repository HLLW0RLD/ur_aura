package com.example.ur_color.ui.screen

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
import com.example.ur_color.ui.ExpandableFloatingBox
import com.example.ur_color.ui.WindowType

@Serializable
object Main : Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = LocalNavController.current
    val scope = rememberCoroutineScope()
    val dailyCardService = remember { LocalDailyCardService() }

    var card by remember { mutableStateOf<Card?>(null) }

    val user = PrefCache.user.collectAsState().value

    LaunchedEffect(Unit) {
        val result = dailyCardService.generateDailyCard(userName =  user?.firstName ?: "")
        result.onSuccess { card = it }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        CustomAppBar(
            modifier = Modifier.align(Alignment.TopCenter),
            title = "a u r a",
            showOptions = true,
            onOptionsClick = { /* TODO */ },
            backgroundColor = Color.White
        )

        Column(
            modifier = Modifier
                .padding(top = 56.dp, bottom = 64.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ExpandableFloatingBox(
                closedTitle = ("ваша карта дня " + card?.name + "!"),
                expandedTitle = card?.advice ?: "oops",
                windowType = WindowType.Full,
                content = {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(card?.name ?: "oops", style = MaterialTheme.typography.titleLarge)
                        Spacer(Modifier.height(8.dp))
                        Text("Стихия: ${card?.element}, Номер: ${card?.number}")
                        Spacer(Modifier.height(8.dp))
                        Text(
                            card?.fullMeaning ?: "oops",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(Modifier.height(8.dp))
                        Text("Совет: ${card?.advice}", style = MaterialTheme.typography.bodyMedium)
                        Spacer(Modifier.height(8.dp))
                        Text("Ключевые слова: ${card?.keywords?.joinToString(", ")}")
                        Spacer(Modifier.height(8.dp))
                        Text("Совместимые карты: ${card?.compatibleWith?.joinToString(", ")}")
                        Spacer(Modifier.height(16.dp))
                    }
                }
            )
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

@Composable
fun DailyCardBottomSheet(card: Card, onClose: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(card.name, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text("Стихия: ${card.element}, Номер: ${card.number}")
        Spacer(Modifier.height(8.dp))
        Text(card.fullMeaning, style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(8.dp))
        Text("Совет: ${card.advice}", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(8.dp))
        Text("Ключевые слова: ${card.keywords.joinToString(", ")}")
        Spacer(Modifier.height(8.dp))
        Text("Совместимые карты: ${card.compatibleWith.joinToString(", ")}")
        Spacer(Modifier.height(16.dp))
        Button(onClick = onClose) {
            Text("Закрыть")
        }
    }
}