package com.mho.training.features.trailers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.domain.Trailer
import com.example.android.domain.result.DataResult
import com.example.android.usecases.GetTrailerListUseCase
import com.mho.training.mviandroid.bases.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class TrailersViewModel(
    private val movieId: Int,
    private val getTrailerListUseCase: GetTrailerListUseCase,
    uiDispatcher: CoroutineDispatcher
) : BaseViewModel(uiDispatcher) {

    //region Constructors

    init {
        initScope()
    }

    //endregion

    //region Fields

    private val _trailers = MutableLiveData<List<Trailer>>()
    val trailers: LiveData<List<Trailer>> get() = _trailers

    private val _loadingTrailers = MutableLiveData<Boolean>()
    val loadingTrailers: LiveData<Boolean> get() = _loadingTrailers

    private val _hasNotTrailers = MutableLiveData<Boolean>()
    val hasNotTrailers: LiveData<Boolean> get() = _hasNotTrailers

    //endregion

    //region Override Methods & Callbacks

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    //endregion

    //region Public Methods

    fun onTrailersFromMovie(){
        launch {
            _loadingTrailers.value = true
            validateTrailerResult(getTrailerListUseCase.invoke(movieId))
            _loadingTrailers.value = false
        }
    }

    //endregion

    //region Private Methods

    private fun validateTrailerResult(trailerListResult: DataResult<List<Trailer>>){
        when(trailerListResult){
            is DataResult.Success -> {
                _trailers.value = trailerListResult.data
                _hasNotTrailers.value = false
            }
            is DataResult.Error -> {
                _trailers.value = emptyList()
                _hasNotTrailers.value = true
            }
        }
    }

    //endregion

}