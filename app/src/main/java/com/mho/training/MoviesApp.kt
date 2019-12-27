package com.mho.training

import android.app.Application
import androidx.room.Room
import com.mho.training.data.database.MovieDatabase

class MoviesApp : Application() {

    lateinit var db: MovieDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            MovieDatabase::class.java, "movie-db"
        ).build()
    }
}