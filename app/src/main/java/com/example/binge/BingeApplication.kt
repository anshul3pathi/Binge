package com.example.binge

import android.app.Application
import com.example.binge.di.components.AppComponent
import com.example.binge.di.components.DaggerAppComponent
import com.example.binge.model.repository.MoviesRepo
import com.example.binge.model.storage.Storage
import kotlin.time.ExperimentalTime

@ExperimentalTime
open class BingeApplication : Application() {

//    val moviesRepository: MoviesRepo
//        get() = ServiceLocator.provideMoviesRepository(this)
//
//    val sharedPreference: Storage
//        get() = ServiceLocator.provideSharePreference(this)

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent() = DaggerAppComponent.factory().create(applicationContext)

}