package com.example.binge.model.repository

import com.example.binge.data.source.FakeDataSource
import com.example.binge.data.source.storage.FakeSharedPReferences
import com.example.binge.model.Result
import com.example.binge.model.data.Movies
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime
import kotlin.time.hours

@ExperimentalTime
@ExperimentalCoroutinesApi
class MoviesRepositoryTest {

    private lateinit var repository: MoviesRepository
    private lateinit var localFakeDataSource: FakeDataSource
    private lateinit var remoteFakeDataSource: FakeDataSource
    private lateinit var sharedPref: FakeSharedPReferences

    private val movie1 = Movies(
        listOf("Ayushman", "Tabbu", "Radhika"),
        "Kafi sexy movie",
        listOf("Dont know", "Christopher Nolan"),
        listOf("Crime", "Thriller", "Drama", "Music"),
        "djklfhasdklfhkadjsfh",
        "https://m.media-amazon.com/images/M/MV5BZWZhMjhhZmYtOTIzOC00MGYzLWI1OGYtM2ZkN2IxNTI4ZWI3XkEyXkFqcGdeQXVyNDAzNDk0MTQ@._V1_UX182_CR0,0,182,268_AL__QL50.jpg",
        "adlskfjhaklsdfhadklsf",
        "Andhadhun",
        9.2,
        2016
    )
    private val movie2 = Movies(
        listOf("Rajkumar", "Pankaj"),
        "Intermixing timelines",
        listOf("Anurag Basu"),
        listOf("Crime", "Thriller", "Drama", "Music"),
        "djklfhasdkladsffhkadjsfh",
        "adjklshadfasfadsffadjshfads",
        "adlskfjhakladsfadsfasfadssdfhadklsf",
        "Ludo",
        9.0,
        2020
    )
    private val movie3 = Movies(
        listOf("Leaonardo Di Caprio", "Micheal Caine"),
        "MindFuck",
        listOf("Christopher Nolan"),
        listOf("Crime", "Thriller", "Drama", "Sci-Fi"),
        "djklfhasdfadsfadsfaklfhkadjsfh",
        "adsdasfdsdfgsghsfgrght",
        "adlskfjhaklgfgrs4tresgtsdfhadklsf",
        "Inception",
        9.6,
        2012
    )
    val movie4 = Movies(
        listOf("McCanaughay", "Michaol Caine", "Anne Hathaway"),
        "MindFuck2",
        listOf("Christopher Nolan"),
        listOf("Drama", "Sci-Fi"),
        "djklfhasdklfhkfd4rdaf4r54adjsfh",
        "adjklshfadgh 4r43fadjshfads",
        "adlskfjhaklsfer 334dfhadklsf",
        "Interstellar",
        9.8,
        2014
    )

    private val remoteMovies = listOf(movie1, movie2, movie3, movie4)
    private val localMovies = listOf(movie1, movie2)

    //  Given
    @Before
    fun setupRepository() {

        sharedPref = FakeSharedPReferences()

        localFakeDataSource =
            FakeDataSource(localMovies.toMutableList(),  asRemote = false)

        remoteFakeDataSource =
            FakeDataSource(remoteMovies.toMutableList(),  asRemote = true)

        repository = MoviesRepository(
            localFakeDataSource,
            remoteFakeDataSource,
            sharedPref
        )
    }

    @Test
    fun shouldFetchData_lastSyncedTime_TrueOrFalse() {
        /**
         *  lastSynced time is stored in sharedPreferences provided by Storage interface
         *  and in this case provided by the implementation of Storage
         *  interface - FakeSharedPreferences
        **/

        //  When value of lastSynced time is -1
        sharedPref.setLong(-1L)

        //  Then shouldFetchNewData should return true
        assertThat(repository.shouldFetchNewData(), `is`(true))

        //  When value of lastSynced time is greater than 1 hour
        sharedPref.setLong(1.5.hours.toLongMilliseconds())

        //  Then shouldFetchNewData should return true
        assertThat(repository.shouldFetchNewData(), `is`(true))

        //  When value of lastSynced time is smaller tha 1 hour
        sharedPref.setLong(0.5.hours.toLongMilliseconds())

        //  Then shouldFetchNewData should return false
        assertThat(repository.shouldFetchNewData(), `is`(false))
    }

    @Test
    fun getMovies_requestAllMoviesFromRepository() = runBlockingTest {

        //  When value of lastSynced time is less than 1 hour ago or -1
        sharedPref.setLong(0.5.hours.toLongMilliseconds())
        var movies = repository.getMovies() as Result.Success

        //  Then movies are loaded from local data source
        assertThat(movies.data, IsEqual(localMovies))

        //  When value of lastSynced time is greater than 1 hour ago
        sharedPref.setLong(2.hours.toLongMilliseconds())
        movies = repository.getMovies() as Result.Success

        //  Then movies are loaded from remote data source
        assertThat(movies.data, IsEqual(remoteMovies))
    }

    @Test
    fun getMovies_remoteOrLocalOrBothFail_moviesList() = runBlockingTest {


        //  When fetching from remote fails
        sharedPref.setLong(-1L)
        remoteFakeDataSource.setShouldFetchError(true)
        localFakeDataSource.setShouldFetchError(false)
        var movies = repository.getMovies() as Result.Success

        //  Then movies should be fetched from local data source
        assertThat(movies.data, IsEqual(localMovies))


        //  When fetching from local data source fails
        sharedPref.setLong( 0.5.hours.toLongMilliseconds())
        localFakeDataSource.setShouldFetchError(true)
        remoteFakeDataSource.setShouldFetchError(false)
        movies = repository.getMovies() as Result.Success

        //  Then movies should be fetched from remote
        assertThat(movies.data, IsEqual(remoteMovies))


        //  When fetching from both local and remote data source fails
        sharedPref.setLong(-1L)
        remoteFakeDataSource.setShouldFetchError(true)
        localFakeDataSource.setShouldFetchError(true)
        val movies2 = repository.getMovies() as Result.Error

        //  Then repository will throw IllegalStateException("Cannot fetch fake data!")
        assertThat(movies2.exception.message, IsEqual("Cannot fetch fake data!"))
    }

}