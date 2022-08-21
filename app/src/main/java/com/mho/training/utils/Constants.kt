package com.mho.training.utils

import com.mho.training.BuildConfig

object Constants {

    const val LESS_LINES_BIOGRAPHY = 5
    const val MAX_LINES_BIOGRAPHY = Int.MAX_VALUE

    const val DATE_FORMAT_PATTERN_INPUT = "yyyy-MM-dd"
    const val DATE_FORMAT_PATTERN_OUTPUT = "dd MMMM yyyy"

    const val EXTRA_MOVIE = BuildConfig.APPLICATION_ID + ".EXTRA_MOVIE"
    const val EXTRA_MOVIE_ID = BuildConfig.APPLICATION_ID + ".EXTRA_MOVIE_ID"
    const val EXTRA_CREDIT_ID = BuildConfig.APPLICATION_ID + ".EXTRA_CREDIT_ID"

    const val URL_IMAGE_TBMD = "http://image.tmdb.org/t/p/w185/"
}