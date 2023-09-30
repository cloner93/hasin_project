package com.milad.hasin_project.data.di

import com.milad.hasin_project.data.TmdbClientNetwork
import com.milad.hasin_project.data.dataSource.RetrofitNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * The `BindNetworkModule` interface is a Dagger Hilt module that provides bindings for the network-related
 * dependencies in the application.
 *
 * @see Module
 * @see InstallIn
 * @see SingletonComponent
 * @see Binds
 */
@Module
@InstallIn(SingletonComponent::class)
interface BindNetworkModule {

    /**
     * Binds the [RetrofitNetwork] implementation to the [TmdbClientNetwork] interface.
     *
     * @param movieRepository The [RetrofitNetwork] implementation.
     * @return An instance of [TmdbClientNetwork].
     */
    @Binds
    fun bindsTmdbClientNetwork(
        movieRepository: RetrofitNetwork
    ): TmdbClientNetwork
}