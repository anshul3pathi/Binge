package com.example.binge.di.modules

import dagger.Module
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Module(
    includes = [
        RepositoryModule::class,
        NetworkModule::class,
        MoviesDataSourceModule::class,
        StorageModule::class,
        DatabaseModule::class
    ]
)
class AppModule