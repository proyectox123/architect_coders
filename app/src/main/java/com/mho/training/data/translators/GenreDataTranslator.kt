package com.mho.training.data.translators

import com.example.android.domain.Genre
import com.example.android.frameworkretrofit.data.models.genre.ServerGenre

fun ServerGenre.toGenreDomain() = Genre(
    id,
    name
)