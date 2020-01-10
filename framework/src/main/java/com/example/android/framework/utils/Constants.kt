package com.example.android.framework.utils

object Constants {

    const val REQUEST_GET_CREDITS_BY_MOVIE_ID = "movie/{movie_id}/credits"
    const val REQUEST_GET_IN_THEATERS_MOVIE_LIST = "movie/now_playing"
    const val REQUEST_GET_KEYWORD_LIST_BY_MOVIE_ID = "movie/{movie_id}/keywords"
    const val REQUEST_GET_MOVIE_LIST_BY_PERSON_ID = "person/{person_id}/movie_credits"
    const val REQUEST_GET_PERSON_BY_CREDIT_ID = "person/{person_id}"
    const val REQUEST_GET_POPULAR_MOVIE_LIST = "movie/popular"
    const val REQUEST_GET_REVIEW_LIST_BY_MOVIE_ID = "movie/{movie_id}/reviews"
    const val REQUEST_GET_TOP_RATED_MOVIE_LIST = "movie/top_rated"
    const val REQUEST_GET_TRAILER_LIST_BY_MOVIE_ID = "movie/{movie_id}/videos"

    const val REQUEST_PARAM_API_KEY = "api_key"
    const val REQUEST_PARAM_MOVIE_ID = "movie_id"
    const val REQUEST_PARAM_PERSON_ID = "person_id"
    const val REQUEST_PARAM_REGION = "region"

    const val SIMPLE_DATE_FORMAT = "yyyy-MM-dd"

}