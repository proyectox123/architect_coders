package com.example.android.data.sources

import com.example.android.domain.Keyword
import com.example.android.framework.data.remote.requests.Result

interface RemoteKeywordDataSource {
    suspend fun getKeywordList(movieId: Int): Result<List<Keyword>>
}