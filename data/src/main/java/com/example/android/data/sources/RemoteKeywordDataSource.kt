package com.example.android.data.sources

import com.example.android.domain.Keyword

interface RemoteKeywordDataSource {
    suspend fun getKeywordList(movieId: Int): List<Keyword>
}