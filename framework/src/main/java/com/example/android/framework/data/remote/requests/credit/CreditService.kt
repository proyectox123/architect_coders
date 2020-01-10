package com.example.android.framework.data.remote.requests.credit

import com.example.android.framework.data.remote.models.credit.CreditResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CreditService {

    @GET("movie/{movie_id}/credits")
    suspend fun getCreditListByMovieAsync(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<CreditResult>
}