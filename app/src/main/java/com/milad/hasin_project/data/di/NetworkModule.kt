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

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Dispatcher(TmdbDispatchers.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun apiKey(): String {
        return "55957fcf3ba81b137f8fc01ac5a31fb5"
    }

    @Singleton
    @Provides
    fun okHttpLogger(): HttpLoggingInterceptor {
        val okHttpLogger = HttpLoggingInterceptor {
            println(it)
        }
        okHttpLogger.level = HttpLoggingInterceptor.Level.BASIC

        return okHttpLogger
    }

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