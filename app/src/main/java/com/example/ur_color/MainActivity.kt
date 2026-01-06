package com.example.ur_color

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.ur_color.data.local.dataManager.PersonalDataManager
import com.example.ur_color.ui.screen.AuraDetails
import com.example.ur_color.ui.screen.BottomBarState
import com.example.ur_color.ui.screen.Login
import com.example.ur_color.ui.screen.Main
import com.example.ur_color.ui.screen.Profile
import com.example.ur_color.ui.screen.DailyTest
import com.example.ur_color.ui.screen.EditProfile
import com.example.ur_color.ui.screen.TabsHost
import com.example.ur_color.ui.screen.TabsHostScreen
import com.example.ur_color.ui.screen.LocalBottomBarState
import com.example.ur_color.ui.screen.Screen
import com.example.ur_color.ui.screen.Settings
import com.example.ur_color.ui.screen.Lab
import com.example.ur_color.ui.screen.animatedScreenComposable
import com.example.ur_color.ui.screen.route
import com.example.ur_color.ui.theme.AppTheme
import com.example.ur_color.utils.LocalNavController

const val SCREEN_DATA = "{json}"
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
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
                    startRoute = if (user != null) TabsHost else Login
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
                            // хост экран для табов
                            animatedScreenComposable<TabsHost>(navController) {
                                TabsHostScreen()
                            }

                            animatedScreenComposable<Login>(
                                navController = navController,
                            ) { Login(it) }
                            animatedScreenComposable<DailyTest>(
                                navController = navController,
                            ) { DailyTest(it) }
                            animatedScreenComposable<AuraDetails>(
                                navController = navController,
                            ) { AuraDetails(it) }
                            animatedScreenComposable<Settings>(
                                navController = navController,
                            ) { Settings(it) }
                            animatedScreenComposable<EditProfile>(
                                navController = navController,
                            ) { EditProfile(it) }
                        }
                    }
                }
            }
        }
    }
}