package com.example.binge.di.modules

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module(includes = [MoshiModule::class])
class BaseNetworkModule {

    private val baseUrl = "https://raw.githubusercontent.com/theapache64/top250/master/"

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(this.baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

}