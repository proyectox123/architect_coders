package com.mho.training.sources

import com.example.android.data.sources.RemoteKeywordDataSource
import com.example.android.domain.Keyword
import com.example.android.domain.result.DataResult
import com.example.android.domain.result.safeApiCall
import com.example.android.frameworkretrofit.data.requests.keyword.KeywordRequest
import com.mho.training.BuildConfig
import com.mho.training.data.translators.toDomainKeyword
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class KeywordServerDataSource(
    private val errorUnableToFetchKeywords: String,
    private val errorDuringFetchingKeywords: String,
    private val keywordRequest: KeywordRequest
) : RemoteKeywordDataSource {

    override suspend fun getKeywordList(movieId: Int): DataResult<List<Keyword>> = withContext(Dispatchers.IO) {
        safeApiCall(
            call = { requestKeywordList(movieId) },
            errorMessage = errorUnableToFetchKeywords
        )
    }

    private suspend fun requestKeywordList(movieId: Int) : DataResult<List<Keyword>> {
        val response = keywordRequest.service
            .getKeywordListByMovieAsync(movieId, BuildConfig.MOVIE_DB_API_KEY)

        if(response.isSuccessful){
            val results = response.body()?.results
            if (!results.isNullOrEmpty()) {
                return DataResult.Success(results.map { it.toDomainKeyword() })
            }
        }

        return DataResult.Error(IOException(errorDuringFetchingKeywords))
    }
}