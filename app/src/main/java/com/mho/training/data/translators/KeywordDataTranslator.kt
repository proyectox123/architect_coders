package com.mho.training.data.translators

import com.example.android.domain.Keyword
import com.example.android.framework.data.remote.models.keyword.ServerKeyword

fun ServerKeyword.toDomainKeyword(): Keyword =
    Keyword(
        id,
        name
    )