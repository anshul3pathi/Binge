package com.example.binge.di.modules

import com.example.binge.model.MoviesDataSource
import com.example.binge.model.database.LocalDataSource
import com.example.binge.model.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class RemoteDataSource

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class LocalDataSource

@Module
abstract class MoviesDataSourceModule {

    @com.example.binge.di.modules.RemoteDataSource
    @Binds
    abstract fun provideRemoteDataSource(remoteDataSource: RemoteDataSource): MoviesDataSource

    @com.example.binge.di.modules.LocalDataSource
    @Binds
    abstract fun provideLocalDataSource(localDataSource: LocalDataSource): MoviesDataSource

}