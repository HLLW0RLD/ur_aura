package com.example.ur_color.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ur_color.R
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.utils.getCurrentDateTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.core.graphics.toColorInt

enum class WindowType { Slim, Regular, Full }

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AuraPickerField(
    label: String,
    date: String,
    color: Color? = null,
    onDateChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDatePicker by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxWidth()) {
        AuraOutlinedTextField(
            color = color,
            value = date,
            onValueChange = {},
            readOnly = true,
            label = label,
            trailingIcon = {
//                Icon(
//                    Icons.Default.DateRange, contentDescription = "Выбрать дату",
//                    tint = color ?: AppColors.accentPrimary
//                )
            },
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clickable { showDatePicker = true }
        )
    }

    if (showDatePicker) {
        ReturnAuraPickerDialog(
            initialDate = date.ifEmpty { getCurrentDateTime() },
            onDateSelected = { newDate ->
                onDateChanged(newDate)
            },
            onDismiss = { showDatePicker = false },
            color = color
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReturnAuraPickerDialog(
    initialDate: String? = null,
    color: Color? = null,
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val initialMillis = remember(initialDate) {
        initialDate?.let { dateString ->
            try {
                SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                    .parse(dateString)?.time
            } catch (e: Exception) {
                null
            }
        }
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialMillis
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            AuraTextButton(color = color, text = "OK") {
                datePickerState.selectedDateMillis?.let { millis ->
                    val selectedDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                        .format(Date(millis))
                    onDateSelected(selectedDate)
                }
                onDismiss()
            }
        },
        dismissButton = {
            AuraTextButton(text = "Отмена", color = color) { onDismiss }
        },
        colors = DatePickerDefaults.colors(
            containerColor = color ?: AppColors.accentPrimary,
        ),
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
fun AuraTextButton(
    text: String,
    color: Color? = null,
    enabled: Boolean = true,
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = color ?: AppColors.accentPrimary,
            disabledContainerColor = color ?: AppColors.surface,
        ),
        onClick = {
            onClick()
        },
        enabled = enabled,

        border = border,
        contentPadding = contentPadding,
        modifier = Modifier.then(modifier)
    ) {
        Text(
            text = text,
            color = AppColors.background
        )
    }
}

@Composable
fun AuraRadioButton(
    selected: Boolean,
    text: String,
    color: Color? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .then(modifier)
            .padding(start = 4.dp)
            .clickable {
                onClick()
            },
    ) {
        RadioButton(
            selected = selected,
            onClick = { onClick() },
            colors = RadioButtonDefaults.colors(
                selectedColor = color ?: AppColors.accentPrimary,
                unselectedColor = AppColors.surface
            )
        )
        Text(
            text = text,
            color = if (selected) AppColors.textPrimary else AppColors.textSecondary
        )
    }
}

@Composable
fun AuraOutlinedTextField(
    value: String,
    label: String,
    color: Color? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    onValueChange: (String) -> Unit,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors(
            focusedLabelColor = color ?: AppColors.textPrimary,
            unfocusedLabelColor = color ?: AppColors.textPrimary,
            focusedBorderColor = color ?: AppColors.textPrimary,
            unfocusedBorderColor = color ?: AppColors.textPrimary,
        ),
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth(),

        enabled = enabled,
        readOnly = readOnly,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
    )
}

@Composable
fun CustomAppBar(
    title: String,
    showBack: Boolean = false,
    showOptions: Boolean = false,
    isCentered: Boolean = true,
    showDivider: Boolean = true,
    onBackClick: (() -> Unit)? = null,
    onOptionsClick: (() -> Unit)? = null,
    backIcon: Painter = painterResource(R.drawable.arrow_left),
    optionsIcon: Painter = painterResource(R.drawable.switcher_options),
    backIconTint: Color = AppColors.surface,
    optionsIconTint: Color = AppColors.surface,
    backgroundColor: Color = AppColors.background,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .height(56.dp),
        color = backgroundColor
    ) {
        Box {
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
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
                    else Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
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

            if (showDivider) {
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    thickness = 0.5.dp,
                    color = AppColors.textPrimary
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SwipeCard(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = AppColors.textPrimary,
    backgroundColor: Color = AppColors.surface,
    onSwipeLeft: () -> Unit = {},
    onSwipeRight: () -> Unit = {},
    height: Dp = 620.dp,
    width: Dp = 370.dp,
    shadowElevation: Dp = 8.dp,
    fontSize: TextUnit = 20.sp,
    minLines: Int = 2,
    maxLines: Int = 2,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(shadowElevation)
    ) {
        Box(
            modifier = Modifier
                .size(width = width, height = height)
                .fillMaxSize()
                .padding(3.dp)
                .border(
                    shape = RoundedCornerShape(24.dp),
                    width = 6.dp,
                    color = AppColors.background
                )
                .padding(24.dp),
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = textColor,
                fontSize = fontSize,
                minLines = minLines,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(60.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            shape = CircleShape,
                            color = AppColors.white
                        )
                        .padding(3.dp)
                        .border(
                            border = BorderStroke(3.dp, AppColors.success),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .clickable {
                            onSwipeLeft()
                        },
                ) {
                    Text(
                        text = "Да",
                        textAlign = TextAlign.Center,
                        color = AppColors.black,
                        fontSize = 16.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                    )
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            shape = CircleShape,
                            color = AppColors.white
                        )
                        .padding(3.dp)
                        .border(
                            border = BorderStroke(3.dp, AppColors.error),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .clickable {
                            onSwipeRight()
                        },
                ) {
                    Text(
                        text = "Нет",
                        textAlign = TextAlign.Center,
                        color = AppColors.black,
                        fontSize = 16.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
fun GradientGraphBox(
    values: List<Int>,
    modifier: Modifier = Modifier,
    barSpacingDp: Int = 6,
    showStat: Boolean = true
) {
    val safeValues = values.map { it.coerceIn(0, 10) }
    val barCount = safeValues.size
    val anims = remember(values) {
        safeValues.map { Animatable(0f) }
    }

    LaunchedEffect(safeValues) {
        anims.forEach { it.snapTo(0f) }
        val scope = this
        safeValues.forEachIndexed { idx, v ->
            val target = (v.coerceIn(0, 10) / 10f)
            scope.launch {
                delay(idx * 40L)
                anims[idx].animateTo(
                    target,
                    animationSpec = tween(
                        durationMillis = 400,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        }
    }

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        if (barCount == 0) return@Canvas

        val labelAreaHeight = if (showStat) 18.dp.toPx() else 0f
        val graphHeight = h - labelAreaHeight

        val spacing = barSpacingDp.dp.toPx()
        val totalSpacing = spacing * (barCount - 1).coerceAtLeast(0)
        val barWidth = (w - totalSpacing) / barCount

        val textPaint = android.graphics.Paint().apply {
            color = "#444444".toColorInt()
            textAlign = android.graphics.Paint.Align.CENTER
            textSize = 13.sp.toPx()
            isAntiAlias = true
        }

        fun colorForValue(fraction: Float): Color {
            return when {
                fraction <= 0.5f -> {
                    lerp(Color(0xFFFF4D4D), Color(0xFF45D07B), fraction / 0.5f)
                }
                else -> {
                    lerp(Color(0xFF45D07B), Color(0xFF8F00FF), (fraction - 0.5f) / 0.5f)
                }
            }
        }

        safeValues.forEachIndexed { i, v ->
            val x = i * (barWidth + spacing)
            val frac = anims.getOrNull(i)?.value ?: (v.coerceIn(0, 10) / 10f)
            val barHeight = frac * graphHeight
            val top = graphHeight - barHeight

            val rect = Rect(x, top, x + barWidth, graphHeight)
            drawRoundRect(
                color = colorForValue(frac),
                topLeft = Offset(rect.left, rect.top),
                size = rect.size,
                cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
            )

            drawRoundRect(
                color = Color.Black.copy(alpha = 0.06f),
                topLeft = Offset(rect.left, rect.top),
                size = rect.size,
                cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
                blendMode = BlendMode.SrcOver
            )

            if (showStat) {
                val textY = h - 4.dp.toPx() // нижняя часть Canvas
                drawContext.canvas.nativeCanvas.drawText(
                    v.toString(),
                    x + barWidth / 2,
                    textY,
                    textPaint
                )
            }
        }

        drawLine(
            color = Color(0x22000000),
            strokeWidth = 1.dp.toPx(),
            start = Offset(0f, graphHeight),
            end = Offset(w, graphHeight)
        )
    }
}

@Composable
fun ExpandableBox(
    collapsedText: String,
    modifier: Modifier = Modifier,
    content: (@Composable () -> Unit)? = null
) {
    var collapsed by remember { mutableStateOf(true) }

    Surface(
        modifier = modifier
            .wrapContentHeight()
            .background(AppColors.surface, RoundedCornerShape(10.dp))
            .padding(3.dp)
            .border(BorderStroke(3.dp, AppColors.background), RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        tonalElevation = 2.dp,
        shadowElevation = 4.dp,
    ) {
        Column(
            modifier = Modifier
                .clickable { collapsed = !collapsed }
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .then(
                        if (collapsed) {
                            Modifier.fillMaxWidth()
                        } else {
                            Modifier.width(150.dp)
                        }
                    )
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = collapsedText, fontSize = 14.sp, color = Color(0xFF222222))
                AnimatedVisibility(
                    visible = !collapsed,
                    enter = expandVertically(animationSpec = tween(300)) + fadeIn(tween(200)),
                    exit = shrinkVertically(animationSpec = tween(250)) + fadeOut(tween(180))
                ) {
                    Icon(
                        tint = Color.Black,
                        painter = painterResource(R.drawable.close_filled),
                        contentDescription = "Закрыть",
                        modifier = Modifier.clickable { collapsed = !collapsed }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            AnimatedVisibility(
                visible = !collapsed,
                enter = expandVertically(animationSpec = tween(300)) + fadeIn(tween(200)),
                exit = shrinkVertically(animationSpec = tween(250)) + fadeOut(tween(180))
            ) {
                if (content != null) {
                    content()
                }
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

    val revAlpha by transition.animateFloat(
        transitionSpec = { tween(animationSpeed, easing = easing) },
        label = "alphaAnim"
    ) { if (it) 0f else 1f }

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
            .background(if (expanded) AppColors.background else AppColors.surface)
            .padding(3.dp)
            .border(
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(
                    color = if (expanded) AppColors.surface else AppColors.background,
                    width = 3.dp
                )
            )
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
            Box(
                Modifier
                    .fillMaxSize()
//                    .background(AuraColors.accentSecondary.copy(revAlpha))
                ,
                contentAlignment = Alignment.Center
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = closedTitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.textPrimary
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
    titleColor: Color = AppColors.textPrimary,
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
                color = titleColor,
                text = expandedTitle,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Icon(
                tint = AppColors.textPrimary,
                painter = painterResource(R.drawable.close_filled),
                contentDescription = "Закрыть",
                modifier = Modifier.clickable { onClose() }
            )
        }

        Spacer(Modifier.height(4.dp))
        HorizontalDivider(thickness = 0.5.dp, color = AppColors.accentPrimary)

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
                    TextButton(onClick = it) {
                        Text(
                            text = "Отмена",
                            color = AppColors.textPrimary,
                        )
                    }
                }
                onConfirm?.let {
                    TextButton(onClick = it) {
                        Text(
                            text = "ОК",
                            color = AppColors.textPrimary,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DynamicDoubleColumn(
    useScroll: Boolean = false,
    spacing: Dp = 12.dp,
    paddingHorizontal: Dp = 0.dp,
    paddingVertical: Dp = 0.dp,
    modifier: Modifier = Modifier,
    content: @Composable TwoColumnScope.() -> Unit
) {
    val scope = remember { TwoColumnScopeImpl() }
    val scrollState = rememberScrollState()

    val mod = if (useScroll) modifier.verticalScroll(scrollState) else modifier

    scope.content()
    Row(
        modifier = mod
            .padding(horizontal = paddingHorizontal, vertical = paddingVertical),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(spacing)
        ) {
            scope.leftColumn.forEach { it() }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(spacing)
        ) {
            scope.rightColumn.forEach { it() }
        }
    }
}


private class TwoColumnScopeImpl : TwoColumnScope {
    val leftColumn = mutableListOf<@Composable () -> Unit>()
    val rightColumn = mutableListOf<@Composable () -> Unit>()
    private var toggle = false

    override fun left(content: @Composable () -> Unit) {
        leftColumn.add(content)
    }

    override fun right(content: @Composable () -> Unit) {
        rightColumn.add(content)
    }

    override fun item(content: @Composable () -> Unit) {
        if (toggle) rightColumn.add(content) else leftColumn.add(content)
        toggle = !toggle
    }
}
interface TwoColumnScope {
    fun left(content: @Composable () -> Unit)
    fun right(content: @Composable () -> Unit)
    fun item(content: @Composable () -> Unit)
}

private fun lerp(start: Color, stop: Color, fraction: Float): Color {
    val f = fraction.coerceIn(0f, 1f)
    return Color(
        red = start.red + (stop.red - start.red) * f,
        green = start.green + (stop.green - start.green) * f,
        blue = start.blue + (stop.blue - start.blue) * f,
        alpha = start.alpha + (stop.alpha - start.alpha) * f
    )
}
