package com.milad.hasin_project

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.milad.hasin_project.presentation.ui.movieInfo.MovieInfoScreen
import com.milad.hasin_project.presentation.ui.movieList.MovieListScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "popularMovies") {
        composable("popularMovies") { MovieListScreen(navController = navController) }
        composable("movie/{movie_id}") { navBackStackEntry ->
            val movieId = navBackStackEntry.arguments?.getString("movie_id")
            movieId?.let { MovieInfoScreen(navController = navController) }
        }
    }
}
