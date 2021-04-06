package com.example.binge.model.storage

import android.content.Context
import androidx.core.content.edit
import com.example.binge.R
import javax.inject.Inject

class SharedPreferencesStorage @Inject constructor(private val context: Context) : Storage {

    companion object {
        private const val SHARED_PREFERENCE_KEY = "SHARED_PREFERENCES"
        private const val LAST_SYNCED_KEY = "last_synced_time"
        private const val DARK_THEME_KEY = "dark_theme?"
    }

    private val sharedPref = context.getSharedPreferences(
        SHARED_PREFERENCE_KEY,
        Context.MODE_PRIVATE
    )

    override fun setLong(value: Long) {
        sharedPref.edit {
            putLong(LAST_SYNCED_KEY, value)
        }
    }

    override fun getLong(): Long {
        return sharedPref.getLong(LAST_SYNCED_KEY, -1)
    }

    override fun setBoolean(value: Boolean) {
        sharedPref.edit {
            putBoolean(DARK_THEME_KEY, value)
        }
    }

    override fun getBoolean(): Boolean {
        return sharedPref
            .getBoolean(DARK_THEME_KEY, false)
    }
}