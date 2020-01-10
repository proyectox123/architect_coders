package com.example.android.framework.data.remote.requests.movie

import com.example.android.framework.data.remote.models.movie.MovieCreditsResult
import com.example.android.framework.data.remote.models.movie.MovieResult
import com.example.android.framework.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET(Constants.REQUEST_GET_MOVIE_LIST_BY_PERSON_ID)
    suspend fun getMovieListByPersonAsync(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String
    ): Response<MovieCreditsResult>
}