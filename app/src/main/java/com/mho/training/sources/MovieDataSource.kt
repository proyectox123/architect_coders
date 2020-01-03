package com.mho.training.sources

import android.content.res.Resources
import com.example.android.data.sources.RemoteDataSource
import com.example.android.domain.Movie
import com.mho.training.data.remote.requests.movies.MoviePopularRequest
import com.mho.training.data.remote.requests.movies.MovieTopRatedRequest
import com.mho.training.data.translators.toDomainMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDataSource(
    private val resources: Resources
): RemoteDataSource {

    override suspend fun getTopRatedMovieList(region: String): List<Movie> = withContext(Dispatchers.IO) {
        MovieTopRatedRequest(region)
            .requestMovieList()
            .map { it.toDomainMovie(resources) }
    }

    override suspend fun getPopularMovieList(region: String): List<Movie> =
        MoviePopularRequest(region)
            .requestMovieList()
            .map { it.toDomainMovie(resources) }
}