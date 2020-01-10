package com.example.android.framework.data.remote.requests.credit

import com.example.android.framework.data.remote.models.credit.CreditResult
import com.example.android.framework.utils.Constants.REQUEST_GET_CREDITS_BY_MOVIE_ID
import com.example.android.framework.utils.Constants.REQUEST_PARAM_API_KEY
import com.example.android.framework.utils.Constants.REQUEST_PARAM_MOVIE_ID
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CreditService {

    @GET(REQUEST_GET_CREDITS_BY_MOVIE_ID)
    suspend fun getCreditListByMovieAsync(
        @Path(REQUEST_PARAM_MOVIE_ID) movieId: Int,
        @Query(REQUEST_PARAM_API_KEY) apiKey: String
    ): Response<CreditResult>
}