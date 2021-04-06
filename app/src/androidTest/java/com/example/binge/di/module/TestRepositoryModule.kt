package com.example.binge.di.module

import com.example.binge.data.repository.FakeAndroidTestRepository
import com.example.binge.model.repository.MoviesRepo
import dagger.Binds
import dagger.Module

@Module
abstract class TestRepositoryModule {

    @Binds
    abstract fun provideRepository(repository: FakeAndroidTestRepository): MoviesRepo

}