package com.milad.hasin_project.domain.use_case

import com.milad.hasin_project.data.TmdbClientNetwork
import com.milad.hasin_project.data.utils.Result
import com.milad.hasin_project.data.utils.SafeApiRequest
import com.milad.hasin_project.domain.model.FullMovieDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMovieUseCase
@Inject constructor(
    private val network: TmdbClientNetwork
) : SafeApiRequest() {
    suspend operator fun invoke(
        movieId: Int,
        language: String? = null,
        appendToResponse: String? = null
    ): Flow<Result<FullMovieDetail>> =
        apiRequest { network.getMovie(movieId, language, appendToResponse) }
}