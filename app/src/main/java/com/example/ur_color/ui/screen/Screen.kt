package com.example.ur_color.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

@Serializable
sealed interface Screen

fun Screen.route(): String {
    val gson = Gson()
    val type = this::class.simpleName ?: error("No class name for $this")

    return if (this::class.objectInstance != null) {
        type
    } else {
        val json = gson.toJson(this)
        "$type/$json"
    }
}

fun NavController.nav(route: String) {
    val routeBase = route.substringBefore("/")
    val currentRouteBase =
        currentBackStackEntry?.destination?.route?.substringBefore("/") ?: ""

    if (routeBase == currentRouteBase) {
        return
    }

    navigate(route) {
        launchSingleTop = true
    }
}

enum class Direction { LEFT, RIGHT, TOP, BOTTOM }
inline fun <reified T : Screen> NavGraphBuilder.animatedScreenComposable(
    navController: NavController,
    screenClass: KClass<T>,
    enterFrom: Direction = Direction.RIGHT,
    exitTo: Direction = Direction.RIGHT,
    crossinline content: @Composable (T) -> Unit
) {
    val gson = Gson()
    val typeName = screenClass.simpleName ?: error("No class name for $screenClass")

    val nav = navController
    if (nav.currentBackStackEntry?.destination?.route?.startsWith(typeName) == true) {
        return
    }

    if (screenClass.objectInstance != null) {
        composable(
            route = typeName,
            enterTransition = { enterTransition(enterFrom) },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition(exitTo) }
        ) {
            BackHandler { navController.popBackStack() }
            content(screenClass.objectInstance as T)
        }
    } else {
        composable(
            route = "$typeName/{json}",
            arguments = listOf(navArgument("json") { type = NavType.StringType }),
            enterTransition = { enterTransition(enterFrom) },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition(exitTo) }
        ) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("json")
            val screen = gson.fromJson(json, screenClass.java)
            BackHandler { navController.popBackStack() }
            content(screen)
        }
    }
}


fun enterTransition(enterFrom: Direction): EnterTransition {
    return when (enterFrom) {
        Direction.LEFT -> slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(300))
        Direction.RIGHT -> slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300))
        Direction.TOP -> slideInVertically(initialOffsetY = { -it }, animationSpec = tween(300))
        Direction.BOTTOM -> slideInVertically(initialOffsetY = { it }, animationSpec = tween(300))
    }
}

fun exitTransition(): ExitTransition {
    return ExitTransition.None
}

fun popEnterTransition(): EnterTransition {
    return EnterTransition.None
}

fun popExitTransition(exitTo: Direction): ExitTransition {
    return when (exitTo) {
        Direction.LEFT -> slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(300))
        Direction.RIGHT -> slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(300))
        Direction.TOP -> slideOutVertically(targetOffsetY = { -it }, animationSpec = tween(300))
        Direction.BOTTOM -> slideOutVertically(targetOffsetY = { it }, animationSpec = tween(300))
    }
}


inline fun <reified T : Screen> NavGraphBuilder.screenComposable(
    crossinline content: @Composable (T) -> Unit
) {
    val gson = Gson()
    val typeName = T::class.simpleName ?: error("No class name for ${T::class}")

    if (T::class.objectInstance != null) {
        composable(typeName) {
            content(T::class.objectInstance as T)
        }
    } else {
        composable(
            route = "$typeName/{json}",
            arguments = listOf(navArgument("json") { type = NavType.StringType })
        ) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("json")
            val screen = gson.fromJson(json, T::class.java)
            content(screen)
        }
    }
}