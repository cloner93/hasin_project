package com.milad.hasin_project.domain

import com.milad.hasin_project.data.dataSource.fake.FakeTmdbNetworkData
import com.milad.hasin_project.data.utils.Result
import com.milad.hasin_project.domain.model.FullMovieDetail
import com.milad.hasin_project.domain.use_case.GetMovieUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Unit test for the [GetMovieUseCase] class.
 */
class GetMovieUseCaseTest {
    @Mock
    lateinit var network: FakeTmdbNetworkData
    private lateinit var useCase: GetMovieUseCase

    // Dispatcher for testing
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

    // Setup before each test
    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)

        useCase = GetMovieUseCase(network)
    }

    // Test case for the first emit always being a loading state
    @Test
    fun `test first emit always is loading`() = runTest(testDispatcher) {
        val actual = mutableListOf<Result<FullMovieDetail>>()

        // Collect the results for the first emit
        useCase(12345).take(1).collect {
            actual.add(it)
        }

        assertNotNull(actual)
        assertEquals(actual[0], Result.loading(null))
    }

    // Test case for the second emit returning a Connection error 404
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test second emit return Connection error 404`() = runTest(testDispatcher) {
        // Create a test error
        val error = RuntimeException("404", Throwable())
        given(network.getMovie(12345)).willThrow(error)

        val actual = mutableListOf<Result<FullMovieDetail>>()

        // Collect the results for the second emit
        useCase(12345).take(2).collect {
            actual.add(it)
        }

        assertEquals(actual[1], Result.error("Connection error: 404", null))
    }
}