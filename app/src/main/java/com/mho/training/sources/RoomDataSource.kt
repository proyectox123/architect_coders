package com.mho.training.sources

import android.content.res.Resources
import com.example.android.data.sources.LocalDataSource
import com.example.android.domain.Movie
import com.mho.training.data.database.MovieDatabase
import com.mho.training.data.database.tables.MovieEntity
import com.mho.training.data.translators.toDomainMovie
import com.mho.training.data.translators.toEntityMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(
    db: MovieDatabase,
    private val resources: Resources
) : LocalDataSource {

    private val movieDao = db.movieDao()

    override suspend fun getFavoriteMovieList(): List<Movie> = withContext(Dispatchers.IO) {
        movieDao.getAll()
            .map { it.toDomainMovie(resources) }
    }

    override suspend fun getFavoriteMovieStatus(movie: Movie): Boolean = withContext(Dispatchers.IO) {
        movieDao.findById(movie.id) != null
    }

    override suspend fun updateFavoriteMovieStatus(movie: Movie): Boolean = withContext(Dispatchers.IO) {
        val movieEntity: MovieEntity = movie.toEntityMovie()
        if(movieDao.findById(movie.id) == null){
            movieDao.insertMovie(movieEntity)
            true
        }else{
            movieDao.deleteMovie(movieEntity)
            false
        }
    }
}