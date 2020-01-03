package com.example.android.data.sources

import com.example.android.domain.Review

interface RemoteReviewDataSource {
    suspend fun getReviewList(movieId: Int): List<Review>
}