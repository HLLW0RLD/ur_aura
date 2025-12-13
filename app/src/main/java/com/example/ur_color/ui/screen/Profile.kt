package com.example.ur_color.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.ui.screen.viewModel.ProfileViewModel
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.utils.LocalNavController
import com.example.ur_color.utils.toast
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data class Profile(val user: String = "null") : Screen

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun ProfileScreen() {

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.background)
    ) {
        CustomAppBar(
            title = "Your profile",
            showBack = true,
            onBackClick = {
                navController.popBack()
            },
            isCentered = false,
            backgroundColor = AppColors.background,
        )

        user?.let { u ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Круглый аватар (по uri или placeholder)
                val avatarPainter = if (u.avatarUri != null) {
                    rememberAsyncImagePainter(u.avatarUri)
                } else {
                    rememberAsyncImagePainter(android.R.drawable.sym_def_app_icon)
                }

                Image(
                    painter = avatarPainter,
                    contentDescription = "Аватар профиля",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                )

                Spacer(Modifier.width(16.dp))

                Column {
                    Text(
                        color = AppColors.textPrimary,
                        text = "${u.firstName}, ${level?.toInt() ?: 1} уровень",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        color = AppColors.textPrimary,
                        text = "${u.zodiacSign.lowercase()}",
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
                Text(
                    color = AppColors.textPrimary,
                    text = "Aura Details",
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
                    text = "Daily Tests",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isDailyTestAvailable) {
                                navController.nav(DailyTest)
                            } else {
                                context.toast("Тест уже пройден")
                            }
                        }
                        .padding(8.dp)
                )
                Text(
                    color = AppColors.textPrimary,
                    text = "Personal Tests",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        }
                        .padding(8.dp)
                )
                Text(
                    color = AppColors.textPrimary,
                    text = "Diary",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        }
                        .padding(8.dp)
                )
                Text(
                    color = AppColors.textPrimary,
                    text = "Settings",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.nav(Settings)
                        }
                        .padding(8.dp)
                )
                Text(
                    "Выйти из профиля",
                    style = MaterialTheme.typography.bodyLarge,
                    color = AppColors.textPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            scope.launch {
                                profileViewModel.deleteUser(context)
                                // например, вернуться на экран логина
                                navController.nav(Login, true)
                            }
                        }
                        .padding(8.dp)
                )
                Text(
                    "Удалить профиль",
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
            Text("Нет данных пользователя", style = MaterialTheme.typography.bodyMedium)
        }
    }
}