package com.example.binge
//
//import android.content.Context
//import androidx.annotation.VisibleForTesting
//import androidx.room.Room
//import com.example.binge.model.MoviesDataSource
//import com.example.binge.model.database.LocalDataSource
//import com.example.binge.model.database.MoviesDataBase
//import com.example.binge.model.remote.IMDBApi
//import com.example.binge.model.remote.IMDBApiService
//import com.example.binge.model.remote.RemoteDataSource
//import com.example.binge.model.repository.MoviesRepo
//import com.example.binge.model.repository.MoviesRepository
//import com.example.binge.model.storage.SharedPreferencesStorage
//import com.example.binge.model.storage.Storage
//import kotlinx.coroutines.Dispatchers
//import kotlin.time.ExperimentalTime
//
//@ExperimentalTime
//object ServiceLocator {
//
//    private var database: MoviesDataBase? = null
//    @Volatile
//    var moviesRepository: MoviesRepo? = null
//        @VisibleForTesting set
//
//    private var sharedPref: Storage? = null
//
//    private val lock = Any()
//
//    @VisibleForTesting
//    fun resetRepository() {
//        synchronized(lock) {
//            database?.apply {
//                clearAllTables()
//                close()
//            }
//            database = null
//            moviesRepository = null
//        }
//    }
//
//    fun provideMoviesRepository(context: Context): MoviesRepo {
//        synchronized(this) {
//            return  moviesRepository?: createMoviesRepository(context)
//        }
//    }
//
//    private fun createMoviesRepository(context: Context): MoviesRepo {
//        val newRepo = MoviesRepository(
//            provideSharePreference(context),
//        )
//        moviesRepository = newRepo
//        return newRepo
//    }
//
//    private fun createTaskLocalDataSource(context: Context): MoviesDataSource {
//        val database = database ?: createDataBase(context)
//        return LocalDataSource(database.moviesDao())
//    }
//
//    private fun createDataBase(context: Context): MoviesDataBase {
//        val result = Room.databaseBuilder(
//            context.applicationContext,
//            MoviesDataBase::class.java, "moviesDb"
//        ).build()
//        database = result
//        return result
//    }
//
//    fun provideSharePreference(context: Context): Storage {
//        return sharedPref ?: createSharedPreference(context)
//    }
//
//    private fun createSharedPreference(context: Context): Storage {
//        val storage = SharedPreferencesStorage(context)
//        sharedPref = storage
//        return storage
//    }
//}