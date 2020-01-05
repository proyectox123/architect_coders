package com.mho.training.data.translators

import com.example.android.domain.Keyword
import com.mho.training.data.remote.models.ServerKeyword

fun ServerKeyword.toDomainKeyword(): Keyword =
    Keyword(
        id,
        name
    )