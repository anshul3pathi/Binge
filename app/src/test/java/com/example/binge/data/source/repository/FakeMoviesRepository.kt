package com.example.binge.data.source.repository

import com.example.binge.model.Result
import com.example.binge.model.data.Movies
import com.example.binge.model.repository.MoviesRepo

class FakeMoviesRepository(
    private var shouldFetchFromDb: Boolean,
    private var shouldFetchError: Boolean
) : MoviesRepo {

    private val movies = mutableListOf(
        Movies(
            listOf("Ayushman", "Tabbu", "Radhika"),
            "Kafi sexy movie",
            listOf("Dont know", "Christopher Nolan"),
            listOf("Crime", "Thriller", "Drama", "Music"),
            "djklfhasdklfhkadjsfh",
            "adjklshfadjshfads",
            "adlskfjhaklsdfhadklsf",
            "Andhadhun",
            9.2,
            2016,
        ),
        Movies(
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
        ),
        Movies(
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
        ),
        Movies(
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
        ),
    )

    init {
        for(i in 0 until movies.size) {
            movies[i].id = i.toLong()
        }
    }

    override suspend fun getMovies(): Result<List<Movies>> {
        return if(shouldFetchNewData()) {
            fetchNewData()
        } else {
            fetchFromDb()
        }
    }

    override fun shouldFetchNewData() = shouldFetchFromDb

    override suspend fun fetchNewData(): Result<List<Movies>> {
        return if(!shouldFetchError) {
            Result.Success(movies)
        } else {
            Result.Error(Exception("Some error occurred!"))
        }
    }

    override suspend fun saveNewData(list: List<Movies>) {
        deleteOldData()
        movies.addAll(list)
    }

    override suspend fun fetchFromDb(): Result<List<Movies>> {
        return if(!shouldFetchError) {
            Result.Success(movies)
        } else {
            Result.Error(Exception("Some error occurred!"))
        }
    }

    override suspend fun getMovieById(id: Long): Result<Movies> {
        return if(!shouldFetchError) {
            Result.Success(movies[0])
        } else {
            Result.Error(Exception("Cannot fetch movie by id!"))
        }
    }

    override suspend fun deleteOldData() {
        movies.clear()
    }

    fun setShouldFetchError(value: Boolean) {
        shouldFetchError = value
    }

    fun setShouldFetchFromDb(value: Boolean) {
        shouldFetchFromDb = value
    }

}