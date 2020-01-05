package com.example.android.usecases

import com.example.android.data.repositories.KeywordRepository
import com.example.android.domain.Keyword

class GetKeywordListUseCase(private val keywordRepository: KeywordRepository){

    suspend fun invoke(movieId: Int): List<Keyword> =
        keywordRepository.getKeywordList(movieId)
}