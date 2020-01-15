package com.example.android.framework.data.remote.requests.movie

import com.example.android.framework.data.remote.models.movie.MovieCreditsResult
import com.example.android.framework.data.remote.models.movie.MovieDetailResult
import com.example.android.framework.data.remote.models.movie.MovieResult
import com.example.android.framework.utils.Constants.REQUEST_GET_IN_THEATERS_MOVIE_LIST
import com.example.android.framework.utils.Constants.REQUEST_GET_MOVIE_DETAIL_BY_ID
import com.example.android.framework.utils.Constants.REQUEST_GET_MOVIE_LIST_BY_PERSON_ID
import com.example.android.framework.utils.Constants.REQUEST_GET_POPULAR_MOVIE_LIST
import com.example.android.framework.utils.Constants.REQUEST_GET_TOP_RATED_MOVIE_LIST
import com.example.android.framework.utils.Constants.REQUEST_PARAM_API_KEY
import com.example.android.framework.utils.Constants.REQUEST_PARAM_MOVIE_ID
import com.example.android.framework.utils.Constants.REQUEST_PARAM_PERSON_ID
import com.example.android.framework.utils.Constants.REQUEST_PARAM_REGION
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET(REQUEST_GET_POPULAR_MOVIE_LIST)
    suspend fun getPopularMovieListAsync(
        @Query(REQUEST_PARAM_API_KEY) apiKey: String,
        @Query(REQUEST_PARAM_REGION) region: String
    ): Response<MovieResult>

    @GET(REQUEST_GET_TOP_RATED_MOVIE_LIST)
    suspend fun getTopRatedMovieListAsync(
        @Query(REQUEST_PARAM_API_KEY) apiKey: String,
        @Query(REQUEST_PARAM_REGION) region: String
    ): Response<MovieResult>

    @GET(REQUEST_GET_IN_THEATERS_MOVIE_LIST)
    suspend fun getInTheatersMovieListAsync(
        @Query(REQUEST_PARAM_API_KEY) apiKey: String,
        @Query(REQUEST_PARAM_REGION) region: String
    ): Response<MovieResult>

    @GET(REQUEST_GET_MOVIE_LIST_BY_PERSON_ID)
    suspend fun getMovieListByPersonAsync(
        @Path(REQUEST_PARAM_PERSON_ID) personId: Int,
        @Query(REQUEST_PARAM_API_KEY) apiKey: String
    ): Response<MovieCreditsResult>

    @GET(REQUEST_GET_MOVIE_DETAIL_BY_ID)
    suspend fun getMovieDetailByIdAsync(
        @Path(REQUEST_PARAM_MOVIE_ID) movieId: Int,
        @Query(REQUEST_PARAM_API_KEY) apiKey: String
    ): Response<MovieDetailResult>
}