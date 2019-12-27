package com.mho.training.data

import com.mho.training.MoviesApp
import com.mho.training.data.database.tables.MovieEntity
import com.mho.training.data.remote.requests.movies.MoviePopularRequest
import com.mho.training.data.remote.requests.movies.MovieTopRatedRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(application: MoviesApp) {

    //region Fields

    private val db = application.db

    //endregion

    //region Public Methods

    suspend fun requestTopRatedMovieList(): List<MovieEntity> = withContext(Dispatchers.IO) {
        MovieTopRatedRequest().requestMovieList() ?: mutableListOf()
    }

    suspend fun requestPopularMovieList(): List<MovieEntity> = withContext(Dispatchers.IO) {
        MoviePopularRequest().requestMovieList() ?: mutableListOf()
    }

    suspend fun findFavoriteMovies(): List<MovieEntity> = withContext(Dispatchers.IO) {
        db.movieDao().getAll()
    }

    //endregion

}