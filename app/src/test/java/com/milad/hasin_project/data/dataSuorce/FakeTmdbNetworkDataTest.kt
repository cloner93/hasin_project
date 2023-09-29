package com.milad.hasin_project.data.dataSuorce

import JamUnitTestFakeAssetManager
import com.google.gson.Gson
import com.milad.hasin_project.data.dataSource.fake.FakeTmdbNetworkData
import com.milad.hasin_project.domain.model.Movie
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class FakeTmdbNetworkDataTest {

    private lateinit var subject: FakeTmdbNetworkData

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        subject = FakeTmdbNetworkData(
            ioDispatcher = testDispatcher,
            networkJson = Gson(),
            assets = JamUnitTestFakeAssetManager,
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test load popular movies`() = runTest(testDispatcher) {
        assertEquals(
            Movie(
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
            ),
            subject.getPopularMovies().movies.first(),
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test get fail popular movies`() = runTest(testDispatcher) {
        assertEquals(
            550,
            subject.getMovie(1).body()!!.id
        )
    }
}