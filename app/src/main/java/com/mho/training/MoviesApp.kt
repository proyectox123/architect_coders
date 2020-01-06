package com.mho.training

import android.app.Application
import com.example.android.framework.data.local.database.MovieDatabase

class MoviesApp : Application() {

    lateinit var db: MovieDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = MovieDatabase.getDatabase(this)
    }
}