package com.mho.training.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mho.training.data.database.converters.DateConverter
import com.mho.training.data.database.daos.MovieDao
import com.mho.training.data.database.tables.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}