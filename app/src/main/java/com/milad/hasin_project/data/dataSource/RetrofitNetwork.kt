package com.milad.hasin_project.data.dataSource

import com.milad.hasin_project.data.TmdbClientNetwork
import com.milad.hasin_project.domain.model.FullMovieDetail
import com.milad.hasin_project.domain.model.PopularMoviesResponse
import okhttp3.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton
/**
 * The `RetrofitNetwork` class is a singleton class that implements the [TmdbClientNetwork] interface
 * using Retrofit for making network requests to the TMDb API.
 *
 * @property okhttpCallFactory The OkHttpClient factory for making HTTP requests.
 *
 * @see Singleton
 * @see Inject
 * @see TmdbClientNetwork
 */
@Singleton
class RetrofitNetwork @Inject constructor(
    okhttpCallFactory: Call.Factory,
) : TmdbClientNetwork {

    /**
     * The network API instance for making API requests.
     */
    private val networkApi = Retrofit.Builder()
        .baseUrl(baseUrl)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitNetworkApi::class.java)

    /**
     * Implementation of the [getPopularMovies] method from [TmdbClientNetwork].
     */
    override suspend fun getPopularMovies(
        page: Int?,
        language: String?,
        region: String?
    ): PopularMoviesResponse = networkApi.getPopularMovies(
        page = page,
        language = language,
        region = region
    )

    /**
     * Implementation of the [getMovie] method from [TmdbClientNetwork].
     */
    override suspend fun getMovie(
        movieId: Int,
        language: String?,
        appendToResponse: String?
    ): Response<FullMovieDetail> = networkApi.getMovie(
        movieId = movieId,
        language = language,
        appendToResponse = appendToResponse
    )
}

/**
 * The RetrofitNetworkApi interface defines the API endpoints for making network requests.
 */
private interface RetrofitNetworkApi {

    /**
     * Retrieves a list of popular movies.
     */
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query(value = "page") page: Int?,
        @Query(value = "language") language: String?,
        @Query(value = "region") region: String?,
    ): PopularMoviesResponse

    /**
     * Retrieves details for a specific movie.
     */
    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path(value = "movie_id") movieId: Int,
        @Query(value = "language") language: String?,
        @Query(value = "append_to_response") appendToResponse: String?,
    ): Response<FullMovieDetail>
}

/**
 * The base URL for the TMDb API.
 */
private const val baseUrl = "http://api.themoviedb.org/3/"