package com.milad.hasin_project.domain.use_case

import com.milad.hasin_project.data.network.TmdbClientNetwork
import com.milad.hasin_project.domain.model.FullMovieDetail
import kotlinx.coroutines.flow.Flow
import com.milad.hasin_project.data.utils.SafeApiRequest

class GetMovieUseCase(private val network: TmdbClientNetwork) {
    operator fun invoke(
        movieId: Int,
        language: String?,
        append_to_response: String?
    ): Flow<Result<FullMovieDetail>> =
       TODO(" apiRequest { network.getMovie(movieId, language, append_to_response) }")

}