package com.example.ur_color.ui

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberOverscrollEffect
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import coil.compose.AsyncImage
import com.example.ur_color.data.model.SocialContent
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.ur_color.data.model.User
import com.example.ur_color.utils.IconPosition
import com.example.ur_color.utils.TwoColumnScope
import com.example.ur_color.utils.TwoColumnScopeImpl
import com.example.ur_color.utils.WindowType
import com.example.ur_color.utils.animPic
import com.example.ur_color.utils.lerp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlin.Int.Companion.MAX_VALUE

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AuraDatePickerField(
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
private fun ReturnAuraPickerDialog(
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AuraDateTimePickerField(
    label: String,
    time: String,
    color: Color? = null,
    onTimeChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showTimePicker by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxWidth()) {
        AuraOutlinedTextField(
            color = color,
            value = time,
            onValueChange = {},
            readOnly = true,
            label = label,
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clickable { showTimePicker = true }
        )
    }

    if (showTimePicker) {
        AuraTimePickerDialog(
            initialTime = time,
            color = color,
            onTimeSelected = {
                showTimePicker = false
                onTimeChanged(it)
            },
            onDismiss = { showTimePicker = false }
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AuraTimePickerDialog(
    initialTime: String,
    color: Color? = null,
    onTimeSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val (hour, minute) = remember(initialTime) {
        try {
            val parts = initialTime?.split(":")
            parts?.let {
                it[0].toInt() to it[1].toInt()
            } ?: (0 to 0)
        } catch (e: Exception) {
            0 to 0
        }
    }

    val timePickerState = rememberTimePickerState(
        initialHour = hour,
        initialMinute = minute,
        is24Hour = true
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            AuraTextButton(text = "OK", color = color) {
                val formatted = String.format(
                    "%02d:%02d",
                    timePickerState.hour,
                    timePickerState.minute
                )

                onTimeSelected(formatted)
            }
        },
        dismissButton = {
            AuraTextButton(text = "Отмена", color = color) { onDismiss() }
        },
        text = {
            TimePicker(state = timePickerState)
        },
        containerColor = color ?: AppColors.accentPrimary
    )
}

@Composable
fun AuraDropdown(
    options: List<String>,
    selectedOption: String? = null,
    placeholder: String = "Выберите вариант",
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = AppColors.surface,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { expanded = true }
                .padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            AuraDropdownItem(
                text = selectedOption,
                placeholder = placeholder
            )

            Icon(
                painter = if (expanded) painterResource(R.drawable.arrow_up) else painterResource(
                    R.drawable.arrow_down
                ),
                contentDescription = null,
                tint = AppColors.icon
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.background)
        ) {
            options.forEach { option ->
                AuraDropdownItem(
                    text = selectedOption,
                )
            }
        }
    }
}
@Composable
private fun AuraDropdownItem(
    text: String? = null,
    icon: Painter? = null,
    placeholder: String = "Выберите вариант",
    iconPosition: IconPosition = IconPosition.START
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null && iconPosition == IconPosition.START) {
            Icon(icon, contentDescription = null, tint = AppColors.textPrimary)
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = text ?: placeholder,
            color = if (text != null) AppColors.textPrimary else AppColors.textSecondary,
            modifier = Modifier.weight(1f)
        )
        if (icon != null && iconPosition == IconPosition.END) {
            Spacer(modifier = Modifier.width(8.dp))
            Icon(icon, contentDescription = null, tint = AppColors.icon)
        }
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
fun AutoScrollHorizontalPager(
    pageCount: Int,
    autoScroll: Boolean = true,
    isInfinite: Boolean = true,
    showIndicator: Boolean = true,
    intervalMs: Long = 3_000L,
    pageSpacing: Dp = 16.dp,
    modifier: Modifier = Modifier,
    content: @Composable (page: Int) -> Unit
) {

    val virtualCount = if (isInfinite) Int.MAX_VALUE else pageCount
    val startPage = if (isInfinite) virtualCount / 2 else 0

    val pagerState = rememberPagerState(
        initialPage = startPage,
        pageCount = { virtualCount }
    )

    val pagerAnimationSpec = tween<Float>(
        durationMillis = 750,
        easing = FastOutSlowInEasing
    )

    if (autoScroll) {
        LaunchedEffect(Unit) {
            delay(intervalMs)
            pagerState.animateScrollToPage(
                page = pagerState.currentPage + 1,
                animationSpec = pagerAnimationSpec
            )
        }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.isScrollInProgress }
                .filter { !it }
                .drop(1)
                .collectLatest {
                    delay(intervalMs)
                    pagerState.animateScrollToPage(
                        page = pagerState.currentPage + 1,
                        animationSpec = pagerAnimationSpec
                    )
                }
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {

        HorizontalPager(
            state = pagerState,
            pageSpacing = pageSpacing,
            overscrollEffect = rememberOverscrollEffect()
        ) { page ->
            val realPage =
                if (isInfinite) page % pageCount
                else page

            content(realPage)
        }

        if (showIndicator) {
            PagerDotsIndicator(
                activeImages = animPic,
                pageCount = pageCount,
                currentPage = pagerState.currentPage % pageCount,
                modifier = Modifier
                    .fillMaxWidth()
//                    .align(Alignment.BottomCenter)
//                    .padding(bottom = 12.dp)
            )
        }
    }
}
@Composable
private fun PagerDotsIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
    dotSize: Dp = 8.dp,
    activeImages: List<Int>,
    activeColor: Color = AppColors.divider,
    inactiveColor: Color = AppColors.divider
) {

    val recentImages = remember { mutableListOf<Int>() }
    val activeImage = remember(currentPage) {
        val availableImages = activeImages.filter { it !in recentImages }
        val chosen = if (availableImages.isNotEmpty()) availableImages.random() else activeImages.random()
        recentImages.add(chosen)
        if (recentImages.size > 3) recentImages.removeAt(0)
        chosen
    }

    BoxWithConstraints(
        modifier = modifier
            .height(dotSize * 2),
        contentAlignment = Alignment.CenterStart
    ) {
        val rowWidth = maxWidth * 0.66f
        val totalDotsWidth = dotSize * pageCount
        val spacing = if (pageCount > 1) (rowWidth - totalDotsWidth) / (pageCount - 1) else 0.dp

        val startOffset = (maxWidth - rowWidth) / 2

        val targetOffset = startOffset + (dotSize + spacing) * currentPage
        val animatedOffset by animateDpAsState(
            targetValue = targetOffset,
            animationSpec = tween(durationMillis = 300)
        )

        Row(
            modifier = Modifier
                .width(rowWidth)
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(spacing)
        ) {
            repeat(pageCount) { i ->
                val targetColor = if (currentPage == i) Color.Transparent else inactiveColor
                val animatedColor by animateColorAsState(
                    targetValue = targetColor,
                    animationSpec = tween(durationMillis = 300)
                )

                Box(
                    modifier = Modifier
                        .size(dotSize)
                        .clip(CircleShape)
                        .background(animatedColor)
                )
            }
        }

        Box(
            modifier = Modifier
                .offset(x = animatedOffset)
                .size(dotSize + 2.dp)
        ) {
            Image(
                painter = painterResource(activeImage),
                contentDescription = "",
                colorFilter = ColorFilter.tint(activeColor),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun CustomAppBar(
    title: String = stringResource(R.string.app_name_stylized),
    showBack: Boolean = false,
    showOptions: Boolean = false,
    isCentered: Boolean = true,
    showDivider: Boolean = true,
    onBackClick: (() -> Unit)? = null,
    onOptionsClick: (() -> Unit)? = null,
    backIcon: Painter = painterResource(R.drawable.arrow_left),
    optionsIcon: Painter = painterResource(R.drawable.switcher_options),
    backIconTint: Color = AppColors.icon,
    optionsIconTint: Color = AppColors.icon,
    backgroundColor: Color = AppColors.icon,
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
    centerText: String ?= null,
    centerImg: Painter ?= null,
    modifier: Modifier = Modifier,
    textColor: Color = AppColors.textPrimary,
    backgroundColor: Color = AppColors.backgroundDark,
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
                    color = AppColors.divider
                )
                .padding(top = 24.dp)
            ,
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
                    .align(Alignment.TopCenter)
                    .height(60.dp)
                    .padding(horizontal = 16.dp)
            )

            if (centerText != null && centerImg != null) {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = centerText,
                        textAlign = TextAlign.Center,
                        color = textColor,
                        fontSize = fontSize * 3,
                        minLines = 1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(Modifier.size(16.dp))
                    Image(
                        modifier = Modifier
                            .size(100.dp),
                        painter = centerImg,
                        colorFilter = ColorFilter.tint(AppColors.accentPrimary),
                        contentDescription = null
                    )
                }
            } else if (centerText != null) {
                Text(
                    text = centerText,
                    textAlign = TextAlign.Center,
                    color = textColor,
                    fontSize = fontSize * 5,
                    minLines = 1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            } else if (centerImg != null) {
                Image(
                    modifier = Modifier
                        .size(170.dp)
                        .align(Alignment.Center),
                    painter = centerImg,
                    colorFilter = ColorFilter.tint(AppColors.accentPrimary),
                    contentDescription = null
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    contentAlignment = Alignment.BottomStart,
                    modifier = Modifier
                        .padding(6.dp)
                        .size(108.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 100.dp,
                                bottomStart = 18.dp,
                                bottomEnd = 0.dp,
                            )
                        )
                        .background(
                            color = AppColors.divider
                        )
                        .clickable {
                            onSwipeLeft()
                        },
                ) {
                    Text(
                        text = "Да",
                        textAlign = TextAlign.Center,
                        color = AppColors.textPrimary,
                        fontSize = 24.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                Box(
                    contentAlignment = Alignment.BottomEnd,
                    modifier = Modifier
                        .padding(6.dp)
                        .size(108.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 100.dp,
                                topEnd = 0.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 18.dp,
                            )
                        )
                        .background(
                            color = AppColors.divider
                        )
                        .clickable {
                            onSwipeRight()
                        },
                ) {
                    Text(
                        text = "Нет",
                        textAlign = TextAlign.Center,
                        color = AppColors.textPrimary,
                        fontSize = 24.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ExpandableGradientGraphBox(
    label: String,
    indicator: Float,
    values: List<Float>,
    vector: Int? = null,
    icon: Painter? = null,
    expanded: Boolean,
    onToggleExpanded: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = AppColors.backgroundLight,
    iconTint: Color = AppColors.accentPrimary,
    textColor: Color = AppColors.textPrimary
) {

    Box(
        modifier = modifier
            .background(
                shape = RoundedCornerShape(24.dp),
                color = backgroundColor
            )
            .clickable(
                interactionSource = null,
                indication = null
            ) {
                onToggleExpanded(false)
            }
    ) {

        AnimatedVisibility(
            visible = !expanded,
            enter = expandVertically(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeIn(
                animationSpec = tween(300)
            ),
            exit = shrinkVertically(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ) + fadeOut(
                animationSpec = tween(300)
            )
        ) {
            Box {
                Text(
                    modifier = Modifier
                        .align(Alignment.TopEnd),
                    textAlign = TextAlign.Center,
                    text = indicator.toInt().toString(),
                    color = AppColors.accentPrimary,
                    fontSize = 12.sp
                )

                Box(
                    modifier = Modifier
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        vector != null -> {
                            Icon(
                                painter = when (vector) {
                                    0 -> painterResource(R.drawable.illusion)
                                    1 -> painterResource(R.drawable.magic_sparkles)
                                    2 -> painterResource(R.drawable.magic_potion)
                                    3 -> painterResource(R.drawable.card_trick)
                                    4 -> painterResource(R.drawable.cauldron_potion)
                                    5 -> painterResource(R.drawable.magic_stick_sparckles)
                                    6 -> painterResource(R.drawable.ball_crystal)
                                    7 -> painterResource(R.drawable.candle)
                                    8 -> painterResource(R.drawable.witch_hat)
                                    9 -> painterResource(R.drawable.illusion_eye)
                                    10 -> painterResource(R.drawable.magic_hat)
                                    else -> painterResource(R.drawable.magic_hat)
                                },
                                contentDescription = "Active dot",
                                tint = iconTint,
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .size(24.dp)
//                                        .padding(bottom = 36.dp)
                            )
                        }

                        icon != null -> icon
                        icon == null && vector == null -> {
                            Text(
                                text = label,
                                color = textColor,
                            )
                        }
                    }
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        ) {
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeIn(
                    animationSpec = tween(300)
                ),
                exit = shrinkVertically(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ) + fadeOut(
                    animationSpec = tween(300)
                )
            ) {
                Column {
                    Text(
                        text = label,
                        color = textColor,
                    )
                    GradientGraphBox(
                        values = values,
                        modifier = Modifier
                            .width(150.dp)
                            .height(60.dp)
                    )
                }
            }
        }
    }
}
@Composable
private fun GradientGraphBox(
    values: List<Float>,
    modifier: Modifier = Modifier,
    barSpacingDp: Int = 6,
    topColor: Color = AppColors.success,
    middleColor: Color = AppColors.accentPrimary,
    bottomColor: Color = AppColors.error,
) {
    val safeValues = values.map { it.coerceIn(0F, 100F) }
    val barCount = safeValues.size
    val anims = remember(values) { safeValues.map { Animatable(0f) } }

    LaunchedEffect(safeValues) {
        anims.forEach { it.snapTo(0f) }
        safeValues.forEachIndexed { idx, v ->
            val target = (v.coerceIn(0F, 100F) / 100f)
            launch {
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

        val spacing = barSpacingDp.dp.toPx()
        val totalSpacing = spacing * (barCount - 1).coerceAtLeast(0)
        val barWidth = (w - totalSpacing) / barCount

        val topPadding = 4.dp.toPx()
        val bottomPadding = 4.dp.toPx()
        val graphHeight = h - topPadding - bottomPadding


        fun colorForValue(fraction: Float): Color {
            val clamped = fraction.coerceIn(0f, 1f)
            return if (clamped < 0.5f) {
                lerp(bottomColor, middleColor, clamped * 2f)
            } else {
                lerp(middleColor, topColor, (clamped - 0.5f) * 2f)
            }
        }

        safeValues.forEachIndexed { i, v ->
            val x = i * (barWidth + spacing)
            val frac = anims.getOrNull(i)?.value ?: (v.coerceIn(0F, 10F) / 10f)
            val barHeight = frac * graphHeight
            val top = topPadding + (graphHeight - barHeight)

            drawRoundRect(
                color = colorForValue(frac),
                topLeft = Offset(x, top),
                size = androidx.compose.ui.geometry.Size(barWidth, barHeight),
                cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
            )

            drawRoundRect(
                color = Color.Black.copy(alpha = 0.06f),
                topLeft = Offset(x, top),
                size = androidx.compose.ui.geometry.Size(barWidth, barHeight),
                cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
                blendMode = BlendMode.SrcOver
            )
        }
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
    backgroundColor: Color = AppColors.backgroundLight,
    expandBackgroundColor: Color = AppColors.backgroundLight,
    borderColor: Color = AppColors.backgroundLight,
    textColor: Color = AppColors.textPrimary,
    titleColor: Color = AppColors.textPrimary,
    closedTitleColor: Color = AppColors.textPrimary,
    topIcon: Painter? = null,
    bottomIcon: Painter? = null,
    centerIcon: Painter? = null,
    topIconColor: Color = AppColors.accentPrimary,
    bottomIconColor: Color = AppColors.accentPrimary,
    centerIconColor: Color = AppColors.accentPrimary,
    borderWidth: Float = 0f,
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
            .background(if (expanded) expandBackgroundColor else backgroundColor)
            .border(
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(
                    color = borderColor,
                    width = borderWidth.dp
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
                titleColor = titleColor,
                textColor = textColor,
                onCancel = onCancel,
                onConfirm = onConfirm,
                onClose = { toggleExpand() },
                backgroundColor = expandBackgroundColor,
                content = content
            )
        } else {
            Box(
                Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (topIcon != null) {
                    Icon(
                        painter = topIcon,
                        contentDescription = "Active dot",
                        tint = topIconColor.copy(alpha = 0.5f),
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .size(250.dp)
                            .offset(-(100).dp, -(60).dp)
//                                        .padding(bottom = 36.dp)
                    )
                }

                if (bottomIcon != null) {
                    Icon(
                        painter = bottomIcon,
                        contentDescription = "Active dot",
                        tint = bottomIconColor.copy(alpha = 0.5f),
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(250.dp)
                            .offset(100.dp, 80.dp)
//                                        .padding(bottom = 36.dp)
                    )
                }

                if (centerIcon != null) {
                    Icon(
                        painter = centerIcon,
                        contentDescription = "Active dot",
                        tint = centerIconColor.copy(alpha = 0.5f),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(300.dp)
//                                        .padding(bottom = 36.dp)
                    )
                }

                Text(
                    textAlign = TextAlign.Center,
                    text = closedTitle,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = closedTitleColor,
                    modifier = Modifier
                        .background(
                            color = backgroundColor,
                            shape = RoundedCornerShape(25.dp)
                        )
                        .padding(vertical = 6.dp, horizontal = 8.dp)
                )
            }
        }
    }
}
@Composable
private fun ExpandableContent(
    expandedTitle: String,
    scrollState: ScrollState,
    titleColor: Color = AppColors.textPrimary,
    textColor: Color = AppColors.textPrimary,
    backgroundColor: Color = AppColors.backgroundLight,
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
            .background(backgroundColor)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .heightIn(min = 36.dp),
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

        HorizontalDivider(thickness = 0.5.dp, color = AppColors.accentPrimary)
        Spacer(Modifier.height(8.dp))

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
                            color = textColor,
                        )
                    }
                }
                onConfirm?.let {
                    TextButton(onClick = it) {
                        Text(
                            text = "ОК",
                            color = textColor,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FeedContentCard(
    content: SocialContent,
    modifier: Modifier = Modifier,
    onClick: (SocialContent) -> Unit = {}
) {

    val context = LocalContext.current

    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(12.dp))
                .background(AppColors.backgroundLight)
                .border(
                    shape = RoundedCornerShape(12.dp),
                    color = AppColors.surfaceLight, // divider
                    width = 0.2.dp
                )
                .blur(16.dp)
                .clickable { onClick(content) }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            when (content) {
                is SocialContent.Post -> {
                    Column {
                        PostAuthorHeader(content.author)

                        Spacer(Modifier.height(8.dp))
                        if (!content.image.isNullOrBlank()) {
                            Box {
                                AsyncImage(
                                    model = content.image,
                                    contentDescription = content.text,
                                    modifier = Modifier
                                        .height(350.dp)
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }

                is SocialContent.Ad -> {
                    Box {
                        AsyncImage(
                            model = content.image,
                            contentDescription = content.title,
                            modifier = Modifier
                                .height(300.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )

                        Text(
                            text = content.title,
                            color = AppColors.textAuto(Color.Black.copy(alpha = 0.3f)),
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomStart)
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            Color.Transparent,
                                            AppColors.backgroundDark.copy(alpha = 0.85f)
                                        )
                                    )
                                )
                                .padding(4.dp)
                                .basicMarquee(iterations = MAX_VALUE)
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            when (content) {
                is SocialContent.Post -> {
                    if (!content.text.isNullOrBlank()) {
                        Text(
                            text = content.text,
                            fontSize = 14.sp,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier,
                            color = AppColors.textPrimary
                        )
                    }
                }

                is SocialContent.Ad -> {
                    Text(
                        text = content.cta,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF007AFF)
                    )
                }
            }
        }
    }
}
@Composable
private fun PostAuthorHeader(user: User) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (!user.avatar.isNullOrBlank()) {
            AsyncImage(
                model = user.avatar,
                contentDescription = user.username,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.width(8.dp))
        }

        Column {
            Text(
                text = user.username + stringResource(R.string.user_lvl_header, user.level),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = AppColors.textPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (!user.about.isNullOrBlank()) {
                Text(
                    text = user.about,
                    fontSize = 12.sp,
                    color = AppColors.textSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}




@Composable
fun DynamicDoubleColumn(
    useScroll: Boolean = false,
    spacing: Dp = 4.dp,
    paddingHorizontal: Dp = 0.dp,
    paddingVertical: Dp = 0.dp,
//    isCenteredHor: Boolean,
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

