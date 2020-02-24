package com.mho.training.features.keywords

import com.example.android.data.repositories.KeywordRepository
import com.example.android.usecases.GetKeywordListUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.Dispatchers

@Module
class KeywordsFragmentModule(
    private val movieId: Int
) {

    @Provides
    fun movieDetailViewModelProvider(
        getKeywordListUseCase: GetKeywordListUseCase
    ) = KeywordsViewModel(
        movieId,
        getKeywordListUseCase,
        Dispatchers.Main
    )

    @Provides
    fun getKeywordListUseCaseProvider(keywordRepository: KeywordRepository) =
        GetKeywordListUseCase(keywordRepository)

}

@Subcomponent(modules = [(KeywordsFragmentModule::class)])
interface KeywordsFragmentComponent {
    val keywordsViewModel: KeywordsViewModel
}