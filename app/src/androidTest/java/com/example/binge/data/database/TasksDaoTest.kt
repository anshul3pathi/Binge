package com.example.binge.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.binge.model.data.Movies
import com.example.binge.model.database.MoviesDataBase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.nullValue
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class TasksDaoTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

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
        ).build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertAll_getAll() = runBlockingTest {
        //  Given - insert a list of movies
        val moviesList = listOf(movie1)
        database.moviesDao().insertAll(moviesList)

        //  When - Get all the movies from database
        val loaded = database.moviesDao().getAll()

        //  Then - The loaded data contains the expected value
        assertThat(loaded as List<Movies>, notNullValue())
        assertThat(loaded[0].id, `is`(movie1.id))
        assertThat(loaded[0].movieName, `is`(movie1.movieName))
        assertThat(loaded[0].genre, `is`(movie1.genre))
        assertThat(loaded[0].directors, `is`(movie1.directors))
        assertThat(loaded[0].actors, `is`(movie1.actors))
        assertThat(loaded[0].rating, `is`(movie1.rating))
        assertThat(loaded[0].year, `is`(movie1.year))
        assertThat(loaded[0].desc, `is`(movie1.desc))
        assertThat(loaded[0].thumbNailUrl, `is`(movie1.thumbNailUrl))
        assertThat(loaded[0].imageUrl, `is`(movie1.imageUrl))
        assertThat(loaded[0].imdbUrl, `is`(movie1.imdbUrl))
    }

    @Test
    fun getMovieById() = runBlockingTest {
        //  Given - insert a new list of movies
        database.moviesDao().insertAll(listOf(movie1))

        //  When - Get the movie by id from database
        val loaded = database.moviesDao().getMovieById(movie1.id)

        //  Then - The loaded data contains the expected value
        assertThat(loaded as Movies, notNullValue())
        assertThat(loaded.id, `is`(movie1.id))
        assertThat(loaded.movieName, `is`(movie1.movieName))
        assertThat(loaded.genre, `is`(movie1.genre))
        assertThat(loaded.directors, `is`(movie1.directors))
        assertThat(loaded.actors, `is`(movie1.actors))
        assertThat(loaded.rating, `is`(movie1.rating))
        assertThat(loaded.year, `is`(movie1.year))
        assertThat(loaded.desc, `is`(movie1.desc))
        assertThat(loaded.thumbNailUrl, `is`(movie1.thumbNailUrl))
        assertThat(loaded.imageUrl, `is`(movie1.imageUrl))
        assertThat(loaded.imdbUrl, `is`(movie1.imdbUrl))
    }

    @Test
    fun deleteOldData() = runBlockingTest {
        //  Given - insert a new list of movies
        database.moviesDao().insertAll(listOf(movie1))

        //  When - old data in database is deleted
        database.moviesDao().deleteOldData()
        val loaded = database.moviesDao().getAll()

        //  Then - the loaded data is emptyList
        assertThat(loaded, `is`(emptyList()))
    }

}