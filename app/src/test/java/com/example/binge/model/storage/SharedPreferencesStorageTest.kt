package com.example.binge.model.storage

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.binge.BingeApplication
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import kotlin.time.ExperimentalTime

@RunWith(AndroidJUnit4::class)
class SharedPreferencesStorageTest {

    private lateinit var sharedPref: SharedPreferencesStorage

    @ExperimentalTime
    @Before
    fun init() {
        val appContext = ApplicationProvider.getApplicationContext<BingeApplication>()
        sharedPref = SharedPreferencesStorage(appContext)
    }

    @Test
    fun setLong_value_value() {
        val value = 19081L

        //  When value is given to sharedPref
        sharedPref.setLong(value)

        //  Then sharedPref.getLong should fetch the same value
        assertThat(sharedPref.getLong(), `is`(value))
    }

    @Test
    fun setBoolean_value_value() {
        val value = false

        //  When value if given to sharedPref.setBoolean
        sharedPref.setBoolean(value)

        //  Then sharedPref.getBoolean should fetch the same value
        assertThat(sharedPref.getBoolean(), `is`(value))
    }

}