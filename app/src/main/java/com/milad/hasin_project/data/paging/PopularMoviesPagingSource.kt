package com.milad.hasin_project.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.milad.hasin_project.data.TmdbClientNetwork
import com.milad.hasin_project.domain.model.Movie
import javax.inject.Inject
import javax.inject.Singleton

/**
 * The `PopularMoviesPagingSource` class is a [PagingSource] implementation responsible for loading pages
 * of popular movies using the [TmdbClientNetwork].
 *
 * @param network The network client for interacting with the TMDb API.
 *
 * @see Singleton
 * @see TmdbClientNetwork
 * @see PagingSource
 */
@Singleton
class PopularMoviesPagingSource @Inject constructor(
    private val network: TmdbClientNetwork
) : PagingSource<Int, Movie>() {

    /**
     * Determines the refresh key for the given [PagingState].
     *
     * @param state The current [PagingState].
     * @return The refresh key.
     */
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    /**
     * Loads the requested page of popular movies.
     *
     * @param params The [LoadParams] indicating the load position and size.
     * @return The result of the load operation, either [LoadResult.Page] or [LoadResult.Error].
     */
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