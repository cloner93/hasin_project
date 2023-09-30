package com.milad.hasin_project.data.dataSource.fake

import JamUnitTestFakeAssetManager
import com.google.gson.Gson
import com.milad.hasin_project.data.TmdbClientNetwork
import com.milad.hasin_project.data.di.Dispatcher
import com.milad.hasin_project.data.di.TmdbDispatchers
import com.milad.hasin_project.domain.model.FullMovieDetail
import com.milad.hasin_project.domain.model.PopularMoviesResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.InputStreamReader
import javax.inject.Inject

/**
 * The `FakeTmdbNetworkData` class implements the [TmdbClientNetwork] interface for providing fake network data.
 *
 * @property ioDispatcher The coroutine dispatcher for IO operations.
 * @property networkJson The Gson instance for JSON parsing.
 * @property assets The [FakeAssetManager] for accessing fake assets.
 */
class FakeTmdbNetworkData @Inject constructor(
    @Dispatcher(TmdbDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Gson,
    private val assets: FakeAssetManager = JamUnitTestFakeAssetManager,
) : TmdbClientNetwork {

    companion object {
        private const val POPULAR_ASSET = "popular.json"
        private const val MOVIE_ASSET = "movie.json"
    }

    /**
     * Retrieves fake popular movies data from the assets.
     *
     * @param page The page number for pagination.
     * @param language The language for the response.
     * @param region The region for the response.
     * @return A [PopularMoviesResponse] representing the fake popular movies data.
     */
    override suspend fun getPopularMovies(
        page: Int?,
        language: String?,
        region: String?
    ): PopularMoviesResponse = withContext(ioDispatcher) {
        assets.open(POPULAR_ASSET).use {
            networkJson.fromJson(InputStreamReader(it), PopularMoviesResponse::class.java)
        }
    }

    /**
     * Retrieves fake movie data from the assets.
     *
     * @param movieId The ID of the movie to retrieve.
     * @param language The language for the response.
     * @param appendToResponse Additional parameters to append to the response.
     * @return A [Response] containing the fake [FullMovieDetail] data.
     */
    override suspend fun getMovie(
        movieId: Int,
        language: String?,
        appendToResponse: String?
    ): Response<FullMovieDetail> = withContext(ioDispatcher) {
        assets.open(MOVIE_ASSET).use {
            Response.success(
                networkJson.fromJson(
                    InputStreamReader(it),
                    FullMovieDetail::class.java
                )
            )
        }
    }
}