package com.example.android.usecases

import com.example.android.data.repositories.CreditRepository
import com.example.android.domain.Credit
import com.example.android.framework.data.remote.requests.Result

class GetCreditListUseCase(private val creditRepository: CreditRepository){

    suspend fun invoke(movieId: Int): Result<List<Credit>> =
        creditRepository.getTrailerList(movieId)
}