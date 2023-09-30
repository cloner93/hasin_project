package com.milad.hasin_project.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

/**
 * The `NetworkModule` object is a Dagger Hilt module that provides network-related dependencies
 * for the application.
 *
 * @see Module
 * @see InstallIn
 * @see SingletonComponent
 * @see Provides
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides the IO dispatcher for coroutine operations with the specified [Dispatcher] qualifier.
     *
     * @return The IO dispatcher.
     */
    @Provides
    @Dispatcher(TmdbDispatchers.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    /**
     * Provides the API key used for TMDb API requests.
     *
     * @return The TMDb API key.
     */
    @Provides
    fun apiKey(): String {
        return "55957fcf3ba81b137f8fc01ac5a31fb5"
    }

    /**
     * Provides an instance of [HttpLoggingInterceptor] for logging HTTP requests and responses.
     *
     * @return The logging interceptor.
     */
    @Singleton
    @Provides
    fun okHttpLogger(): HttpLoggingInterceptor {
        val okHttpLogger = HttpLoggingInterceptor {
            println(it)
        }
        okHttpLogger.level = HttpLoggingInterceptor.Level.BASIC

        return okHttpLogger
    }

    /**
     * Provides an instance of [Call.Factory] for making HTTP requests with the specified interceptors.
     *
     * @param loggingInterceptor The logging interceptor.
     * @param apiKey The TMDb API key.
     * @return The OkHttpClient.
     */
    @Singleton
    @Provides
    fun okHttpClient(loggingInterceptor: HttpLoggingInterceptor, apiKey: String): Call.Factory =
        OkHttpClient.Builder().addInterceptor {
            val originalRequest = it.request()
            val originalUrl = originalRequest.url

            val url = originalUrl.newBuilder()
                .addQueryParameter("api_key", apiKey)
                .build()

            val request = originalRequest.newBuilder().url(url).build()
            it.proceed(request)
        }
            .addInterceptor(loggingInterceptor)
            .build()
}