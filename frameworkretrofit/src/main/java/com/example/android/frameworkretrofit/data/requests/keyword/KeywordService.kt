package com.example.android.frameworkretrofit.data.requests.keyword

import com.example.android.frameworkretrofit.data.models.keyword.KeywordResult
import com.example.android.frameworkretrofit.utils.Constants.REQUEST_GET_KEYWORD_LIST_BY_MOVIE_ID
import com.example.android.frameworkretrofit.utils.Constants.REQUEST_PARAM_API_KEY
import com.example.android.frameworkretrofit.utils.Constants.REQUEST_PARAM_MOVIE_ID
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KeywordService {

    @GET(REQUEST_GET_KEYWORD_LIST_BY_MOVIE_ID)
    suspend fun getKeywordListByMovieAsync(
        @Path(REQUEST_PARAM_MOVIE_ID) movieId: Int,
        @Query(REQUEST_PARAM_API_KEY) apiKey: String
    ): Response<KeywordResult>
}