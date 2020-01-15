package com.example.android.data.repositories

import com.example.android.data.sources.RemoteReviewDataSource
import com.example.android.domain.Review
import com.example.android.domain.result.DataResult

class ReviewRepository(
    private val remoteReviewDataSource: RemoteReviewDataSource
){

    //region Public Methods

    suspend fun getReviewList(movieId: Int): DataResult<List<Review>> =
        remoteReviewDataSource.getReviewList(movieId)

    //endregion

}