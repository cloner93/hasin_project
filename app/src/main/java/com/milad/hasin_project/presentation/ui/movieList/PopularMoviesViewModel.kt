package com.milad.hasin_project.presentation.ui.movieList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.milad.hasin_project.domain.model.Movie
import com.milad.hasin_project.domain.use_case.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * The `PopularMoviesViewModel` class is a ViewModel responsible for managing the UI-related data of
 * the Popular Movies screen. It utilizes the [GetPopularMoviesUseCase] to fetch popular movies.
 *
 * @param useCase The use case responsible for retrieving popular movies.
 *
 * @see ViewModel
 * @see HiltViewModel
 * @see GetPopularMoviesUseCase
 */
@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val useCase: GetPopularMoviesUseCase
) : ViewModel() {

    /**
     * MutableStateFlow representing the UI state with a Flow of PagingData<Movie>.
     */
    private var _uiState: MutableStateFlow<Flow<PagingData<Movie>>> = MutableStateFlow(flow { })
    val uiState: MutableStateFlow<Flow<PagingData<Movie>>>
        get() = _uiState

    /**
     * Initializes the PopularMoviesViewModel and triggers the retrieval of popular movies.
     */
    init {
        getPopularMovies()
    }

    /**
     * Function to fetch popular movies using the [GetPopularMoviesUseCase] and update the UI state.
     */
    fun getPopularMovies() {
        _uiState.value = useCase().cachedIn(viewModelScope)
    }
}