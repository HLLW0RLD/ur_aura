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
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.ui.screen.viewModel.AuraDetailsViewModel
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.utils.LocalNavController
import com.example.ur_color.utils.toColoredText
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt

@Serializable
data class AuraDetails(val color: String? = null) : Screen

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun AuraDetailsScreen(
    cd: AuraDetails,
    auraDetailsViewModel: AuraDetailsViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val navController = LocalNavController.current

    val aura by auraDetailsViewModel.aura.collectAsState()
    val userData by auraDetailsViewModel.user.collectAsState()

    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

    val collapsedHeight = 130.dp
    val collapsedHeightPx = with(density) { collapsedHeight.toPx() }

    val topInset = 95.dp
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
            .background(AppColors.background),
    ) {
        CustomAppBar(
            title = "aura details",
            showBack = true,
            onBackClick = {
                navController.popBackStack()
            },
            showDivider = true,
            isCentered = false,
            backgroundColor = AppColors.background,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        aura?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "Аура фон",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }

        val currentOffset = offsetY.value.coerceIn(expandedY, collapsedY)
        val progress = ((currentOffset - expandedY) / (collapsedY - expandedY)).coerceIn(0f, 1f)
        val cornerDp = lerp(0.dp, 24.dp, progress)

        Surface(
            modifier = Modifier
                .offset { IntOffset(0, currentOffset.roundToInt()) }
                .fillMaxSize()
                .border(
                    color = AppColors.surface,
                    shape = RoundedCornerShape(topStart = cornerDp, topEnd = cornerDp),
                    width = 2.dp
                ),
            shape = RoundedCornerShape(topStart = cornerDp, topEnd = cornerDp),
            color = AppColors.background,
            tonalElevation = 8.dp
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(collapsedHeight)
                    .pointerInput(Unit) {
                        detectVerticalDragGestures(
                            onVerticalDrag = { change, dragAmount ->
                                scope.launch {
                                    change.consume()
                                    val new = (offsetY.value + dragAmount).coerceIn(
                                        expandedY,
                                        collapsedY
                                    )
                                    offsetY.snapTo(new)
                                }
                            },
                            onDragEnd = {
                                val middle = (collapsedY + expandedY) / 2f
                                if (offsetY.value <= middle) animateToExpanded() else animateToCollapsed()
                            }
                        )
                    }
                    .padding(16.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Item("Energy Level", userData?.energyLevel ?: "0", userData?.energyCapacity)
                    Item("Mood", userData?.mood ?: "0", userData?.moodVector)
                    Item("Stress Level", userData?.stressLevel ?: "0", userData?.stressVector)
                    Item("Focus", userData?.focus ?: "0", userData?.focusVector)
                    Item("Motivation", userData?.motivation ?: "0", userData?.motivationVector)
                    Item("Creativity", userData?.creativity ?: "0", userData?.creativityVector)
                    Item("Emotional Balance", userData?.emotionalBalance ?: "0", userData?.emotionalBalanceVector)
                    Item("Physical Energy", userData?.physicalEnergy ?: "0", userData?.physicalEnergyVector)
                    Item("Sleep Quality", userData?.sleepQuality ?: "0", userData?.sleepQualityVector)
                    Item("Intuition Level", userData?.intuitionLevel ?: "0", userData?.intuitionVector)
                    Item("Social Energy", userData?.socialEnergy ?: "0", userData?.socialVector)
                }
            }
        }
    }
}

@Composable
fun Item(
    label: String,
    value: Any,
    vector: List<Int>?
) {
    val textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)) {
        Text(
            text = "$label: $value",
            style = textStyle,
            fontWeight = FontWeight.Bold,
            color = AppColors.textPrimary
        )

        if (vector != null) {
            Text(
                text = vector.toColoredText(),
                style = textStyle,
                color = AppColors.surface
            )
        }

        Divider(modifier = Modifier.padding(top = 6.dp))
    }
}