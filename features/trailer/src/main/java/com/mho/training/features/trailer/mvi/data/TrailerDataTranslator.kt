package com.mho.training.features.trailer.mvi.data

import com.example.android.domain.Trailer
import com.example.android.frameworkretrofit.data.models.trailer.ServerTrailer

private const val URL_THUMBNAIL_YOUTUBE_VIDEO = "http://img.youtube.com/vi/"
private const val URL_THUMBNAIL_YOUTUBE_VIDEO_PATH_DEFAULT = "/hqdefault.jpg"
private const val URL_YOUTUBE_VIDEO = "https://www.youtube.com/watch?v="

fun ServerTrailer.toDomainTrailer(): Trailer =
    Trailer(
        id,
        name,
        generateTrailerThumbnail(key),
        generateTrailerVideoPath(key)
    )

private fun generateTrailerThumbnail(key: String) =
    URL_THUMBNAIL_YOUTUBE_VIDEO + key + URL_THUMBNAIL_YOUTUBE_VIDEO_PATH_DEFAULT

private fun generateTrailerVideoPath(key: String) = URL_YOUTUBE_VIDEO + key
