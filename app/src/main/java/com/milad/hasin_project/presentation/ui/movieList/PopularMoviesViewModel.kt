package com.milad.hasin_project.presentation.ui.movieList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.milad.hasin_project.domain.use_case.GetPopularMoviesUseCase
import com.milad.tmdb_client.core.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val useCase: GetPopularMoviesUseCase
) : ViewModel() {

    private var _uiState: MutableStateFlow<Flow<PagingData<Movie>>> = MutableStateFlow(flow { })
    val uiState: MutableStateFlow<Flow<PagingData<Movie>>>
        get() = _uiState

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        _uiState.value = useCase().cachedIn(viewModelScope)
    }
}