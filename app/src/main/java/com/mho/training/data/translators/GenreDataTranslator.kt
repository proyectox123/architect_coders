package com.mho.training.data.translators

import com.example.android.domain.Genre
import com.example.android.framework.data.remote.models.genre.ServerGenre

fun ServerGenre.toGenreDomain() = Genre(
    id,
    name
)