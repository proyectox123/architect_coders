package com.mho.training.sources

import com.example.android.data.sources.RemoteKeywordDataSource
import com.example.android.domain.Keyword
import com.example.android.framework.data.remote.requests.RetrofitRequest
import com.mho.training.BuildConfig
import com.mho.training.data.translators.toDomainKeyword
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class KeywordDataSource : RemoteKeywordDataSource {

    override suspend fun getKeywordList(movieId: Int): List<Keyword> = withContext(Dispatchers.IO) {
        RetrofitRequest.service
            .getKeywordListByMovieAsync(movieId, BuildConfig.MOVIE_DB_API_KEY)
            .await()
            .body()
            ?.results
            ?.map { it.toDomainKeyword() }
            ?: mutableListOf()
    }
}