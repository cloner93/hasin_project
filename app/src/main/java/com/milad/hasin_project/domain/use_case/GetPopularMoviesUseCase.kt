package com.milad.hasin_project.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.milad.hasin_project.data.paging.PopularMoviesPagingSource
import com.milad.hasin_project.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetPopularMoviesUseCase @Inject constructor(private val paging: PopularMoviesPagingSource) {
    operator fun invoke(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { paging }
        ).flow
    }
}