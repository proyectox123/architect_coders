package com.example.android.data.sources

import com.example.android.domain.Movie
import com.example.android.domain.MovieDetail
import com.example.android.domain.result.DataResult

interface RemoteDataSource {
    suspend fun getTopRatedMovieList(region: String): DataResult<List<Movie>>
    suspend fun getPopularMovieList(region: String): DataResult<List<Movie>>
    suspend fun getInTheatersMovieList(region: String): DataResult<List<Movie>>
    suspend fun getMovieListByPerson(personId: Int): DataResult<List<Movie>>
    suspend fun getMovieDetailById(movieId: Int): DataResult<MovieDetail>
}