package com.mho.training.data.source

import com.mho.training.domain.Review

interface RemoteReviewDataSource {
    suspend fun getReviewList(movieId: Int): List<Review>
}