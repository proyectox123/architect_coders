package com.example.android.data.repositories

import com.example.android.data.sources.RemoteKeywordDataSource
import com.example.android.domain.Keyword

class KeywordRepository(
    private val remoteKeywordDataSource: RemoteKeywordDataSource
){

    //region Public Methods

    suspend fun getKeywordList(movieId: Int): List<Keyword> =
        remoteKeywordDataSource.getKeywordList(movieId)

    //endregion

}