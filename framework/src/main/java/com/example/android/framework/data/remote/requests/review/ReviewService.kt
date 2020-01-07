package com.example.android.framework.data.remote.requests.review

import com.example.android.framework.data.remote.models.ReviewResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewService {

    @GET("movie/{movie_id}/reviews")
    suspend fun  getReviewListByMovieAsync(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<ReviewResult>
}