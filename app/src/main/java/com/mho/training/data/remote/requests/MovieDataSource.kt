package com.mho.training.data.remote.requests

import com.mho.training.data.remote.requests.movies.MoviePopularRequest
import com.mho.training.data.remote.requests.movies.MovieTopRatedRequest
import com.mho.training.data.source.RemoteDataSource
import com.mho.training.data.translators.toDomainMovie
import com.mho.training.domain.Movie

class MovieDataSource: RemoteDataSource {

    override suspend fun getTopRatedMovieList(region: String): List<Movie> =
        MovieTopRatedRequest(region)
            .requestMovieList()
            .map { it.toDomainMovie() }

    override suspend fun getPopularMovieList(region: String): List<Movie> =
        MoviePopularRequest(region)
            .requestMovieList()
            .map { it.toDomainMovie() }
}