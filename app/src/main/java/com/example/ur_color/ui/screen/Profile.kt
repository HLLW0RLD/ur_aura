package com.example.ur_color.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.ur_color.ui.ExpandableFloatingBox
import com.example.ur_color.ui.FeedContentCard
import com.example.ur_color.ui.screen.viewModel.ProfileViewModel
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.AppScaffold
import com.example.ur_color.utils.LocalNavController
import com.example.ur_color.utils.feedCards
import com.example.ur_color.utils.profileCards
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
                showOptions = true,
                optionsIcon = painterResource(R.drawable.switcher_options),
                onOptionsClick = {
                    navController.nav(Settings)
                },
                isCentered = true,
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
                    rememberAsyncImagePainter("https://picsum.photos/seed/abstract02/600/600")
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
                    Text(
                        color = AppColors.textPrimary,
                        text = u.about ?: "Default about text, cant change.",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    // dropdown with full user info
                    // ${calculateAge(u.birthDate)}
                }
            }

            Spacer(Modifier.height(16.dp))

            ExpandableFloatingBox(
                closedTitle = stringResource(R.string.profile_more),
                expandedTitle = stringResource(R.string.prrofile_other_info),
                canShowFull = false,
                expandHeight = 170f,
                backgroundColor = AppColors.surfaceLight,
                height = 56f,
                modifier = Modifier,
            ) {
                Column {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        color = AppColors.white,
                        text = stringResource(R.string.profile_aura_details) + " (premium)",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.nav(AuraDetails())
                            }
                            .padding(8.dp),
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        color = AppColors.white,
                        text = stringResource(R.string.profile_aura_achievement) + " (premium)",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                            }
                            .padding(8.dp),
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        AppColors.backgroundDark
                            .copy(alpha = 0.2f)
                    )
                    .padding(vertical = 16.dp)
            ) {
                profileCards.forEach {
                    item {
                        FeedContentCard(
                            modifier = Modifier
//                                .heightIn(max = 400.dp)
                                .padding(4.dp),
                            content = it,
                            onClick = { }
                        )
                    }
                }
            }
        } ?: run {
            Text(stringResource(R.string.profile_no_user), style = MaterialTheme.typography.bodyMedium)
        }
    }
}