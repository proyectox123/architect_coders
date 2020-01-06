package com.example.android.framework.data.remote.requests

import com.example.android.framework.data.remote.models.KeywordResult
import com.example.android.framework.data.remote.models.MovieResult
import com.example.android.framework.data.remote.models.ReviewResult
import com.example.android.framework.data.remote.models.TrailerResult
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("movie/popular")
    fun getPopularMovieListAsync(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): Deferred<Response<MovieResult>>

    @GET("movie/top_rated")
    fun getTopRatedMovieListAsync(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): Deferred<Response<MovieResult>>

    @GET("movie/now_playing")
    fun getInTheatersMovieListAsync(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): Deferred<Response<MovieResult>>

    @GET("movie/{movie_id}/keywords")
    fun getKeywordListByMovieAsync(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Deferred<Response<KeywordResult>>

    @GET("movie/{movie_id}/reviews")
    fun getReviewListByMovieAsync(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Deferred<Response<ReviewResult>>

    @GET("movie/{movie_id}/videos")
    fun getTrailerListByMovieAsync(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Deferred<Response<TrailerResult>>
}