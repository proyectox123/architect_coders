package com.mho.training.features.keywords

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.domain.Keyword
import com.example.android.domain.result.DataResult
import com.example.android.usecases.GetKeywordListUseCase
import com.mho.training.bases.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class KeywordsViewModel(
    private val movieId: Int,
    private val getKeywordListUseCase: GetKeywordListUseCase,
    uiDispatcher: CoroutineDispatcher
) : BaseViewModel(uiDispatcher) {

    //region Constructors

    init {
        initScope()
    }

    //endregion

    //region Fields

    private val _keywords = MutableLiveData<List<Keyword>>()
    val keywords: LiveData<List<Keyword>> get() = _keywords

    private val _loadingKeywords = MutableLiveData<Boolean>()
    val loadingKeywords: LiveData<Boolean> get() = _loadingKeywords

    private val _hasNotKeywords = MutableLiveData<Boolean>()
    val hasNotKeywords: LiveData<Boolean> get() = _hasNotKeywords

    //endregion

    //region Public Methods

    fun onKeywordsFromMovie(){
        launch {
            _loadingKeywords.value = true
            validateKeywordResult(getKeywordListUseCase.invoke(movieId))
            _loadingKeywords.value = false
        }
    }

    //endregion

    //region Private Methods

    private fun validateKeywordResult(keywordListResult: DataResult<List<Keyword>>){
        when(keywordListResult){
            is DataResult.Success -> {
                _keywords.value = keywordListResult.data
                _hasNotKeywords.value = false
            }
            is DataResult.Error -> {
                _keywords.value = emptyList()
                _hasNotKeywords.value = true
            }
        }
    }

    //endregion
}