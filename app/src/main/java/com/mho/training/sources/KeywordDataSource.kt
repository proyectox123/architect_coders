package com.mho.training.sources

import android.content.res.Resources
import com.example.android.data.sources.RemoteKeywordDataSource
import com.example.android.domain.Keyword
import com.example.android.framework.data.remote.requests.Result
import com.example.android.framework.data.remote.requests.keyword.KeywordRequest
import com.example.android.framework.data.remote.requests.safeApiCall
import com.mho.training.BuildConfig
import com.mho.training.R
import com.mho.training.data.translators.toDomainKeyword
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class KeywordDataSource(private val resources: Resources) : RemoteKeywordDataSource {

    override suspend fun getKeywordList(movieId: Int): Result<List<Keyword>> = withContext(Dispatchers.IO) {
        safeApiCall(
            call = { requestKeywordList(movieId) },
            errorMessage = resources.getString(R.string.error_unable_to_fetch_keywords)
        )
    }

    private suspend fun requestKeywordList(movieId: Int) : Result<List<Keyword>> {
        val response = KeywordRequest.service
            .getKeywordListByMovieAsync(movieId, BuildConfig.MOVIE_DB_API_KEY)

        if(response.isSuccessful){
            val results = response.body()?.results
            if (!results.isNullOrEmpty()) {
                return Result.Success(results.map { it.toDomainKeyword() })
            }
        }

        return Result.Error(IOException(resources.getString(R.string.error_during_fetching_keywords)))
    }
}