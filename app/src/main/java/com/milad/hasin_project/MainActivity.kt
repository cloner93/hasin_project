package com.milad.hasin_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.milad.hasin_project.presentation.theme.hasin_projectTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * The `MainActivity` class is annotated with `@AndroidEntryPoint` and extends `ComponentActivity`.
 * It serves as the main entry point for the Android application, defining the UI using Jetpack Compose.
 *
 * @see ComponentActivity
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * Called when the activity is starting. Sets up the UI using Jetpack Compose.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            hasin_projectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavigation()
                }
            }
        }
    }
}
