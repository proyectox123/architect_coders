package com.mho.training.data

import com.mho.training.data.source.LocalDataSource
import com.mho.training.data.source.RemoteDataSource
import com.mho.training.domain.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    //region Public Methods

    suspend fun getTopRatedMovieList(): List<Movie> = withContext(Dispatchers.IO) {
        remoteDataSource.getTopRatedMovieList()
    }

    suspend fun getPopularMovieList(): List<Movie> = withContext(Dispatchers.IO) {
        remoteDataSource.getPopularMovieList()
    }

    suspend fun getFavoriteMovieList(): List<Movie> = withContext(Dispatchers.IO) {
        localDataSource.getFavoriteMovieList()
    }

    //endregion

}