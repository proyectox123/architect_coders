package com.mho.training.features.credits

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.domain.Credit
import com.example.android.domain.result.DataResult
import com.example.android.usecases.GetCreditListUseCase
import com.mho.training.mviandroid.bases.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class CreditsViewModel(
    private val movieId: Int,
    private val getCreditListUseCase: GetCreditListUseCase,
    uiDispatcher: CoroutineDispatcher
) : BaseViewModel(uiDispatcher) {

    //region Constructors

    init {
        initScope()
    }

    //endregion

    //region Fields

    private val _credits = MutableLiveData<List<Credit>>()
    val credits: LiveData<List<Credit>> get() = _credits

    private val _loadingCredits = MutableLiveData<Boolean>()
    val loadingCredits: LiveData<Boolean> get() = _loadingCredits

    private val _hasNotCredits = MutableLiveData<Boolean>()
    val hasNotCredits: LiveData<Boolean> get() = _hasNotCredits

    //endregion

    //region Override Methods & Callbacks

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    //endregion

    //region Public Methods

    fun onCreditsFromMovie(){
        launch {
            _loadingCredits.value = true
            validateCreditResult(getCreditListUseCase.invoke(movieId))
            _loadingCredits.value = false
        }
    }

    //endregion

    //region Private Methods

    private fun validateCreditResult(creditListResult: DataResult<List<Credit>>){
        when(creditListResult){
            is DataResult.Success -> {
                _credits.value = creditListResult.data
                _hasNotCredits.value = false
            }
            is DataResult.Error -> {
                _credits.value = emptyList()
                _hasNotCredits.value = true
            }
        }
    }

    //endregion

}