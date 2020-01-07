package com.example.android.data.sources

import com.example.android.domain.Review
import com.example.android.framework.data.remote.requests.Result

interface RemoteReviewDataSource {
    suspend fun getReviewList(movieId: Int): Result<List<Review>>
}