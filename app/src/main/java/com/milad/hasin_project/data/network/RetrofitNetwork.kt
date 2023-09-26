package com.milad.hasin_project.data.network

import com.milad.hasin_project.domain.model.FullMovieDetail
import com.milad.tmdb_client.core.model.PopularMoviesResponse
import okhttp3.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class RetrofitNetwork constructor(
    okhttpCallFactory: Call.Factory,
) : TmdbClientNetwork {
    private val networkApi = Retrofit.Builder()
        .baseUrl(baseUrl)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitNetworkApi::class.java)

    override suspend fun getPopularMovies(
        page: Int?,
        language: String?,
        region: String?
    ): PopularMoviesResponse = networkApi.getPopularMovies(
        page = page,
        language = language,
        region = region
    )

    override suspend fun getMovie(
        movieId: Int,
        language: String?,
        appendToResponse: String?
    ): Response<FullMovieDetail> = networkApi.getMovie(
        movieId = movieId,
        language = language,
        append_to_response = appendToResponse
    )
}

private interface RetrofitNetworkApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query(value = "page") page: Int?,
        @Query(value = "language") language: String?,
        @Query(value = "region") region: String?,
    ): PopularMoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path(value = "movie_id") movieId: Int,
        @Query(value = "language") language: String?,
        @Query(value = "append_to_response") append_to_response: String?,
    ): Response<FullMovieDetail>
}

private const val baseUrl = "http://api.themoviedb.org/3/"