package com.example.aniworld.presentation.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.aniworld.presentation.screens.AnimeDetailScreen
import com.example.aniworld.presentation.screens.AnimeListScreen
import com.example.aniworld.presentation.screens.SearchScreen
import com.example.aniworld.presentation.screens.StartingScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.StartingScreen.route
    ) {
        composable(route = Screen.StartingScreen.route) {
            StartingScreen(
                onGetStartedClick = {
                    Log.d("NavGraph", "Navigating to Anime List from Starting Screen")
                    navController.navigate(Screen.AnimeListScreen.route) {
                        // Clear back stack so user can't go back to starting screen
                        popUpTo(Screen.StartingScreen.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(route = Screen.AnimeListScreen.route) {
            AnimeListScreen(
                onAnimeClick = { animeId ->
                    Log.d("NavGraph", "Navigating to Anime Detail: $animeId")
                    navController.navigate(Screen.AnimeDetailScreen.createRoute(animeId))
                },
                onSearchClick = {
                    Log.d("NavGraph", "Navigating to Search Screen")
                    navController.navigate(Screen.SearchScreen.route)
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
                    Log.d("NavGraph", "Back pressed from Detail Screen")
                    navController.popBackStack()
                }
            )
        }
        
        composable(route = Screen.SearchScreen.route) {
            Log.d("NavGraph", "Search Screen Composable Called")
            SearchScreen(
                onBackPressed = {
                    Log.d("NavGraph", "Back pressed from Search Screen")
                    navController.popBackStack()
                },
                onAnimeClick = { animeId ->
                    Log.d("NavGraph", "Navigating to Anime Detail from Search: $animeId")
                    navController.navigate(Screen.AnimeDetailScreen.createRoute(animeId))
                },
                onSearch = { query ->
                    Log.d("NavGraph", "Search query: $query")
                    // This is now handled directly in the SearchScreen
                }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object StartingScreen : Screen("starting_screen")
    object AnimeListScreen : Screen("anime_list")
    object AnimeDetailScreen : Screen("anime_detail") {
        fun createRoute(animeId: Int) = "$route/$animeId"
    }
    object SearchScreen : Screen("anime_search")
} 