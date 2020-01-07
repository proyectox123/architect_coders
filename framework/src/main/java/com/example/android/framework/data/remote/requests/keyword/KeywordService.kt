package com.example.android.framework.data.remote.requests.keyword

import com.example.android.framework.data.remote.models.KeywordResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KeywordService {

    @GET("movie/{movie_id}/keywords")
    suspend fun getKeywordListByMovieAsync(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<KeywordResult>
}