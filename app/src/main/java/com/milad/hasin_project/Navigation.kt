package com.milad.hasin_project

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.milad.hasin_project.presentation.ui.movieInfo.MovieInfoScreen
import com.milad.hasin_project.presentation.ui.movieList.MovieListScreen

/**
 * Jetpack Compose composable function representing the main navigation setup for the application.
 * Utilizes the Navigation component with [NavHost] and defines routes using [composable].
 *
 * @see Composable
 * @see NavHost
 * @see rememberNavController
 * @see MovieListScreen
 * @see MovieInfoScreen
 *
 */
@Composable
fun MainNavigation() {
    // Create a NavController to handle navigation
    val navController = rememberNavController()

    // Define navigation routes within the NavHost
    NavHost(navController = navController, startDestination = "popularMovies") {
        composable("popularMovies") { MovieListScreen(navController = navController) }
        composable("movie/{movie_id}") { navBackStackEntry ->
            val movieId = navBackStackEntry.arguments?.getString("movie_id")
            movieId?.let { MovieInfoScreen(navController = navController) }
        }
    }
}
