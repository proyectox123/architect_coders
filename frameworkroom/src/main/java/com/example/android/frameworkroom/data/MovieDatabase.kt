package com.example.android.frameworkroom.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android.frameworkroom.data.converters.DateConverter
import com.example.android.frameworkroom.data.daos.MovieDao
import com.example.android.frameworkroom.data.tables.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        private const val DATABASE_NAME = "movie-db"

        @Synchronized
        fun getDatabase(context: Context): MovieDatabase = Room.databaseBuilder<MovieDatabase>(
            context.applicationContext,
            MovieDatabase::class.java,
            DATABASE_NAME
        ).build()

    }
}