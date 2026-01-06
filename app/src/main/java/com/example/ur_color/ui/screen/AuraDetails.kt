package com.example.ur_color.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.example.ur_color.R
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.ui.screen.viewModel.AuraDetailsViewModel
import com.example.ur_color.ui.screen.viewModel.ProfileViewModel
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.AppScaffold
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
fun AuraDetails(auraDetails : AuraDetails) {
    AppScaffold(
        showBottomBar = false,
        topBar = {
            val navController = LocalNavController.current

            CustomAppBar(
                title = stringResource(R.string.app_name_stylized),
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
        AuraDetailsScreen(
            modifier = Modifier.padding(it)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun AuraDetailsScreen(
    auraDetailsViewModel: AuraDetailsViewModel = koinViewModel(),
    profileViewModel: ProfileViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val navController = LocalNavController.current
    val scrollState = rememberScrollState()

    val aura by profileViewModel.aura.collectAsState()
    val userData by profileViewModel.user.collectAsState()

    val density = LocalDensity.current
    val configuration = LocalConfiguration.current
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

    val collapsedHeight = 330.dp
    val collapsedHeightPx = with(density) { collapsedHeight.toPx() }

    val topInset = 1.dp
    val topInsetPx = with(density) { topInset.toPx() }

    val expandedY = topInsetPx
    val collapsedY = screenHeightPx - collapsedHeightPx

    val offsetY = remember { Animatable(collapsedY) }
    val scope = rememberCoroutineScope()

    val progress = ((collapsedY - offsetY.value) / (collapsedY - expandedY)).coerceIn(0f, 1f)
    val canScroll = progress >= 0.999f

    LaunchedEffect(Unit) {
        profileViewModel.init(context)
    }

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
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.background),
    ) {

        aura?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "",
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
                    modifier = Modifier
                        .then(if (canScroll) Modifier.verticalScroll(scrollState) else Modifier)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "✦ Базовый цвет — тёплый, насыщенный оранжевый, но он собран внутрь, как огонь, которому не дают разгореться. Это не отсутствие чувств, а их дисциплинированное удерживание.",
                        color = AppColors.textPrimary
                    )
                    Text(
                        text = "✦ Ближе к поверхности оранжевый постепенно гаснет, переходя в дымчатые графитовые узоры. Они движутся медленно, будто человек постоянно перерабатывает свои переживания, но никому не показывает этот процесс",
                        color = AppColors.textPrimary
                    )
                    Text(
                        text = "✦ Края ауры очерчены тонкой голубой линией — это спокойствие, которое человек выставляет наружу. Оно немного искусственное, но не фальшивое: больше как навык держать себя в руках, чем попытка обмануть.",
                        color = AppColors.textPrimary
                    )
                    Text(
                        text = "✦ Вся аура кажется аккуратно собранной, как будто человек всю жизнь тренируется удерживать свои реакции внутри, чтобы не нагружать других. Внутренняя энергия яркая, живая, но запечатанная в удобную форму",
                        color = AppColors.textPrimary
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = progress <= 0.95f,
            modifier = Modifier
                .align(Alignment.BottomEnd)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .fillMaxWidth()
                    .padding(end = 16.dp, bottom = 100.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                FloatingActionButton(
                    onClick = {
                        scope.launch { offsetY.animateTo(collapsedY, tween(400)) }
                    },
                    containerColor = AppColors.accentPrimary.copy(alpha = 0.5f),
                    contentColor = Color.White
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_up),
                        contentDescription = "Добавить"
                    )
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