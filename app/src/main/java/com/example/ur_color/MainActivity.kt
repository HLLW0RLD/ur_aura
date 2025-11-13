package com.example.ur_color

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.ur_color.data.local.dataManager.UserDataManager
import com.example.ur_color.ui.screen.AuraDetails
import com.example.ur_color.ui.screen.AuraDetailsScreen
import com.example.ur_color.ui.screen.Direction
import com.example.ur_color.ui.screen.Login
import com.example.ur_color.ui.screen.LoginScreen
import com.example.ur_color.ui.screen.Main
import com.example.ur_color.ui.screen.MainScreen
import com.example.ur_color.ui.screen.Profile
import com.example.ur_color.ui.screen.ProfileScreen
import com.example.ur_color.ui.screen.DailyTest
import com.example.ur_color.ui.screen.DailyTestScreen
import com.example.ur_color.ui.screen.Screen
import com.example.ur_color.ui.screen.animatedScreenComposable
import com.example.ur_color.ui.screen.route
import com.example.ur_color.ui.theme.AppTheme
import com.example.ur_color.utils.LocalNavController
import kotlinx.coroutines.flow.firstOrNull

const val SCREEN_DATA = "{json}"
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()
                val context = LocalContext.current

                var startRoute by remember { mutableStateOf<Screen?>(null) } // или тип Any для data class
                var isInitialized by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    val user = UserDataManager.user.firstOrNull()
                    startRoute = if (user != null) Main else Login
                    isInitialized = true
                }

                if (!isInitialized) return@AppTheme


                CompositionLocalProvider(LocalNavController provides navController) {
                    NavHost(
                        navController = navController,
                        startDestination = startRoute!!.route()
                    ) {
                        animatedScreenComposable(
                            navController = navController,
                            screenClass = Login::class,
                            enterFrom = Direction.RIGHT,
                            exitTo = Direction.RIGHT
                        ) { LoginScreen() }
                        animatedScreenComposable(
                            navController = navController,
                            screenClass = Main::class,
                            enterFrom = Direction.RIGHT,
                            exitTo = Direction.RIGHT
                        ) { MainScreen() }
                        animatedScreenComposable(
                            navController = navController,
                            screenClass = AuraDetails::class,
                            enterFrom = Direction.RIGHT,
                            exitTo = Direction.RIGHT
                        ) { AuraDetailsScreen(it) }
                        animatedScreenComposable(
                            navController = navController,
                            screenClass = Profile::class,
                            enterFrom = Direction.RIGHT,
                            exitTo = Direction.RIGHT
                        ) { ProfileScreen() }
                        animatedScreenComposable(
                            navController = navController,
                            screenClass = DailyTest::class,
                            enterFrom = Direction.RIGHT,
                            exitTo = Direction.RIGHT
                        ) { DailyTestScreen() }
                    }
                }
            }
        }
    }
}
