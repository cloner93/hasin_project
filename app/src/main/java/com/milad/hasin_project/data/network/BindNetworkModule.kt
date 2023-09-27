package com.milad.hasin_project.data.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BindNetworkModule {

    @Binds
    fun bindsTmdbClientNetwork(
        movieRepository: RetrofitNetwork
    ): TmdbClientNetwork
}