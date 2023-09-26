package com.milad.hasin_project.domain.repository

import androidx.paging.PagingData
import com.milad.hasin_project.domain.model.FullMovieDetail
import com.milad.tmdb_client.core.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(
        page: Int? = null,
        language: String? = null,
        region: String? = null,
    ): Flow<PagingData<Movie>>

    suspend fun getMovie(
        movieId: Int,
        language: String? = null,
        appendToResponse: String? = null,
    ): Flow<Result<FullMovieDetail>>
}