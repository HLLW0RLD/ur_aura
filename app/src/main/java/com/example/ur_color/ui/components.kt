package com.example.ur_color.ui

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ur_color.R
import kotlin.math.roundToInt

enum class WindowType { Slim, Regular, Full }

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
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (showBack) {
                IconButton(onClick = { onBackClick?.invoke() }) {
                    Icon(
                        painter = backIcon,
                        contentDescription = "",
                        tint = backIconTint
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(48.dp))
            }

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = if (isCentered) Modifier.weight(1f)
                else Modifier.weight(1f).padding(start = 8.dp),
                textAlign = if (isCentered) TextAlign.Center else TextAlign.Start
            )

            if (showOptions) {
                IconButton(onClick = { onOptionsClick?.invoke() }) {
                    Icon(
                        painter = optionsIcon,
                        contentDescription = "",
                        tint = optionsIconTint
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(48.dp))
            }
        }
    }
}

@Composable
fun ExpandableFloatingBox(
    closedTitle: String,
    expandedTitle: String,
    modifier: Modifier = Modifier,
    windowType: WindowType = WindowType.Regular,
    canShowFull: Boolean = false,
    height: Float? = null,
    width: Float? = null,
    expandHeight: Float? = null,
    expandWidth: Float? = null,
    onOpen: () -> Unit = {},
    onClose: () -> Unit = {},
    onCancel: (() -> Unit)? = null,
    onConfirm: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    ExpandableBase(
        closedTitle = closedTitle,
        expandedTitle = expandedTitle,
        modifier = modifier,
        windowType = windowType,
        canShowFull = canShowFull,
        height = height,
        width = width,
        expandHeight = expandHeight,
        expandWidth = expandWidth,
        onOpen = onOpen,
        onClose = onClose,
        onCancel = onCancel,
        onConfirm = onConfirm,
        content = content
    )
}

@Composable
private fun ExpandableBase(
    closedTitle: String,
    expandedTitle: String,
    modifier: Modifier = Modifier,
    windowType: WindowType = WindowType.Regular,
    canShowFull: Boolean = false,
    height: Float? = null,
    width: Float? = null,
    expandHeight: Float? = null,
    expandWidth: Float? = null,
    onOpen: () -> Unit,
    onClose: () -> Unit,
    onCancel: (() -> Unit)?,
    onConfirm: (() -> Unit)?,
    content: @Composable () -> Unit
) {
    val scrollState = rememberScrollState()
    var expanded by remember { mutableStateOf(false) }
    var currentWindowType by remember { mutableStateOf(windowType) }

    val transition = updateTransition(targetState = expanded, label = "expandTransition")
    val animationSpeed = 700
    val easing = LinearOutSlowInEasing

    val scale by transition.animateFloat(
        transitionSpec = { tween(animationSpeed, easing = easing) },
        label = "scaleAnim"
    ) { if (it) 1f else 0.9f }

    val elevation by transition.animateDp(
        transitionSpec = { tween(animationSpeed, easing = easing) },
        label = "elevationAnim"
    ) { if (it) 12.dp else 2.dp }

    val alpha by transition.animateFloat(
        transitionSpec = { tween(animationSpeed, easing = easing) },
        label = "alphaAnim"
    ) { if (it) 1f else 0f }

    val offsetY by transition.animateDp(
        transitionSpec = { tween(animationSpeed, easing = easing) },
        label = "offsetY"
    ) { if (it) 0.dp else 50.dp }

    val targetHeightFraction = when (currentWindowType) {
        WindowType.Slim -> 0.25f
        WindowType.Regular -> 0.55f
        WindowType.Full -> 0.9f
    }

    val heightFraction by transition.animateFloat(
        transitionSpec = { tween(animationSpeed, easing = easing) },
        label = "heightFractionAnim"
    ) { if (it) targetHeightFraction else 0.1f }

    fun toggleExpand(toFull: Boolean = false) {
        expanded = !expanded
        if (expanded) {
            onOpen()
            currentWindowType = if (toFull) WindowType.Full else windowType
        } else {
            onClose()
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth(if (expanded) expandWidth ?: 1f else width ?: 1f)
            .heightIn(
                max = if (expanded)
                    (expandHeight?.dp ?: (heightFraction * 800).dp)
                else
                    height?.dp ?: 80.dp
            )
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                shadowElevation = elevation.toPx()
                clip = true
                shape = RoundedCornerShape(20.dp)
            }
            .background(MaterialTheme.colorScheme.surface)
            .pointerInput(canShowFull) {
                detectTapGestures(
                    onTap = { toggleExpand() },
                    onLongPress = { if (canShowFull) toggleExpand(true) }
                )
            }
            .padding(16.dp)
    ) {
        if (expanded) {
            ExpandableContent(
                expandedTitle = expandedTitle,
                scrollState = scrollState,
                alpha = alpha,
                onCancel = onCancel,
                onConfirm = onConfirm,
                onClose = { toggleExpand() },
                content = content
            )
        } else {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    textAlign = TextAlign.Center,
                    text = closedTitle,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
private fun ExpandableContent(
    expandedTitle: String,
    scrollState: ScrollState,
    alpha: Float,
    onCancel: (() -> Unit)?,
    onConfirm: (() -> Unit)?,
    onClose: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .alpha(alpha)
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = expandedTitle,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(R.drawable.close_filled),
                contentDescription = "Закрыть",
                modifier = Modifier.clickable { onClose() }
            )
        }

        Spacer(Modifier.height(4.dp))
        HorizontalDivider(thickness = 0.5.dp, color = Color.Black)

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            content()

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                onCancel?.let {
                    TextButton(onClick = it) { Text("Отмена") }
                }
                onConfirm?.let {
                    TextButton(onClick = it) { Text("ОК") }
                }
            }
        }
    }
}
