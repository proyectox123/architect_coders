package com.example.android.data.sources

import com.example.android.domain.Keyword
import com.example.android.domain.result.DataResult

interface RemoteKeywordDataSource {
    suspend fun getKeywordList(movieId: Int): DataResult<List<Keyword>>
}