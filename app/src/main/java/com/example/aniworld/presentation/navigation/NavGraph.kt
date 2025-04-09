package com.example.aniworld.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.aniworld.presentation.screens.AnimeDetailScreen
import com.example.aniworld.presentation.screens.AnimeListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.AnimeListScreen.route
    ) {
        composable(route = Screen.AnimeListScreen.route) {
            AnimeListScreen(
                onAnimeClick = { animeId ->
                    navController.navigate(Screen.AnimeDetailScreen.createRoute(animeId))
                }
            )
        }
        
        composable(
            route = Screen.AnimeDetailScreen.route + "/{animeId}",
            arguments = listOf(
                navArgument("animeId") {
                    type = NavType.IntType
                }
            )
        ) {
            AnimeDetailScreen(
                onBackPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object AnimeListScreen : Screen("anime_list")
    object AnimeDetailScreen : Screen("anime_detail") {
        fun createRoute(animeId: Int) = "$route/$animeId"
    }
} 