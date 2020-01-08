package com.example.android.data.repositories

import com.example.android.data.sources.RemoteCreditDataSource
import com.example.android.domain.Credit
import com.example.android.framework.data.remote.requests.Result

class CreditRepository(
    private val remoteTrailerDataSource: RemoteCreditDataSource
){

    //region Public Methods

    suspend fun getTrailerList(movieId: Int): Result<List<Credit>> =
        remoteTrailerDataSource.getCreditList(movieId)

    //endregion

}