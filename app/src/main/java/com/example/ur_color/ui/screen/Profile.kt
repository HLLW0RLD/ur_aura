package com.example.ur_color.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ur_color.R
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.ui.ExpandableFloatingBox
import com.example.ur_color.ui.FeedContentCard
import com.example.ur_color.ui.screen.viewModel.ProfileViewModel
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.AppScaffold
import com.example.ur_color.ui.theme.AuraColors
import com.example.ur_color.ui.theme.toColor
import com.example.ur_color.utils.LocalNavController
import com.example.ur_color.utils.animPic
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Serializable
data class Profile(val user: String = "null") : Screen

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun Profile(profile : Profile) {
    AppScaffold(
        showBottomBar = true,
    ) {
        val profileViewModel: ProfileViewModel = koinViewModel()
        val user by profileViewModel.user.collectAsState()

        ProfileScreen(
            user = user
//            modifier = Modifier.padding(it)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun ProfileScreen(
    user: UserData?,
    modifier : Modifier = Modifier,
    profileViewModel: ProfileViewModel = koinViewModel()
) {

    val navController = LocalNavController.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()


    val level by profileViewModel.level.collectAsState()
    val isDailyTestAvailable by profileViewModel.isDailyTestAvailable.collectAsState()
    val profileCardsState by profileViewModel.profileCardsState.collectAsState()

    val color = AppColors.backgroundDark

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

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                item {
                    Spacer(Modifier.height(8.dp))
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(25.dp))
                            .background(color.copy(alpha = 0.2f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                            ,
                            verticalAlignment = Alignment.Top
                        ) {
                            val avatarPainter = if (u.avatarUri != null) {
                                u.avatarUri
                            } else {
                                "https://picsum.photos/seed/abstract02/600/600"
                            }

                            AsyncImage(
                                model = avatarPainter,
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(130.dp)
                                    .clip(CircleShape)
                            )

                            Spacer(Modifier.width(8.dp))

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
                                var expanded by remember { mutableStateOf(false) }
                                Text(
                                    color = AppColors.textPrimary,
                                    text = u.about ?: "Default about text, cant change.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    maxLines = if (expanded) Int.MAX_VALUE else 3,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .clickable(
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() }
                                        ) {
                                            expanded = !expanded
                                        }
                                )

                                // dropdown with full user info
                                // ${calculateAge(u.birthDate)}
                            }
                        }

                        Spacer(Modifier.height(16.dp))

                        Text(
                            color = AppColors.textAuto(AppColors.surfaceLight),
                            text = stringResource(R.string.profile_aura_achievement),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {

                                }
                                .padding(bottom = 4.dp, start = 16.dp),
                        )
                        LazyRow(
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                                .height(48.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            item {
                                Spacer(Modifier.size(8.dp))
                            }
                            items(animPic.size) {
                                val pic = animPic.shuffled()[it]
                                val ind = AuraColors.values().toList().shuffled().first()

                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .background(
                                            color = ind.toColor(),
                                            shape = CircleShape
                                        )
                                        .clip(CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        modifier = Modifier
                                            .size(36.dp),
                                        painter = painterResource(pic),
                                        contentDescription = ""
                                    )
                                }
                            }
                        }

                        ExpandableFloatingBox(
                            closedTitle = stringResource(R.string.profile_more),
                            expandedTitle = stringResource(R.string.prrofile_other_info),
                            canShowFull = true,
                            expandHeight = 200f,
                            backgroundColor = AppColors.surface,
                            expandBackgroundColor = AppColors.surface,
                            borderColor = AppColors.surface,
                            closedTitleColor = AppColors.textPrimary,
                            borderWidth = 2f,
                            height = 80f,
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Top
                            ) {

                                Spacer(Modifier.height(4.dp))
                                Text(
                                    color = AppColors.textAuto(color),
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
                                    color = AppColors.textAuto(color),
                                    text = stringResource(R.string.profile_life_goals) + " (premium)",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            navController.nav(AuraDetails())
                                        }
                                        .padding(8.dp),
                                )
                            }
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                }

                item {
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(25.dp))
                            .background(
                                color
                                    .copy(alpha = 0.2f)
                            )
                    ) {
                        profileCardsState.forEach {
                            FeedContentCard(
                                modifier = Modifier
//                                .heightIn(max = 400.dp)
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                content = it,
                                onClick = { }
                            )
                        }
                    }
                }
            }
        } ?: run {
            Text(stringResource(R.string.profile_no_user), style = MaterialTheme.typography.bodyMedium)
        }
    }
}