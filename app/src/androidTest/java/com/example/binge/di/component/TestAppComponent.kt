package com.example.binge.di.component

import android.content.Context
import com.example.binge.di.components.AppComponent
import com.example.binge.di.module.TestAppModule
import com.example.binge.di.module.TestRepositoryModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
import kotlin.time.ExperimentalTime

@ExperimentalTime
@Singleton
@Component(modules = [TestAppModule::class])
interface TestAppComponent : AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

}