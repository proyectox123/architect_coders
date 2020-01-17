package com.mho.training.sources

import android.content.res.Resources
import com.example.android.data.sources.LocalDataSource
import com.example.android.domain.Movie
import com.example.android.domain.result.DataResult
import com.example.android.domain.result.safeApiCall
import com.example.android.framework.data.local.database.MovieDatabase
import com.example.android.framework.data.local.database.tables.MovieEntity
import com.mho.training.data.translators.toDomainMovie
import com.mho.training.data.translators.toEntityMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException

class MovieRoomDataSource(
    db: MovieDatabase,
    private val resources: Resources,
    private val errorUnableToFetchMovies: String,
    private val errorDuringFetchingMovies: String
) : LocalDataSource {

    //region Fields

    private val movieDao = db.movieDao()

    //endregion

    //region Override Methods & Callbacks

    override suspend fun getFavoriteMovieList(): DataResult<List<Movie>> = withContext(Dispatchers.IO) {
        safeApiCall(
            call = { requestFavoriteMovieList() },
            errorMessage = errorUnableToFetchMovies
        )
    }

    override fun getFavoriteMovieListWithChanges(): Flow<List<Movie>> =
        movieDao.getAllWithChanges().map { movieList -> movieList.map { it.toDomainMovie(resources) } }

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

    //endregion

    //region Private Methods

    private fun requestFavoriteMovieList(): DataResult<List<Movie>> {
        val results = movieDao.getAll()

        if (!results.isNullOrEmpty()) {
            return DataResult.Success(results.map { it.toDomainMovie(resources) })
        }

        return DataResult.Error(IOException(errorDuringFetchingMovies))
    }

    //endregion
}