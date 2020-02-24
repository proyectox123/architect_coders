package com.example.android.frameworkroom.data.daos

import androidx.room.*
import com.example.android.frameworkroom.data.tables.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie")
    fun getAll(): List<MovieEntity>

    @Query("SELECT * FROM Movie")
    fun getAllWithChanges(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM Movie WHERE movie_id = :id")
    fun findById(id: Int): MovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movieEntity: MovieEntity)

    @Delete
    fun deleteMovie(movieEntity: MovieEntity)

}