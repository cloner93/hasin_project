package com.milad.hasin_project.domain.use_case

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.milad.hasin_project.data.paging.PopularMoviesPagingSource
import com.milad.tmdb_client.core.model.Movie
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase(private val paging: PopularMoviesPagingSource) {
     operator fun invoke(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { paging }
        ).flow
    }
}