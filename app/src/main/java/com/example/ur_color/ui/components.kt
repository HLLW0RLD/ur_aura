package com.example.ur_color.ui

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ur_color.R

@Composable
fun CustomAppBar(
    title: String,
    showBack: Boolean = false,
    showOptions: Boolean = false,
    isCentered: Boolean = true,
    onBackClick: (() -> Unit)? = null,
    onOptionsClick: (() -> Unit)? = null,
    backIcon: Painter = painterResource(R.drawable.arrow_left),
    optionsIcon: Painter = painterResource(R.drawable.switcher_options),
    backIconTint: Color = Color.Black,
    optionsIconTint: Color = Color.Black,
    backgroundColor: Color = Color.White,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
            .height(56.dp),
        color = backgroundColor,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (showBack) {
                IconButton(
                    onClick = { onBackClick?.invoke() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        painter = backIcon,
                        contentDescription = "Назад",
                        tint = backIconTint
                    )
                }
            }

            if (showOptions) {
                IconButton(
                    onClick = { onOptionsClick?.invoke() },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        painter = optionsIcon,
                        contentDescription = "Опции",
                        tint = optionsIconTint
                    )
                }
            }

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .align(if (isCentered) Alignment.Center else Alignment.CenterStart)
                    .padding(
                        start = if (!isCentered && showBack) 56.dp else 16.dp,
                        end = 56.dp
                    )
            )
        }
    }
}

enum class WindowType { Slim, Regular, Full }

@Composable
fun ExpandableFloatingBox(
    closedTitle: String,
    expandedTitle: String,
    modifier: Modifier = Modifier,
    windowType: WindowType = WindowType.Regular,
    onClose: (() -> Unit) = {},
    onCancel: (() -> Unit)? = null,
    onConfirm: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val transition = updateTransition(targetState = expanded, label = "expandTransition")

    val animationSpeed = 700
    val easing = LinearOutSlowInEasing

    val scale by transition.animateFloat(
        transitionSpec = { tween(durationMillis = animationSpeed, easing = easing) },
        label = "scaleAnim"
    ) { if (it) 1f else 0.9f }

    val elevation by transition.animateDp(
        transitionSpec = { tween(durationMillis = animationSpeed, easing = easing) },
        label = "elevationAnim"
    ) { if (it) 12.dp else 2.dp }

    val corner by transition.animateDp(
        transitionSpec = { tween(durationMillis = animationSpeed, easing = easing) },
        label = "cornerAnim"
    ) { if (it) 16.dp else 28.dp }

    val alpha by transition.animateFloat(
        transitionSpec = { tween(durationMillis = animationSpeed - 100, easing = easing) },
        label = "alphaAnim"
    ) { if (it) 1f else 0f }

    val targetHeightFraction = when (windowType) {
        WindowType.Slim -> 0.25f
        WindowType.Regular -> 0.5f
        WindowType.Full -> 0.9f
    }

    val heightFraction by transition.animateFloat(label = "heightFractionAnim") {
        if (it) targetHeightFraction else 0.1f
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(heightFraction)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                shadowElevation = elevation.toPx()
                shape = RoundedCornerShape(corner)
                clip = true
            }
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(corner))
            .clickable(
                interactionSource = null
            ) { expanded = !expanded }
            .padding(16.dp)
    ) {
        if (expanded) {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .alpha(alpha)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(0.7f),
                        text = expandedTitle,
                        maxLines = 2,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Icon(
                        modifier = Modifier.clickable {
                            expanded = false
                            onClose()
                        },
                        painter = painterResource(R.drawable.close_filled),
                        contentDescription = "Закрыть"
                    )
                }
                Spacer(Modifier.size(4.dp))
                HorizontalDivider(thickness = 0.5.dp, color = Color.Black)

                content()

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    if (onCancel != null) {
                        TextButton(
                            onClick = { onCancel() }
                        ) {
                            Text("Отмена")
                        }
                    }
                    if (onConfirm != null) {
                        TextButton(
                            onClick = { onConfirm() }
                        ) {
                            Text("ОК")
                        }
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(closedTitle, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

