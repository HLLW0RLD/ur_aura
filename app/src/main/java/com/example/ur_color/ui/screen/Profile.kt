package com.example.ur_color.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ur_color.R
import com.example.ur_color.data.model.user.UserData
import com.example.ur_color.data.model.user.toUser
import com.example.ur_color.ui.CustomAppBar
import com.example.ur_color.ui.ExpandableFloatingBox
import com.example.ur_color.ui.FeedContentCard
import com.example.ur_color.ui.screen.bottomSheet.CreatePostBottomSheet
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

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun ProfileScreen(
    user: UserData?,
    modifier : Modifier = Modifier,
    profileViewModel: ProfileViewModel = koinViewModel()
) {

    val navController = LocalNavController.current
    val context = LocalContext.current

    val profileCardsState by profileViewModel.profileCardsState.collectAsState()

    var showBottomSheet by remember { mutableStateOf(false) }
    val color = AppColors.backgroundDark

    val scrollState = rememberLazyListState()
    val scrollThreshold = 24f
    var accumulatedScroll by remember { mutableFloatStateOf(0f) }

    var extended by remember { mutableStateOf(true) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (source != NestedScrollSource.Drag) return Offset.Zero
                accumulatedScroll += available.y

                when {
                    accumulatedScroll > scrollThreshold &&
                            !extended -> {
                        extended = true
                        accumulatedScroll = 0f
                    }

                    accumulatedScroll < -scrollThreshold &&
                            extended -> {
                        extended = false
                        accumulatedScroll = 0f
                    }
                }

                return Offset.Zero
            }

            override suspend fun onPostFling(
                consumed: Velocity,
                available: Velocity
            ): Velocity {
                accumulatedScroll = 0f
                return Velocity.Zero
            }
        }
    }

    Column(
        modifier = modifier
            .nestedScroll(nestedScrollConnection)
            .fillMaxSize()
            .background(AppColors.background)
    ) {
        user?.let { u ->
            Column(
                modifier = modifier
                    .statusBarsPadding()
            ) {
                AnimatedVisibility(
                    visible = !extended,
                ) {
                    Row(
                        modifier = Modifier
                            .statusBarsPadding()
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val avatarPainter = if (u.avatarUri != null) {
                            u.avatarUri
                        } else {
                            "https://picsum.photos/seed/abstract02/600/600"
                        }

                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            AsyncImage(
                                model = avatarPainter,
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                color = AppColors.textPrimary,
                                text = u.nickName,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }

                        IconButton(
                            onClick = {
                                navController.nav(Settings)
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.switcher_options),
                                contentDescription = "",
                                tint = AppColors.icon
                            )
                        }
                    }
                }

                AnimatedVisibility(
                    visible = extended
                ) {
                    Column {
                        CustomAppBar(
                            title = stringResource(R.string.profile_title),
                            showOptions = true,
                            optionsIcon = painterResource(R.drawable.switcher_options),
                            onOptionsClick = {
                                navController.nav(Settings)
                            },
                            isCentered = true,
                            showDivider = true,
                            backgroundColor = AppColors.background,
                        )

                        Spacer(Modifier.height(16.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val avatarPainter = if (u.avatarUri != null) {
                                u.avatarUri
                            } else {
                                "https://picsum.photos/seed/abstract02/600/600"
                            }

                            Row(
                                modifier = Modifier
                            ) {
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
                                            u.nickName,
                                            u.userLevel
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
                                    var isOverflowing by remember { mutableStateOf(false) }
                                    Column(
                                        modifier = Modifier
                                            .animateContentSize()
                                    ) {
                                        Text(
                                            text = u.about.orEmpty(),
                                            color = AppColors.textPrimary,
                                            style = MaterialTheme.typography.bodyMedium,
                                            maxLines = if (expanded) Int.MAX_VALUE else 5,
                                            overflow = TextOverflow.Ellipsis,
                                            onTextLayout = { textLayoutResult ->
                                                isOverflowing = textLayoutResult.hasVisualOverflow
                                            }
                                        )

                                        if (isOverflowing || expanded) {
                                            Spacer(Modifier.height(4.dp))

                                            Text(
                                                text = if (expanded) "скрыть" else "ещё",
                                                style = MaterialTheme.typography.labelMedium,
                                                color = AppColors.accentPrimary,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .clickable(
                                                        indication = null,
                                                        interactionSource = remember { MutableInteractionSource() }
                                                    ) {
                                                        expanded = !expanded
                                                    }
                                            )
                                        }
                                    }

                                    // dropdown with full user info
                                    // ${calculateAge(u.birthDate)}
                                }
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(25.dp))
                            .background(color.copy(alpha = 0.2f))
                            .padding(vertical = 16.dp)
                    ) {
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

                        Row(
                            modifier = Modifier
                                .padding(end = 12.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.Absolute.Center
                        ) {
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
                                modifier = Modifier
                                    .weight(2.5f)
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

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .size(80.dp)
                                    .background(
                                        color = AppColors.accentPrimary,
                                        shape = RoundedCornerShape(20.dp)
                                    )
                                    .clickable {
                                        showBottomSheet = true
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(36.dp),
                                    painter = painterResource(R.drawable.card_trick),
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .clip(RoundedCornerShape(25.dp))
                            .background(color.copy(alpha = 0.2f))
                            .padding(vertical = 12.dp)
                    ) {
                        if (!profileCardsState.isNullOrEmpty()) {
                            profileCardsState?.forEach {
                                FeedContentCard(
                                    modifier = Modifier
//                                .heightIn(max = 400.dp)
                                        .padding(horizontal = 16.dp, vertical = 8.dp),
                                    content = it,
                                    onClick = { }
                                )
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .clickable(
                                        indication = null,
                                        interactionSource = null
                                    ) {
                                        showBottomSheet = true
                                    },
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    modifier = Modifier
                                        .size(250.dp),
                                    painter = painterResource(R.drawable.cauldron_potion),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(AppColors.accentPrimary)
                                )
                                Text(
                                    color = AppColors.textAuto(color),
                                    text = "Напишите первый  пост!",
                                    fontSize = 24.sp,
                                    style = MaterialTheme.typography.bodyLarge,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(24.dp),
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(Modifier.height(24.dp))
                }
            }
        } ?: run {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    stringResource(R.string.profile_no_user),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        if (showBottomSheet) {
            CreatePostBottomSheet(
                currentUser = user.toUser(),
                onDismiss = { showBottomSheet = false },
            )
        }
    }
}