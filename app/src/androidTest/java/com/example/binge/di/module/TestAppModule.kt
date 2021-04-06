package com.example.binge.di.module

import com.example.binge.di.modules.DatabaseModule
import com.example.binge.di.modules.MoviesDataSourceModule
import com.example.binge.di.modules.NetworkModule
import com.example.binge.di.modules.StorageModule
import dagger.Module

@Module(
    includes = [
        TestRepositoryModule::class,
        NetworkModule::class,
        MoviesDataSourceModule::class,
        StorageModule::class,
        DatabaseModule::class
    ]
)
class TestAppModule