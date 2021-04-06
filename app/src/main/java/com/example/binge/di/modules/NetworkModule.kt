package com.example.binge.di.modules

import com.example.binge.model.remote.IMDBApiService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [BaseNetworkModule::class])
class NetworkModule() {

    private val baseUrl = "https://raw.githubusercontent.com/theapache64/top250/master/"

    @Singleton
    @Provides
    fun provideIMDBApiServiceInterface(retrofit: Retrofit): IMDBApiService {
        return retrofit.create(IMDBApiService::class.java)
    }

}