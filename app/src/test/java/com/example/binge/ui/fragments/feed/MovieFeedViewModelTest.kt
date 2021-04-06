package com.example.binge.ui.fragments.feed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.binge.CurrentTheme
import com.example.binge.DataFetchingStatus
import com.example.binge.SortBy
import com.example.binge.data.source.repository.FakeMoviesRepository
import com.example.binge.data.source.storage.FakeSharedPReferences
import com.example.binge.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.time.ExperimentalTime

@ExperimentalTime
@RunWith(AndroidJUnit4::class)
class MovieFeedViewModelTest {
    
    private val fakeStorage = FakeSharedPReferences()
    private val fakeRepository =
        FakeMoviesRepository(shouldFetchError = false, shouldFetchFromDb = false)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var movieFeedViewModel: MovieFeedViewModel

    @Before  // for setting up viewModel before executing any tests, sort of like init block.
    fun setupViewModel() {
        movieFeedViewModel = MovieFeedViewModel(fakeRepository, fakeStorage)
    }

    @Test
    fun saveThemePreferences_themeValue_sharedPrefValue() {

        // When viewModel is given CurrentTheme.Dark
        movieFeedViewModel.saveThemePreference(CurrentTheme.DARK)

        // Then value of sharedPreference saved should be true
        assertThat(fakeStorage.getBoolean(), `is`(true))

        // When viewModel is given CurrentTheme.Light
        movieFeedViewModel.saveThemePreference((CurrentTheme.LIGHT))

        // Then value of sharedPreference saved should be false
        assertThat(fakeStorage.getBoolean(), `is`(false))

    }

    @Test
    fun changeSortOrder_SortBy_ChangeToastMessage() {
        //  When - viewModel.changeSortOrder() is called with SortBy.RATING
        movieFeedViewModel.changeSortByType(SortBy.RATING)

        //  Then - value of sortByToast will change to Sorting by rating.
        assertThat(
            movieFeedViewModel.sortByToast.getOrAwaitValue(),
            `is`("Sorting by rating.")
        )

        //  When - viewModel.changeSortOrder() is called with SortBy.YEAR
        movieFeedViewModel.changeSortByType(SortBy.YEAR)

        //  Then - value of sortByToast will change to Sorting by year.
        assertThat(
            movieFeedViewModel.sortByToast.getOrAwaitValue(),
            `is`("Sorting by year.")
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun populateFeedData_successFailure_doneError() = runBlockingTest {
        /**
         *   When viewModel object is initialised, it will call repositories getMovies() method
         *   which will fetch Result.Success or Result.Error
         **/

        //  When viewModel.getMovies fetches Result.Success
        fakeRepository.setShouldFetchError(false)
        movieFeedViewModel.refreshData()

        //  Then value of dataFetchingStatus liveData will be DataFetchingStatus.DONE
        assertThat(
            movieFeedViewModel.dataFetchingStatus.getOrAwaitValue(),
            `is`(DataFetchingStatus.DONE)
        )

        //  When viewModel.getMovies fetches Result.Error
        fakeRepository.setShouldFetchError(true)
        fakeStorage.setLong(-1L)
        movieFeedViewModel.refreshData()

        //  Then value of dataFetchingStatus liveData will be DataFetchingStatus.Failed
        assertThat(
            movieFeedViewModel.dataFetchingStatus.getOrAwaitValue(),
            `is`(DataFetchingStatus.FAILED)
        )

    }

}