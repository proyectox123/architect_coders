package com.example.android.framework.data.remote.requests.review

import com.example.android.framework.data.remote.models.review.ReviewResult
import com.example.android.framework.utils.Constants.REQUEST_GET_REVIEW_LIST_BY_MOVIE_ID
import com.example.android.framework.utils.Constants.REQUEST_PARAM_API_KEY
import com.example.android.framework.utils.Constants.REQUEST_PARAM_MOVIE_ID
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewService {

    @GET(REQUEST_GET_REVIEW_LIST_BY_MOVIE_ID)
    suspend fun  getReviewListByMovieAsync(
        @Path(REQUEST_PARAM_MOVIE_ID) movieId: Int,
        @Query(REQUEST_PARAM_API_KEY) apiKey: String
    ): Response<ReviewResult>
}