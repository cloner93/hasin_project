package com.milad.hasin_project.domain.use_case

import com.milad.hasin_project.data.TmdbClientNetwork
import com.milad.hasin_project.data.utils.Result
import com.milad.hasin_project.data.utils.SafeApiRequest
import com.milad.hasin_project.domain.model.FullMovieDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton
/**
 * The `GetMovieUseCase` class is a use case responsible for fetching detailed information about a movie
 * using the [TmdbClientNetwork].
 *
 * @param network The network client for interacting with the TMDb API.
 *
 * @see Singleton
 * @see SafeApiRequest
 * @see TmdbClientNetwork
 */
@Singleton
class GetMovieUseCase @Inject constructor(
    private val network: TmdbClientNetwork
) : SafeApiRequest() {

    /**
     * Invokes the use case to fetch detailed information about a movie.
     *
     * @param movieId The unique identifier of the movie.
     * @param language The language in which to fetch the movie details.
     * @param appendToResponse Additional parameters to append to the API request.
     *
     * @return A [Flow] emitting a [Result] containing the detailed information of the movie.
     */
    suspend operator fun invoke(
        movieId: Int,
        language: String? = null,
        appendToResponse: String? = null
    ): Flow<Result<FullMovieDetail>> =
        apiRequest { network.getMovie(movieId, language, appendToResponse) }
}