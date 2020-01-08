package com.mho.training.sources

import android.content.res.Resources
import com.example.android.data.sources.RemoteCreditDataSource
import com.example.android.domain.Credit
import com.example.android.framework.data.remote.requests.Result
import com.example.android.framework.data.remote.requests.safeApiCall
import com.example.android.framework.data.remote.requests.credit.CreditRequest
import com.mho.training.BuildConfig
import com.mho.training.R
import com.mho.training.data.translators.toDomainCredit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class CreditDataSource(private val resources: Resources) : RemoteCreditDataSource {

    override suspend fun getCreditList(movieId: Int): Result<List<Credit>> = withContext(Dispatchers.IO) {
        safeApiCall(
            call = { requestCreditList(movieId) },
            errorMessage = resources.getString(R.string.error_unable_to_fetch_credits)
        )
    }

    private suspend fun requestCreditList(movieId: Int): Result<List<Credit>> {
        val response = CreditRequest.service
            .getCreditListByMovieAsync(movieId, BuildConfig.MOVIE_DB_API_KEY)

        if(response.isSuccessful){
            val results = response.body()?.results
            if (!results.isNullOrEmpty()) {
                return Result.Success(results.map { it.toDomainCredit() })
            }
        }

        return Result.Error(IOException(resources.getString(R.string.error_during_fetching_credits)))
    }
}