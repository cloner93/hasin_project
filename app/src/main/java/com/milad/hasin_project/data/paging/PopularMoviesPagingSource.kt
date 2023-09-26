package com.milad.hasin_project.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.milad.hasin_project.data.network.TmdbClientNetwork
import com.milad.tmdb_client.core.model.Movie

class PopularMoviesPagingSource constructor(
    private val network: TmdbClientNetwork
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = network.getPopularMovies(page = page)

            LoadResult.Page(
                data = response.movies,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.movies.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}