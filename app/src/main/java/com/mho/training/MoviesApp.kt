package com.mho.training

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.android.framework.data.local.database.MovieDatabase

class MoviesApp : Application() {

    lateinit var db: MovieDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        db = MovieDatabase.getDatabase(this)
    }
}