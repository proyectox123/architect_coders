package com.example.android.usecases

import com.example.android.data.repositories.CreditRepository
import com.example.android.domain.Credit
import com.example.android.domain.result.DataResult

class GetCreditListUseCase(private val creditRepository: CreditRepository){

    suspend fun invoke(movieId: Int): DataResult<List<Credit>> =
        creditRepository.getTrailerList(movieId)
}