package com.example.binge.model.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.binge.model.MoviesDataSource
import com.example.binge.model.Result
import com.example.binge.model.data.Movies
import com.example.binge.model.succeeded
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class LocalDataSourceAndroidTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var localDataSource: MoviesDataSource
    private lateinit var database: MoviesDataBase

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

    @Before
    fun init() {
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            MoviesDataBase::class.java
        ).allowMainThreadQueries().build()

        localDataSource = LocalDataSource(
            database.moviesDao()
        )
    }

    @After
    fun cleanUp() = database.close()

    @Test
    fun saveNewData_fetchAllData() = runBlockingTest {
        //  Given - insert a new movieList in database
        localDataSource.saveNewData(listOf(movie1))

        //  When - all movies are retrieved
        val loaded = localDataSource.fetchAllData()

        //  Then - same movie is returned
        assertThat(loaded.succeeded, `is`(true))
        loaded as Result.Success
        assertThat(loaded.data[0].id, Matchers.`is`(movie1.id))
        assertThat(loaded.data[0].movieName, Matchers.`is`(movie1.movieName))
        assertThat(loaded.data[0].genre, Matchers.`is`(movie1.genre))
        assertThat(loaded.data[0].directors, Matchers.`is`(movie1.directors))
        assertThat(loaded.data[0].actors, Matchers.`is`(movie1.actors))
        assertThat(loaded.data[0].rating, Matchers.`is`(movie1.rating))
        assertThat(loaded.data[0].year, Matchers.`is`(movie1.year))
        assertThat(loaded.data[0].desc, Matchers.`is`(movie1.desc))
        assertThat(loaded.data[0].thumbNailUrl, Matchers.`is`(movie1.thumbNailUrl))
        assertThat(loaded.data[0].imageUrl, Matchers.`is`(movie1.imageUrl))
        assertThat(loaded.data[0].imdbUrl, Matchers.`is`(movie1.imdbUrl))
    }

    @Test
    fun fetchAllData_noDataInDatabase_fetchesError() = runBlockingTest {
        //  Given - empty database

        //  When - list of all movies is retrieved
        val movies = localDataSource.fetchAllData()

        //  Then - Error is fetched
        assertThat(movies.succeeded, `is`(false))
    }

    @Test
    fun getMoviesById_givenValidId_fetchesMovie() = runBlockingTest {
        //  Given - insert a new movieList in the database
        localDataSource.saveNewData(listOf(movie1))

        //  When - movie is searched by valid id in database
        val movie = localDataSource.getMoviesById(movie1.id)

        //  Then - same movie is returned
        assertThat(movie.succeeded, `is`(true))
        movie as Result.Success
        assertThat(movie.data.id, Matchers.`is`(movie1.id))
        assertThat(movie.data.movieName, Matchers.`is`(movie1.movieName))
        assertThat(movie.data.genre, Matchers.`is`(movie1.genre))
        assertThat(movie.data.directors, Matchers.`is`(movie1.directors))
        assertThat(movie.data.actors, Matchers.`is`(movie1.actors))
        assertThat(movie.data.rating, Matchers.`is`(movie1.rating))
        assertThat(movie.data.year, Matchers.`is`(movie1.year))
        assertThat(movie.data.desc, Matchers.`is`(movie1.desc))
        assertThat(movie.data.thumbNailUrl, Matchers.`is`(movie1.thumbNailUrl))
        assertThat(movie.data.imageUrl, Matchers.`is`(movie1.imageUrl))
        assertThat(movie.data.imdbUrl, Matchers.`is`(movie1.imdbUrl))
    }

    @Test
    fun getMoviesById_givenInvalidId_fetchesError() = runBlockingTest {
        //  Given - insert a new movieList in the database
        localDataSource.saveNewData(listOf(movie1))

        //  When - movie is searched by invalid id in the database
        val movie = localDataSource.getMoviesById(-1L)

        //  Then - Error is fetched
        assertThat(movie.succeeded, `is`(false))
    }

}