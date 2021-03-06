package com.mho.training.sources

import com.example.android.data.sources.RemoteCreditDataSource
import com.example.android.domain.Credit
import com.example.android.domain.result.DataResult
import com.example.android.domain.result.safeApiCall
import com.example.android.frameworkretrofit.data.requests.credit.CreditRequest
import com.mho.training.BuildConfig
import com.mho.training.data.translators.toDomainCredit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class CreditServerDataSource(
    private val errorUnableToFetchCredits: String,
    private val errorDuringFetchingCredits: String,
    private val creditRequest: CreditRequest
) : RemoteCreditDataSource {

    override suspend fun getCreditList(movieId: Int): DataResult<List<Credit>> = withContext(Dispatchers.IO) {
        safeApiCall(
            call = { requestCreditList(movieId) },
            errorMessage = errorUnableToFetchCredits
        )
    }

    private suspend fun requestCreditList(movieId: Int): DataResult<List<Credit>> {
        val response = creditRequest.service
            .getCreditListByMovieAsync(movieId, BuildConfig.MOVIE_DB_API_KEY)

        if(response.isSuccessful){
            val results = response.body()?.results
            if (!results.isNullOrEmpty()) {
                return DataResult.Success(results.map { it.toDomainCredit() })
            }
        }

        return DataResult.Error(IOException(errorDuringFetchingCredits))
    }
}