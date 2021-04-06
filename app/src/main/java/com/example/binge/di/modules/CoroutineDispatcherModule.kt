package com.example.binge.di.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class CoroutineDispatcherModule {

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

}
