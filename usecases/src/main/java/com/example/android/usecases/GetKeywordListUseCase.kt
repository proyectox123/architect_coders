package com.example.android.usecases

import com.example.android.data.repositories.KeywordRepository
import com.example.android.domain.Keyword
import com.example.android.framework.data.remote.requests.Result

class GetKeywordListUseCase(private val keywordRepository: KeywordRepository){

    suspend fun invoke(movieId: Int): Result<List<Keyword>> =
        keywordRepository.getKeywordList(movieId)
}