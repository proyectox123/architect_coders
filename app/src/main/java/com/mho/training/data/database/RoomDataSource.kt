package com.mho.training.data.database

import android.content.res.Resources
import com.mho.training.data.source.LocalDataSource
import com.mho.training.data.translators.toDomainMovie
import com.mho.training.domain.Movie
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