package com.milad.hasin_project.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.milad.hasin_project.data.paging.PopularMoviesPagingSource
import com.milad.hasin_project.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The `GetPopularMoviesUseCase` class is a use case responsible for fetching a stream of popular movies
 * using the provided [PopularMoviesPagingSource].
 *
 * @param paging The paging source for retrieving popular movies.
 *
 * @see Singleton
 * @see PopularMoviesPagingSource
 */
@Singleton
class GetPopularMoviesUseCase @Inject constructor(private val paging: PopularMoviesPagingSource) {

    /**
     * Invokes the use case to retrieve a flow of popular movies using the provided paging source.
     *
     * @return A [Flow] emitting [PagingData] containing popular movies.
     */
    operator fun invoke(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { paging }
        ).flow
    }
}