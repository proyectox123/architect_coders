package com.mho.training.data.translators

import com.example.android.domain.Trailer
import com.example.android.frameworkretrofit.data.models.trailer.ServerTrailer
import com.mho.training.utils.Constants

fun ServerTrailer.toDomainTrailer(): Trailer =
    Trailer(
        id,
        name,
        generateTrailerThumbnail(key),
        generateTrailerVideoPath(key)
    )

private fun generateTrailerThumbnail(key: String) =
    Constants.URL_THUMBNAIL_YOUTUBE_VIDEO + key + Constants.URL_THUMBNAIL_YOUTUBE_VIDEO_PATH_DEFAULT

private fun generateTrailerVideoPath(key: String) =
    Constants.URL_YOUTUBE_VIDEO + key