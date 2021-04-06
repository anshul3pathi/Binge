package com.example.binge.ui.fragments.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.binge.data.source.repository.FakeMoviesRepository
import com.example.binge.data.source.storage.FakeSharedPReferences
import com.example.binge.getOrAwaitValue
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.time.ExperimentalTime


@RunWith(AndroidJUnit4::class)
@ExperimentalTime
class DetailMovieViewModelTest {

    private val fakeStorage = FakeSharedPReferences()
    private val fakeRepository = FakeMoviesRepository(false, false)

    @get:Rule
    private var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var detailsMovieViewModel: DetailMovieViewModel

    @Before
    fun init() {
        //  Given - DetailMovieViewModel
        detailsMovieViewModel = DetailMovieViewModel(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun openImdbUrlButtonClicked_openImdbTrue() {
        //  When - openImdbUrlButtonClicked method is called
        detailsMovieViewModel.openImdbUrlButtonClicked()

        //  Then - value of openImdbUrl live data changes to true
        assertThat(detailsMovieViewModel.openImdbLiveData.getOrAwaitValue(), `is`(true))
    }

    @Test
    fun imdbUrlOpened_openImdbFalse() {
        //  When - imdbUrlOpened method is called
        detailsMovieViewModel.imdbUrlOpened()

        //  Then - value of openImdbUrl live data changes to false
        assertThat(detailsMovieViewModel.openImdbLiveData.getOrAwaitValue(), `is`(false))
    }

}