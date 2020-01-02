package com.mho.training.data

import com.mho.training.data.source.RemoteReviewDataSource
import com.mho.training.domain.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReviewRepository(
    private val remoteReviewDataSource: RemoteReviewDataSource
){

    //region Public Methods

    suspend fun getReviewList(movieId: Int): List<Review> = withContext(Dispatchers.IO) {
        remoteReviewDataSource.getReviewList(movieId)
    }

    //endregion

}