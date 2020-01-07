package com.example.android.framework.data.remote.requests.movie

import com.example.android.framework.data.remote.models.MovieResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    suspend fun getPopularMovieListAsync(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): Response<MovieResult>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovieListAsync(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): Response<MovieResult>

    @GET("movie/now_playing")
    suspend fun getInTheatersMovieListAsync(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): Response<MovieResult>
}