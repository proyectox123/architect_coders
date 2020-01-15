package com.example.android.data.repositories

import com.example.android.data.sources.RemoteKeywordDataSource
import com.example.android.domain.Keyword
import com.example.android.domain.result.DataResult

class KeywordRepository(
    private val remoteKeywordDataSource: RemoteKeywordDataSource
){

    //region Public Methods

    suspend fun getKeywordList(movieId: Int): DataResult<List<Keyword>> =
        remoteKeywordDataSource.getKeywordList(movieId)

    //endregion

}