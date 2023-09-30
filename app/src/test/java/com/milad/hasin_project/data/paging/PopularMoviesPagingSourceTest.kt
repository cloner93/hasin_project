package com.milad.hasin_project.data.paging

import androidx.paging.PagingSource
import com.milad.hasin_project.data.dataSource.fake.FakeTmdbNetworkData
import com.milad.hasin_project.domain.model.Movie
import com.milad.hasin_project.domain.model.PopularMoviesResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Unit test for [PopularMoviesPagingSource].
 * @see <a href="https://medium.com/@mohamed.gamal.elsayed/android-how-to-test-paging-3-pagingsource-433251ade028">Android — How to test Paging 3 (PagingSource)?</a>
 */
class PopularMoviesPagingSourceTest {

    // Mocked network instance
    @Mock
    lateinit var network: FakeTmdbNetworkData

    // Subject under test
    private lateinit var pagingSource: PopularMoviesPagingSource

    // Test dispatcher for coroutines
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

    // Setting up the test
    @Before
    fun before() {
        MockitoAnnotations.openMocks(this)
        pagingSource = PopularMoviesPagingSource(network)
    }

    // Test data
    companion object {
        private val movie = Movie(
            posterPath = "/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg",
            adult = false,
            overview = "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
            releaseDate = "2016-08-03",
            genreIds = listOf(14, 28, 80),
            id = 297761,
            originalTitle = "Suicide Squad",
            originalLanguage = "en",
            title = "Suicide Squad",
            backdropPath = "/ndlQ2Cuc3cjTL7lTynw6I4boP4S.jpg",
            popularity = 48.261451,
            voteCount = 1466,
            video = false,
            voteAverage = 5.91
        )
        val movieResponse = PopularMoviesResponse(
            page = 1,
            movies = listOf(movie),
            totalPages = 10,
            totalResults = 100
        )
        val nextMovieResponse = PopularMoviesResponse(
            page = 2,
            movies = listOf(movie),
            totalPages = 10,
            totalResults = 100
        )
    }

    // Test case for loading popular movies with a failure due to HTTP error
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test paging source load - failure - http error`() = runTest(testDispatcher) {
        val error = RuntimeException("404", Throwable())
        given(network.getPopularMovies(any(), any(), any())).willThrow(error)

        val expected = PagingSource.LoadResult.Error<Int, Movie>(error)
        val actual = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )

        assertEquals("failure - http error", expected, actual)
    }

    // Test case for loading popular movies with a failure due to receiving null response
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test paging source load - failure - received null`() = runTest(testDispatcher) {
        given(network.getPopularMovies(any(), any(), any())).willReturn(null)

        val expected = PagingSource.LoadResult.Error<Int, Movie>(
            NullPointerException("Cannot invoke \"com.milad.hasin_project.domain.model.PopularMoviesResponse.getMovies()\" because \"response\" is null")
        )
        val actual = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )

        assertEquals("failure - received null", expected.toString(), actual.toString())
    }

    // Test case for loading popular movies with a successful refresh
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test paging source refresh - success`() = runTest(testDispatcher) {
        given(network.getPopularMovies(any(), any(), any())).willReturn(movieResponse)

        val expected = PagingSource.LoadResult.Page(
            data = movieResponse.movies,
            prevKey = -1,
            nextKey = 1
        )

        val actual = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )

        assertEquals("refresh - success", expected, actual)
    }

    // Test case for loading popular movies with a successful append
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test paging source append - success`() = runTest(testDispatcher) {
        given(network.getPopularMovies(any(), any(), any())).willReturn(nextMovieResponse)

        val expected = PagingSource.LoadResult.Page(
            data = movieResponse.movies,
            prevKey = null,
            nextKey = 2
        )

        val actual = pagingSource.load(
            PagingSource.LoadParams.Append(
                key = 1,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )

        assertEquals("append - success", expected, actual)
    }

    // Test case for loading popular movies with a successful prepend
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test paging source prepend - success`() = runTest(testDispatcher) {
        given(network.getPopularMovies(any(), any(), any())).willReturn(movieResponse)

        val expected = PagingSource.LoadResult.Page(
            data = movieResponse.movies,
            prevKey = -1,
            nextKey = 1
        )

        val actual = pagingSource.load(
            PagingSource.LoadParams.Prepend(
                key = 0,
                loadSize = 1,
                placeholdersEnabled = false
            )
        )

        assertEquals("prepend - success", expected, actual)
    }
}