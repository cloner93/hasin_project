package com.milad.hasin_project.presentation.ui.movieList.utils

/**
 * Extension function to generate a poster URL based on the current string (assumed to be a poster path).
 *
 * @return The complete URL for the poster image.
 */
fun String.makePosterURL(): String =
    "https://image.tmdb.org/t/p/w342$this"