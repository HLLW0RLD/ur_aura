package com.example.ur_color.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ur_color.R
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.ui.ExpandableFloatingBox
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.AppScaffold
import com.example.ur_color.utils.LocalNavController
import kotlinx.serialization.Serializable

@Serializable
data object Tests: Screen

@Composable
fun Tests(tests: Tests) {
    AppScaffold(
        showBottomBar = true,
        topBar = {
            val navController = LocalNavController.current

            CustomAppBar(
                title = stringResource(R.string.profile_tests),
                isCentered = true,
                backgroundColor = AppColors.background,
            )
        }
    ) {
        TestScreen(
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun TestScreen(
    modifier : Modifier = Modifier
) {
    val navController = LocalNavController.current

    val s = stringResource(R.string.profile_daily_test_done)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ExpandableFloatingBox(
            closedTitle = stringResource(R.string.profile_daily_tests),
            expandedTitle = stringResource(R.string.profile_daily_tests),
            canShowFull = false,
            expandHeight = 250f,
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

        ExpandableFloatingBox(
            closedTitle = stringResource(R.string.profile_personal_tests),
            expandedTitle = stringResource(R.string.profile_personal_tests),
            canShowFull = false,
            expandHeight = 250f,
            onConfirm = {

            }
        ) {
            Text(stringResource(R.string.personal_tests_description))
        }
    }

}