package com.example.binge

import androidx.test.core.app.ApplicationProvider
import com.example.binge.di.component.DaggerTestAppComponent
import com.example.binge.di.components.AppComponent
import kotlin.time.ExperimentalTime

/**
 * BingeTestApplication will override MyApplication in android tests
 */
@ExperimentalTime
class BingeTestApplication : BingeApplication() {
    override fun initializeComponent(): AppComponent {
        // Creates a new TestAppComponent that injects fakes types
        return DaggerTestAppComponent.factory().create(ApplicationProvider.getApplicationContext())
    }
}