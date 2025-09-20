package com.example.ur_color.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import kotlinx.serialization.Serializable

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