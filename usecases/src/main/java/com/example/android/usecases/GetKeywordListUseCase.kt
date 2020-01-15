package com.example.android.usecases

import com.example.android.data.repositories.KeywordRepository
import com.example.android.domain.Keyword
import com.example.android.domain.result.DataResult

class GetKeywordListUseCase(private val keywordRepository: KeywordRepository){

    suspend fun invoke(movieId: Int): DataResult<List<Keyword>> =
        keywordRepository.getKeywordList(movieId)
}