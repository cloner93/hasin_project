package com.milad.tmdb_client.feature.movielist.utils

fun String.makePosterURL(): String =
    "https://image.tmdb.org/t/p/w342$this"