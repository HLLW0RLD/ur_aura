package com.example.ur_color.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import com.example.ur_color.ui.screen.viewModel.AuraDetailsViewModel
import com.example.ur_color.ui.theme.AuraColors
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random

@Serializable
data class AuraDetails(val color: String? = null) : Screen

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun AuraDetailsScreen(
    cd: AuraDetails,
    auraDetailsViewModel: AuraDetailsViewModel = koinViewModel()
) {
    val context = LocalContext.current

    val aura by auraDetailsViewModel.aura.collectAsState()

    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

    val collapsedHeight = 56.dp
    val collapsedHeightPx = with(density) { collapsedHeight.toPx() }

    val topInset = 56.dp // отступ сверху в expanded
    val topInsetPx = with(density) { topInset.toPx() }

    val expandedY = topInsetPx
    val collapsedY = screenHeightPx - collapsedHeightPx

    val offsetY = remember { Animatable(collapsedY) }
    val scope = rememberCoroutineScope()

    fun animateToExpanded() {
        scope.launch {
            offsetY.animateTo(expandedY, animationSpec = spring(stiffness = Spring.StiffnessMedium))
        }
    }

    fun animateToCollapsed() {
        scope.launch {
            offsetY.animateTo(collapsedY, animationSpec = spring(stiffness = Spring.StiffnessMedium))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AuraColors.background),
    ) {
        aura?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "Аура фон",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }


        Button(
            modifier = Modifier
                .padding(36.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(10.dp),
            onClick = {
                val rnd = Random(System.currentTimeMillis())
                val rndInd = rnd.nextInt(1, 10)

                auraDetailsViewModel.consumeAnswer(
                    context = context,
                    question = question,
                    answer = rndInd
                )
            },
            content = {
                Text(
                    color = AuraColors.textPrimary,
                    text = "история",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
        )

        val currentOffset = offsetY.value.coerceIn(expandedY, collapsedY)
        val progress = ((currentOffset - expandedY) / (collapsedY - expandedY)).coerceIn(0f, 1f)
        val cornerDp = lerp(0.dp, 24.dp, progress)

//        Surface(
//            modifier = Modifier
//                .offset { IntOffset(0, currentOffset.roundToInt()) }
//                .fillMaxSize(),
//            shape = RoundedCornerShape(topStart = cornerDp, topEnd = cornerDp),
//            color = MaterialTheme.colorScheme.background,
//            tonalElevation = 8.dp
//        ) {
//            Column(modifier = Modifier.fillMaxSize()) {
//                // Заголовок + handle
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(collapsedHeight)
//                        .pointerInput(Unit) {
//                            detectVerticalDragGestures(
//                                onVerticalDrag = { change, dragAmount ->
//                                    scope.launch {
//                                        change.consume()
//                                        val new = (offsetY.value + dragAmount).coerceIn(
//                                            expandedY,
//                                            collapsedY
//                                        )
//                                        offsetY.snapTo(new)
//                                    }
//                                },
//                                onDragEnd = {
//                                    val middle = (collapsedY + expandedY) / 2f
//                                    if (offsetY.value <= middle) animateToExpanded() else animateToCollapsed()
//                                }
//                            )
//                        },
//                    contentAlignment = Alignment.Center
//                ) {
//                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                        Box(
//                            modifier = Modifier
//                                .width(48.dp)
//                                .height(4.dp)
//                                .background(Color.Gray, RoundedCornerShape(2.dp))
//                        )
//                        Text(
//                            text = "Aura Details",
//                            style = MaterialTheme.typography.titleMedium,
//                            modifier = Modifier.padding(top = 4.dp)
//                        )
//                    }
//                }
//
//
//            }
//        }
    }
}