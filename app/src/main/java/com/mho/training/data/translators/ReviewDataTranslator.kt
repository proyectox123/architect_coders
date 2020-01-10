package com.mho.training.data.translators

import com.example.android.domain.Review
import com.example.android.framework.data.remote.models.review.ServerReview

fun ServerReview.toDomainReview(): Review =
    Review(
        id,
        author,
        content,
        url
    )