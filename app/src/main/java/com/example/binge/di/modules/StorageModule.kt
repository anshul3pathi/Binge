package com.example.binge.di.modules

import com.example.binge.model.storage.SharedPreferencesStorage
import com.example.binge.model.storage.Storage
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class StorageModule {

    @Binds
    abstract fun provideStorage(storage: SharedPreferencesStorage): Storage

}