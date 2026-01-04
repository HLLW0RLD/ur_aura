package com.example.ur_color.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ur_color.R
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.ui.DynamicDoubleColumn
import com.example.ur_color.ui.ExpandableBox
import com.example.ur_color.ui.ExpandableFloatingBox
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.AppScaffold
import com.example.ur_color.utils.LocalNavController
import kotlinx.serialization.Serializable

@Serializable
data object Lab: Screen

@Composable
fun Lab(Lab: Lab) {
    AppScaffold(
        showBottomBar = true,
        topBar = {
            val navController = LocalNavController.current

            CustomAppBar(
                title = stringResource(R.string.profile_lab),
                isCentered = true,
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
    modifier : Modifier = Modifier
) {
    val navController = LocalNavController.current

    val tabTitles = listOf("Психология", "Изотерика")
    var selectedTab by remember { mutableStateOf(0) }

    val s = stringResource(R.string.profile_daily_test_done)

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = AppColors.background,
            contentColor = AppColors.accentPrimary
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }
        Spacer(Modifier.height(16.dp))
        when (selectedTab) {
            0 -> PsychologyTabContent()
            1 -> EsotericsTabContent()
        }
    }
}

@Composable
fun PsychologyTabContent() {
    val navController = LocalNavController.current

    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        ExpandableFloatingBox(
            closedTitle = stringResource(R.string.profile_daily_tests),
            expandedTitle = stringResource(R.string.profile_daily_tests),
            canShowFull = false,
            expandHeight = 250f,
            height = 100f,
            onConfirm = {
                navController.nav(DailyTest)
//                            if (isDailyTestAvailable) {
//                                navController.nav(DailyTest)
//                            } else {
//
//                                context.toast(s)
//                            }
            }
        ) {
            Text(stringResource(R.string.daily_tests_description))
        }

        Spacer(Modifier.height(16.dp))

        ExpandableFloatingBox(
            closedTitle = stringResource(R.string.profile_personal_tests),
            expandedTitle = stringResource(R.string.profile_personal_tests),
            canShowFull = false,
            expandHeight = 250f,
            height = 100f,
            onConfirm = { /* действие */ }
        ) {
            Text(stringResource(R.string.personal_tests_description))
        }
    }
}

@Composable
fun EsotericsTabContent() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        ExpandableFloatingBox(
            closedTitle = "Совместимость",
            expandedTitle = "Совместимость",
            canShowFull = false,
            expandHeight = 250f,
            height = 100f,
            onConfirm = { /* действие */ }
        ) {
            Text("Совместимость по именам")
        }
        Spacer(Modifier.height(16.dp))

        ExpandableFloatingBox(
            closedTitle = "Гороскоп",
            expandedTitle = "Гороскоп",
            canShowFull = false,
            expandHeight = 250f,
            height = 100f,
            onConfirm = { /* действие */ }
        ) {
            Text("Расширенный гороскоп")
        }
        Spacer(Modifier.height(16.dp))

        ExpandableFloatingBox(
            closedTitle = "Гадания",
            expandedTitle = "Гадания",
            canShowFull = false,
            expandHeight = 250f,
            height = 100f,
            onConfirm = { /* действие */ }
        ) {
            Text("Гадания по темам")
        }
        Spacer(Modifier.height(16.dp))

        ExpandableFloatingBox(
            closedTitle = "Магия",
            expandedTitle = "Магия",
            canShowFull = false,
            expandHeight = 250f,
            height = 100f,
            onConfirm = { /* действие */ }
        ) {
            Text("Число дня")
        }
    }
}