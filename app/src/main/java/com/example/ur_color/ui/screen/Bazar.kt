package com.example.ur_color.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.ur_color.R
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.ui.screen.viewModel.BazarViewModel
import com.example.ur_color.ui.screen.viewModel.CustomTestViewModel
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.AppScaffold
import com.example.ur_color.utils.LocalNavController
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data object Bazar: Screen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Bazar(bazar: Bazar) {
    AppScaffold(
        showBottomBar = true,
        topBar = {
            val navController = LocalNavController.current

            CustomAppBar(
                title = stringResource(R.string.bottom_menu_bazar),
                isCentered = true,
//                showOptions = true,
//                optionsIcon = painterResource(R.drawable.magic_stick_sparckles),
                backgroundColor = AppColors.background,
            )
        }
    ) {
        BazarScreen(
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun BazarScreen(
    modifier : Modifier = Modifier,
    bazarViewModel: BazarViewModel = koinViewModel(),
) {

}