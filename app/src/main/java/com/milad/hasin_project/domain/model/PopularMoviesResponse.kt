package com.milad.hasin_project.domain.model

import com.google.gson.annotations.SerializedName
import com.milad.hasin_project.domain.model.Movie

data class PopularMoviesResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movies: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)