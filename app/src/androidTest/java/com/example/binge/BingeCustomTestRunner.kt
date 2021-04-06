package com.example.binge

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import kotlin.time.ExperimentalTime

class BingeCustomTestRunner : AndroidJUnitRunner() {

    @ExperimentalTime
    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, BingeTestApplication::class.java.name, context)
    }

}