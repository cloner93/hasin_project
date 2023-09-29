package com.milad.hasin_project.data

import com.milad.hasin_project.domain.model.FullMovieDetail
import com.milad.hasin_project.domain.model.PopularMoviesResponse
import retrofit2.Response

interface TmdbClientNetwork {
    suspend fun getPopularMovies(
        page: Int? = null,
        language: String? = null,
        region: String? = null,
    ): PopularMoviesResponse

    suspend fun getMovie(
        movieId: Int,
        language: String? = null,
        appendToResponse: String? = null,
    ): Response<FullMovieDetail>
}