package com.mho.training.data.database.daos

import androidx.room.*
import com.mho.training.data.database.tables.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie")
    fun getAll(): List<MovieEntity>

    @Query("SELECT * FROM Movie WHERE movie_id = :id")
    fun findById(id: Int): MovieEntity

    @Query("SELECT COUNT(movie_id) FROM Movie")
    fun movieCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movies: List<MovieEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)
}