package com.example.ur_color.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ur_color.R
import com.example.ur_color.data.model.AuraItem
import com.example.ur_color.data.model.AuraItemType
import com.example.ur_color.data.model.AuraRowConfig
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.ui.ExpandableFloatingBox
import com.example.ur_color.ui.screen.viewModel.LabViewModel
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.AppScaffold
import com.example.ur_color.ui.theme.toColor
import com.example.ur_color.utils.LocalNavController
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data object Lab: Screen

@Composable
fun Lab(lab: Lab) {
    AppScaffold(
        showBottomBar = true,
        topBar = {
            val navController = LocalNavController.current

            CustomAppBar(
                title = stringResource(R.string.bottom_menu_lab),
                isCentered = true,
                showOptions = true,
                optionsIcon = painterResource(R.drawable.magic_stick_sparckles),
                backgroundColor = AppColors.background,
            )
        }
    ) {
        LabScreen(
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun LabScreen(
    modifier : Modifier = Modifier,
    labViewModel: LabViewModel = koinViewModel()
) {
    val navController = LocalNavController.current

    val auraSectionsState by labViewModel.auraSectionsState.collectAsState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(auraSectionsState) { section ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp))
                    .background(AppColors.backgroundDark.copy(alpha = 0.2f))
            ) {
                SectionHeader(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    title = section.sectionTitle,
                    onMoreClick = { }
                )

                AuraExpandableRow(
                    config = section.rowConfig,
                    items = section.items,
                    onConfirm = {
                        when (it) {
                            /*

                            получаем json список AuraSection с заполненными заголовками, описанием, типом и айдишником у каждого AuraItem
                            и парсим для экрана вместе AuraRowConfig(не пприходят с сервера)
                            по клику мы переходим на экран-контеййнер соответствующийй типу контента и запрашиваем его по айди контента(теста, совместимости )

                            ежедднневные тесты не будут приходить если они пройдены
                            */

                            AuraItemType.PSYCHOLOGY_TEST ->
                                navController.nav(DailyTest/*(PSYCHOLOGY_DAILY_TEST, "1212-asdf-234")*/)    // это общий контейнер для вопросов который принимает
                                                                                                                     // тип и айди чтобы отправить их длля полученнния контента

                            AuraItemType.COMPATIBILITY -> {
//                                navController.nav(CompatibilityScreen)
                            }

                            AuraItemType.HOROSCOPE -> {
//                                navController.nav(HoroscopeScreen)
                            }

                            AuraItemType.DIVINATION -> {
//                                navController.nav(DivinationScreen)
                            }

                            AuraItemType.COURSE -> {
//                                navController.nav(CourseListScreen)
                            }
                        }
                    }
                )
            }
        }

        item {
            Spacer(Modifier.size(36.dp))
        }
    }
}

@Composable
fun AuraExpandableRow(
    config: AuraRowConfig,
    items: List<AuraItem>,
    onConfirm: (AuraItemType) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        itemsIndexed(items) { index, i ->
            Box(
                modifier = Modifier.width(350.dp)
            ) {
                ExpandableFloatingBox(
                    closedTitle = i.title,
                    expandedTitle = i.title,
                    canShowFull = false,
                    expandHeight = 250f,
                    height = 200f,
                    width = 250f,
                    expandWidth = 350f,
                    topIconColor = config.color.toColor(),
                    bottomIconColor = config.color.toColor(),
                    topIcon = if (config.topIconRes != null) painterResource(config.topIconRes) else null,
                    centerIcon = if (config.centerIconRes != null) painterResource(config.centerIconRes) else null,
                    bottomIcon = if (config.bottomIconRes != null) painterResource(config.bottomIconRes) else null,

                    onConfirm = { onConfirm(i.type) }
                ) {
                    Text(i.description)
                }
            }
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            style = MaterialTheme.typography.labelMedium,
            color = AppColors.textPrimary
        )
        TextButton(
            onClick = onMoreClick,
            modifier = Modifier.padding(end = 8.dp)
        ) {
            Text(
                text = stringResource(R.string.show_more),
                color = AppColors.accentPrimary,
            )
        }
    }
}

