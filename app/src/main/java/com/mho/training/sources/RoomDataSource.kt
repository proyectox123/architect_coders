package com.mho.training.sources

import android.content.res.Resources
import com.example.android.data.sources.LocalDataSource
import com.example.android.domain.Movie
import com.mho.training.data.database.MovieDatabase
import com.mho.training.data.translators.toDomainMovie
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

}