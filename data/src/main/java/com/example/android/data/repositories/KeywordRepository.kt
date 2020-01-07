package com.example.android.data.repositories

import com.example.android.data.sources.RemoteKeywordDataSource
import com.example.android.domain.Keyword
import com.example.android.framework.data.remote.requests.Result

class KeywordRepository(
    private val remoteKeywordDataSource: RemoteKeywordDataSource
){

    //region Public Methods

    suspend fun getKeywordList(movieId: Int): Result<List<Keyword>> =
        remoteKeywordDataSource.getKeywordList(movieId)

    //endregion

}