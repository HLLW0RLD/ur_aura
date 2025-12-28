package com.example.ur_color

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.ui.screen.AuraDetails
import com.example.ur_color.ui.screen.BottomBarState
import com.example.ur_color.ui.screen.Login
import com.example.ur_color.ui.screen.Main
import com.example.ur_color.ui.screen.Profile
import com.example.ur_color.ui.screen.DailyTest
import com.example.ur_color.ui.screen.LocalBottomBarState
import com.example.ur_color.ui.screen.Screen
import com.example.ur_color.ui.screen.Settings
import com.example.ur_color.ui.screen.Tests
import com.example.ur_color.ui.screen.animatedScreenComposable
import com.example.ur_color.ui.screen.baseRoute
import com.example.ur_color.ui.screen.nav
import com.example.ur_color.ui.screen.popBack
import com.example.ur_color.ui.screen.route
import com.example.ur_color.ui.theme.AppColors
import com.example.ur_color.ui.theme.AppTheme
import com.example.ur_color.utils.LocalNavController
import org.w3c.dom.Text

const val SCREEN_DATA = "{json}"
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val context = LocalContext.current

                val navController = rememberNavController()
                val bottomBarState = remember { BottomBarState() }

                var startRoute by remember { mutableStateOf<Screen?>(null) } // или тип Any для data class
                var isInitialized by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    val user = PersonalDataManager.getUser(context = context)
                    startRoute = if (user != null) Main else Login
                    isInitialized = true
                }

                if (!isInitialized) return@AppTheme

                CompositionLocalProvider(
                    LocalNavController provides navController,
                            LocalBottomBarState provides bottomBarState
                ) {
                    Box(
                        Modifier.fillMaxSize(),
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = startRoute!!.route()
                        ) {
                            animatedScreenComposable<Login>(
                                navController = navController,
                            ) { Login(it) }
                            animatedScreenComposable<Main>(
                                navController = navController,
                            ) { Main(it) }
                            animatedScreenComposable<Profile>(
                                navController = navController,
                            ) { Profile(it) }
                            animatedScreenComposable<DailyTest>(
                                navController = navController,
                            ) { DailyTest(it) }
                            animatedScreenComposable<Tests>(
                                navController = navController,
                            ) { Tests(it) }
                            animatedScreenComposable<AuraDetails>(
                                navController = navController,
                            ) { AuraDetails(it) }
                            animatedScreenComposable<Settings>(
                                navController = navController,
                            ) { Settings(it) }
                        }

                        AnimatedVisibility(
                            visible = bottomBarState.enabled && bottomBarState.visible,
                            enter = slideInVertically { it } + fadeIn(),
                            exit = slideOutVertically { it } + fadeOut(),
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                        ) {
                            AppBottomNavigation()
                        }
                    }
                }
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun AppBottomNavigation(
    modifier: Modifier = Modifier
) {
    val navController = LocalNavController.current

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route?.substringBefore('/')

    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
            .background(
                color = AppColors.background.copy(alpha = 0.8f),
                shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)
            )
            .border(
                shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp),
                color = AppColors.surfaceLight, // surfaceLight
                width = 1.dp
            )
            .height(72.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        BottomNavItem(
            modifier = Modifier
                .weight(1f),
            icon = R.drawable.ball_crystal,
            text = stringResource(R.string.bottom_menu_main),
            selected = currentRoute == Main.baseRoute(),
            onClick = { navController.popBack(Main) }
        )

        BottomNavItem(
            modifier = Modifier
                .weight(1f),
            icon = R.drawable.card_trick,
            text = stringResource(R.string.bottom_menu_tests),
            selected = currentRoute == Tests.baseRoute(),
            onClick = { navController.nav(Tests) }
        )

        BottomNavItem(
            modifier = Modifier
                .weight(1f),
            icon = R.drawable.illusion_eye,
            text = stringResource(R.string.bottom_menu_profile),
            selected = currentRoute == Profile().baseRoute(),
            onClick = { navController.nav(Profile()) }
        )
    }
}

@Composable
fun BottomNavItem(
    text: String,
    @DrawableRes icon: Int,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clickable {
                onClick()
        },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = if (selected)
                AppColors.accentPrimary
            else
                AppColors.textSecondary
        )
        Text(
            text = text,
            color = if (selected)
                AppColors.accentPrimary
            else
                AppColors.textSecondary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Thin
        )
    }
}