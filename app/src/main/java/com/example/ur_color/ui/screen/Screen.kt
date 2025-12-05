package com.example.ur_color.ui.screen

import android.util.Base64
import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json



fun NavController.nav(
    screen : Screen,
) {
    val route = screen.route()
    val page = route.substringBefore("/")
    val current =
        currentBackStackEntry?.destination?.route?.substringBefore("/") ?: ""

    if (page == current) {
        return
    }

    navigate(route) {
        launchSingleTop = true
    }
}

fun NavController.popBack(to: Screen? = null, inclusive: Boolean = false) {
    val route = to?.route()?.substringBefore("/")
    if (route == null) {
        popBackStack()
        return
    }
    popBackStack(route, inclusive)
}





inline fun <reified T : Screen> NavGraphBuilder.animatedScreenComposable(
    navController: NavController,
    enterFrom: Direction = Direction.RIGHT,
    exitTo: Direction = Direction.RIGHT,
    crossinline content: @Composable (T) -> Unit
) {
    val typeName = T::class.simpleName ?: error("No class name for ${T::class}")
    val objectInstance = T::class.objectInstance

    if (objectInstance != null) {
        composable(
            route = typeName,
            enterTransition = { enterTransition(enterFrom) },
            exitTransition = { exitTransition() },
            popEnterTransition = { popEnterTransition() },
            popExitTransition = { popExitTransition(exitTo) }
        ) {
            BackHandler { navController.popBackStack() }
            content(objectInstance)
        }
        return
    }

    composable(
        route = "$typeName/{encoded}",
        arguments = listOf(navArgument("encoded") { type = NavType.StringType }),
        enterTransition = { enterTransition(enterFrom) },
        exitTransition = { exitTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition(exitTo) }
    ) { backStackEntry ->
        val encoded = backStackEntry.arguments?.getString("encoded")
            ?: error("Missing encoded argument for $typeName")

        val screen = decodeFromBase64<T>(encoded)

        BackHandler { navController.popBackStack() }
        content(screen)
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





val jsonFormat = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
}

inline fun <reified T> encodeToBase64(obj: T): String {
    val json = jsonFormat.encodeToString(obj)
    return Base64.encodeToString(json.toByteArray(), Base64.URL_SAFE or Base64.NO_WRAP)
}

inline fun <reified T> decodeFromBase64(encoded: String): T {
    val json = String(Base64.decode(encoded, Base64.URL_SAFE or Base64.NO_WRAP))
    return jsonFormat.decodeFromString(json)
}

fun Screen.route(): String {
    val type = this::class.simpleName ?: error("No class name for $this")

    val isObject = this::class.objectInstance != null
    return if (isObject) {
        type
    } else {
        val encoded = encodeToBase64(this)
        "$type/$encoded"
    }
}

enum class Direction { LEFT, RIGHT, TOP, BOTTOM }

@Serializable
sealed interface Screen