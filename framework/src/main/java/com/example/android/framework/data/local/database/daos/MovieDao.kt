package com.example.android.framework.data.local.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.framework.data.local.database.tables.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie")
    fun getAll(): List<MovieEntity>

    @Query("SELECT * FROM Movie")
    fun getAllWithChanges(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM Movie WHERE movie_id = :id")
    fun findById(id: Int): MovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movieEntity: MovieEntity)

    @Delete
    fun deleteMovie(movieEntity: MovieEntity)

}