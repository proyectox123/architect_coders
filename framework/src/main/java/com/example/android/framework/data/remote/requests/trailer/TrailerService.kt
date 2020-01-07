package com.example.android.framework.data.remote.requests.trailer

import com.example.android.framework.data.remote.models.TrailerResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrailerService {

    @GET("movie/{movie_id}/videos")
    suspend fun getTrailerListByMovieAsync(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<TrailerResult>
}