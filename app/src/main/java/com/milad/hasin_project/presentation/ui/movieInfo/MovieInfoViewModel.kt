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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import java.io.Serializable
import javax.inject.Inject

@HiltViewModel
class MovieInfoViewModel @Inject constructor(
    private val useCase: GetMovieUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val movieId = (savedStateHandle.get<String>("movie_id")?.toInt()) ?: 0

    private val _state = popularMovieState(movieId)
    val state: StateFlow<MovieInfoState> = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            MovieInfoState.Loading
        )

    private fun popularMovieState(
        movieId: Int
    ): Flow<MovieInfoState> = flow {
        useCase(movieId).collect { res ->
            when (res.status) {
                Status.SUCCESS -> {
                    if (res.data != null) {
                        emit(MovieInfoState.Success(res.data))
                    } else {
                        emit(MovieInfoState.Error(Throwable("we can't found info for this movie!")))
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

    fun onRetryClicked() {
        popularMovieState(movieId)
    }
}

sealed interface MovieInfoState : Serializable {
    object Loading : MovieInfoState
    data class Error(val throwable: Throwable) : MovieInfoState
    data class Success(val data: FullMovieDetail) : MovieInfoState
}

sealed class MovieInfoEvent {
    object onBack : MovieInfoEvent()
    object onShare : MovieInfoEvent()
    object onRetry : MovieInfoEvent()
}
