package com.example.android.data.repositories

import com.example.android.data.sources.RemoteCreditDataSource
import com.example.android.domain.Credit
import com.example.android.domain.result.DataResult

class CreditRepository(
    private val remoteCreditDataSource: RemoteCreditDataSource
){

    //region Public Methods

    suspend fun getCreditList(movieId: Int): DataResult<List<Credit>> =
        remoteCreditDataSource.getCreditList(movieId)

    //endregion

}