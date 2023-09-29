package com.milad.hasin_project.data.dataSource.fake

import JamUnitTestFakeAssetManager
import com.google.gson.Gson
import com.milad.hasin_project.data.TmdbClientNetwork
import com.milad.hasin_project.data.di.Dispatcher
import com.milad.hasin_project.domain.model.FullMovieDetail
import com.milad.hasin_project.domain.model.PopularMoviesResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.InputStreamReader
import javax.inject.Inject
import retrofit2.Response
import com.milad.hasin_project.data.di.TmdbDispatchers

class FakeTmdbNetworkData @Inject constructor(
    @Dispatcher(TmdbDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val networkJson: Gson,
    private val assets: FakeAssetManager = JamUnitTestFakeAssetManager,
) : TmdbClientNetwork {

    companion object {
        private const val POPULAR_ASSET = "popular.json"
        private const val MOVIE_ASSET = "movie.json"
    }

    override suspend fun getPopularMovies(
        page: Int?,
        language: String?,
        region: String?
    ): PopularMoviesResponse = withContext(ioDispatcher) {
        assets.open(POPULAR_ASSET).use {
            networkJson.fromJson(InputStreamReader(it), PopularMoviesResponse::class.java)
        }
    }

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