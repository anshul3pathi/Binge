package com.example.binge.di.components

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import com.example.binge.di.modules.AppModule
import com.example.binge.di.modules.MoviesDataSourceModule
import com.example.binge.di.modules.RepositoryModule
import com.example.binge.di.modules.StorageModule
import com.example.binge.ui.activities.splash.SplashScreenActivity
import com.example.binge.ui.fragments.feed.MovieFeedFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: MovieFeedFragment)
    fun inject(activity: SplashScreenActivity)

}