package com.milad.hasin_project.presentation.ui.movieList

import androidx.paging.PagingData
import com.milad.hasin_project.domain.model.Movie
import com.milad.hasin_project.domain.use_case.GetPopularMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class PopularMoviesViewModelTest {

    private lateinit var viewModel: PopularMoviesViewModel

    @Mock
    private lateinit var useCase: GetPopularMoviesUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `getPopularMovies should update uiState`() = runTest(testDispatcher) {
        val testData: Flow<PagingData<Movie>> =flow{ emit(PagingData.from(listOf(movie)))}

        `when`(useCase.invoke()).thenReturn(testData)

        viewModel = PopularMoviesViewModel(useCase)

        val uiStateValue = viewModel.uiState.first().first()
        assertEquals(testData.first(), uiStateValue)
    }
}