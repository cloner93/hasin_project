package com.milad.hasin_project.presentation.ui.movieInfo

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import com.milad.hasin_project.data.utils.Result
import com.milad.hasin_project.domain.use_case.GetMovieUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
/**
 * Unit test for the [MovieInfoViewModel] class.
 */
class MovieInfoViewModelTest {
    @Mock
    private lateinit var usecase: GetMovieUseCase

    @Mock
    private lateinit var savedState: SavedStateHandle
    private lateinit var viewModel: MovieInfoViewModel

    // Dispatcher for testing
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

    // Setup before each test
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    // Test case for emitting MovieInfoState loading first
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test emit MovieInfoState loading first`() = runTest(testDispatcher) {
        // Mock saved state and use case
        given(savedState.get<String>("movie_id")).willReturn("12345")
        given(usecase.invoke(12345)).willReturn(
            flow {
                emit(Result.loading(null))
            }
        )

        // Create the view model
        viewModel = MovieInfoViewModel(usecase, savedState)

        val actual = mutableStateListOf<MovieInfoState>()
        viewModel.state.take(1).collect {
            actual.add(it)
        }

        assertEquals(actual[0], MovieInfoState.Loading)
    }

    // Test case for the second emit returning an error
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test second emit return error`() = runTest(testDispatcher) {
        // Mock saved state and use case
        given(savedState.get<String>("movie_id")).willReturn("12345")
        given(usecase.invoke(12345)).willReturn(
            flow {
                emit(Result.loading(null))
                emit(Result.error("error", null))
            }
        )
        // Create the view model
        viewModel = MovieInfoViewModel(usecase, savedState)

        val actual = mutableStateListOf<MovieInfoState>()
        viewModel.state.take(2).collect {
            actual.add(it)
        }

        assertEquals(actual.size, 2)
        assertEquals(
            (actual[1] as MovieInfoState.Error).throwable.message,
            MovieInfoState.Error(Throwable("error")).throwable.message
        )
    }
}