package com.example.binge.di.modules

import com.example.binge.model.repository.MoviesRepo
import com.example.binge.model.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Module(includes = [CoroutineDispatcherModule::class])
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideRepository(repository: MoviesRepository): MoviesRepo

}