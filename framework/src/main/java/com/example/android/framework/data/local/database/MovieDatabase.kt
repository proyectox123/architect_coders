package com.example.android.framework.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android.framework.data.local.database.converters.DateConverter
import com.example.android.framework.data.local.database.daos.MovieDao
import com.example.android.framework.data.local.database.tables.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        const val DATABASE_NAME = "movie-db"

        @Synchronized
        fun getDatabase(context: Context): MovieDatabase = Room.databaseBuilder<MovieDatabase>(
            context.applicationContext,
            MovieDatabase::class.java,
            DATABASE_NAME
        ).build()

    }
}