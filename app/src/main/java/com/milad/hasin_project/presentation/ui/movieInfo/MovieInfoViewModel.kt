package com.milad.hasin_project.presentation.ui.movieInfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.milad.hasin_project.data.utils.Status
import com.milad.hasin_project.domain.model.FullMovieDetail
import com.milad.hasin_project.domain.use_case.GetMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import java.io.Serializable
import javax.inject.Inject

/**
 * The `MovieInfoViewModel` class is a ViewModel for managing the UI-related data of the
 * Movie Info screen. It utilizes the [GetMovieUseCase] to fetch movie details.
 *
 * @param useCase The use case responsible for retrieving movie details.
 * @param savedStateHandle A handle to saved state, allowing access to data across ViewModel
 * lifecycle events.
 *
 * @see ViewModel
 * @see HiltViewModel
 * @see GetMovieUseCase
 */
@HiltViewModel
class MovieInfoViewModel @Inject constructor(
    private val useCase: GetMovieUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    /**
     * The movie ID extracted from the saved state handle.
     */
    val movieId = (savedStateHandle.get<String>("movie_id")?.toInt()) ?: 0

    /**
     * Private function to get the state flow of [MovieInfoState] based on the provided
     * [movieId].
     */
    private val _state = getMovieInfoState(movieId)
    val state: StateFlow<MovieInfoState> = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            MovieInfoState.Loading
        )

    /**
     * Function to retrieve the state flow of [MovieInfoState] based on the provided [movieId].
     */
    private fun getMovieInfoState(
        movieId: Int
    ): Flow<MovieInfoState> = flow {
        useCase(movieId).collect { res ->
            when (res.status) {
                Status.SUCCESS -> {
                    if (res.data != null) {
                        emit(MovieInfoState.Success(res.data))
                    } else {
                        emit(MovieInfoState.Error(Throwable("We can't find info for this movie!")))
                    }
                }

                Status.ERROR -> {
                    emit(MovieInfoState.Error(Throwable(res.message)))
                }

                Status.LOADING -> {
                    emit(MovieInfoState.Loading)
                }
            }
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Function to trigger a retry attempt for fetching movie information.
     */
    fun onRetryClicked() {
        getMovieInfoState(movieId)
    }
}

/**
 * Sealed interface representing the possible states of the Movie Info screen.
 */
sealed interface MovieInfoState : Serializable {
    object Loading : MovieInfoState
    data class Error(val throwable: Throwable) : MovieInfoState
    data class Success(val data: FullMovieDetail) : MovieInfoState
}
