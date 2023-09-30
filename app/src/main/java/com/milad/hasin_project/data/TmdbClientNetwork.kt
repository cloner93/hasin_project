package com.milad.hasin_project.data

import com.milad.hasin_project.domain.model.FullMovieDetail
import com.milad.hasin_project.domain.model.PopularMoviesResponse
import retrofit2.Response

/**
 * The `TmdbClientNetwork` interface defines the network operations required for interacting with the
 * TMDb API to fetch popular movies and detailed information about a specific movie.
 */
interface TmdbClientNetwork {

    /**
     * Fetches a list of popular movies from the TMDb API.
     *
     * @param page The page number of the results to retrieve.
     * @param language The language in which to fetch the movies.
     * @param region The region for which to fetch movies.
     *
     * @return A [PopularMoviesResponse] containing information about popular movies.
     */
    suspend fun getPopularMovies(
        page: Int? = null,
        language: String? = null,
        region: String? = null,
    ): PopularMoviesResponse

    /**
     * Fetches detailed information about a specific movie from the TMDb API.
     *
     * @param movieId The unique identifier of the movie.
     * @param language The language in which to fetch the movie details.
     * @param appendToResponse Additional parameters to append to the API request.
     *
     * @return A [Response] containing the detailed information of the movie.
     */
    suspend fun getMovie(
        movieId: Int,
        language: String? = null,
        appendToResponse: String? = null,
    ): Response<FullMovieDetail>
}