package com.milad.hasin_project.presentation.ui.movieList

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalFoundationApi::class)
class MovieListScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun movieListScreenTest() {
        composeTestRule.setContent {
            MyAppTheme {
                // Create a fake NavHost for testing
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "movieListScreen"
                ) {
                    composable("movieListScreen") {
                        MovieListScreen(navController = navController)
                    }
                }
            }
        }

        composeTestRule
            .onNodeWithText("Popular")
            .assertIsDisplayed()

    }
}
