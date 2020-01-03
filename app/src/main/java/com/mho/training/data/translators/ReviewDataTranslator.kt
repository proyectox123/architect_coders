package com.mho.training.data.translators

import com.example.android.domain.Review
import com.mho.training.data.remote.models.ServerReview

fun ServerReview.toDomainReview(): Review =
    Review(
        id,
        author,
        content,
        url
    )