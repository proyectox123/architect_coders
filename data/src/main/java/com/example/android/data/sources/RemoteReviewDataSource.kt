package com.example.android.data.sources

import com.example.android.domain.Review
import com.example.android.domain.result.DataResult

interface RemoteReviewDataSource {
    suspend fun getReviewList(movieId: Int): DataResult<List<Review>>
}