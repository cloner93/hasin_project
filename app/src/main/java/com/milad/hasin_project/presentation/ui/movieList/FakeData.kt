package com.milad.tmdb_client.feature.movielist

import com.milad.tmdb_client.core.model.Movie

val movie = Movie(
    posterPath = "https://m.media-amazon.com/images/I/51iJAwDnpVL._SX300_SY300_QL70_FMwebp_.jpg",
    adult = false,
    overview = "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
    releaseDate = "2016-08-03",
    genreIds = listOf(14, 28, 80),
    id = 297761,
    originalTitle = "Suicide Squad",
    originalLanguage = "en",
    title = "Suicide Squad",
    backdropPath = "https://image.tmdb.org/t/p/original/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg",
    popularity = 48.26145,
    voteCount = 1466,
    video = false,
    voteAverage = 5.91
)


val fakeMovieList = listOf(
    movie,movie,movie,
    movie,movie,movie,
    movie,movie,movie,
    movie,movie,movie,
    movie,movie,movie,
    movie
)