package com.example.ur_color.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.ur_color.R
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.ui.screen.viewModel.ProfileViewModel
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.AppScaffold
import com.example.ur_color.utils.LocalNavController
import com.example.ur_color.utils.toast
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data class Profile(val user: String = "null") : Screen

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun Profile(profile : Profile) {
    AppScaffold(
        showBottomBar = true,
        topBar = {
            val navController = LocalNavController.current

            CustomAppBar(
                title = stringResource(R.string.profile_title),
                showBack = true,
                onBackClick = {
                    navController.popBack()
                },
                showOptions = true,
                optionsIcon = painterResource(R.drawable.switcher_options),
                onOptionsClick = {
                    navController.nav(Settings)
                },
                isCentered = false,
                backgroundColor = AppColors.background,
            )
        },
    ) {
        ProfileScreen(
            modifier = Modifier.padding(it)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun ProfileScreen(
    modifier : Modifier = Modifier
) {

    val navController = LocalNavController.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val profileViewModel: ProfileViewModel = koinViewModel()
    val user by profileViewModel.user.collectAsState()
    val level by profileViewModel.level.collectAsState()
    val isDailyTestAvailable by profileViewModel.isDailyTestAvailable.collectAsState()

    LaunchedEffect(Unit) {
        profileViewModel.checkDailyTestAvailability(context)
    }

    LaunchedEffect(Unit) {
        profileViewModel.init(context)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppColors.background)
    ) {
        user?.let { u ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val avatarPainter = if (u.avatarUri != null) {
                    rememberAsyncImagePainter(u.avatarUri)
                } else {
                    rememberAsyncImagePainter(android.R.drawable.sym_def_app_icon)
                }

                Image(
                    painter = avatarPainter,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                )

                Spacer(Modifier.width(16.dp))

                Column {
                    Text(
                        color = AppColors.textPrimary,
                        text = stringResource(
                            R.string.profile_name_level,
                            u.firstName,
                            level?.toInt() ?: 1
                        ),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        color = AppColors.textPrimary,
                        text = stringResource(
                            R.string.profile_zodiac,
                            u.zodiacSign.lowercase()
                        ),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    // dropdown with full user info
                    // ${calculateAge(u.birthDate)}
                }
            }

            Spacer(Modifier.height(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                val s = stringResource(R.string.profile_daily_test_done)
                Text(
                    color = AppColors.textPrimary,
                    text = stringResource(R.string.profile_daily_tests),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.nav(DailyTest)
//                            if (isDailyTestAvailable) {
//                                navController.nav(DailyTest)
//                            } else {
//
//                                context.toast(s)
//                            }
                        }
                        .padding(8.dp)
                )
                Text(
                    color = AppColors.textPrimary,
                    text = stringResource(R.string.profile_personal_tests),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        }
                        .padding(8.dp)
                )
                Text(
                    color = AppColors.textPrimary,
                    text = stringResource(R.string.profile_aura_details) + " (premium)",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.nav(AuraDetails())
                        }
                        .padding(8.dp)
                )
                Text(
                    color = AppColors.textPrimary,
                    text = stringResource(R.string.profile_diary) + " (premium)",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        }
                        .padding(8.dp)
                )
                Text(
                    color = AppColors.textPrimary,
                    text = stringResource(R.string.profile_settings),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.nav(Settings)
                        }
                        .padding(8.dp)
                )
                Text(
                    stringResource(R.string.profile_logout),
                    style = MaterialTheme.typography.bodyLarge,
                    color = AppColors.textPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            scope.launch {
                                profileViewModel.deleteUser(context)
                                navController.nav(Login, true)
                            }
                        }
                        .padding(8.dp)
                )
                Text(
                    stringResource(R.string.profile_delete),
                    style = MaterialTheme.typography.bodyLarge,
                    color = AppColors.error,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        }
                        .padding(8.dp)
                )
            }
        } ?: run {
            Text(stringResource(R.string.profile_no_user), style = MaterialTheme.typography.bodyMedium)
        }
    }
}