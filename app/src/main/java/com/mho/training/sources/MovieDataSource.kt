package com.mho.training.sources

import android.content.res.Resources
import com.example.android.data.sources.RemoteDataSource
import com.example.android.domain.Movie
import com.mho.training.BuildConfig
import com.mho.training.data.remote.requests.RetrofitRequest
import com.mho.training.data.translators.toDomainMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieDataSource(
    private val resources: Resources
): RemoteDataSource {

    override suspend fun getTopRatedMovieList(region: String): List<Movie> = withContext(Dispatchers.IO) {
        RetrofitRequest.service
            .getTopRatedMovieListAsync(BuildConfig.MOVIE_DB_API_KEY, region)
            .await()
            .body()
            ?.results
            ?.map { it.toDomainMovie(resources) }
            ?: mutableListOf()
    }

    override suspend fun getPopularMovieList(region: String): List<Movie> = withContext(Dispatchers.IO) {
        RetrofitRequest.service
            .getPopularMovieListAsync(BuildConfig.MOVIE_DB_API_KEY, region)
            .await()
            .body()
            ?.results
            ?.map { it.toDomainMovie(resources) }
            ?: mutableListOf()
    }

    override suspend fun getInTheatersMovieList(region: String): List<Movie> = withContext(Dispatchers.IO) {
        RetrofitRequest.service
            .getInTheatersMovieListAsync(BuildConfig.MOVIE_DB_API_KEY, region)
            .await()
            .body()
            ?.results
            ?.map { it.toDomainMovie(resources) }
            ?: mutableListOf()
    }
}