package com.mho.training.features.reviews

import com.example.android.domain.Review
import com.example.android.domain.result.DataResult
import com.example.android.usecases.GetReviewListUseCase
import com.mho.training.bases.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReviewsViewModel(
    private val movieId: Int,
    private val getReviewListUseCase: GetReviewListUseCase,
    uiDispatcher: CoroutineDispatcher
) : BaseViewModel(uiDispatcher) {

    //region Constructors

    init {
        initScope()
    }

    //endregion

    //region Fields

    private val _reviews = MutableStateFlow(emptyList<Review>())
    val reviews: StateFlow<List<Review>> get() = _reviews

    private val _loadingReviews = MutableStateFlow(false)
    val loadingReviews: StateFlow<Boolean> get() = _loadingReviews

    private val _hasNotReviews = MutableStateFlow(true)
    val hasNotReviews: StateFlow<Boolean> get() = _hasNotReviews

    //endregion

    //region Override Methods & Callbacks

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    //endregion

    //region Public Methods

    fun onReviewsFromMovie(){
        launch {
            _loadingReviews.value = true
            validateReviewResult(getReviewListUseCase.invoke(movieId))
            _loadingReviews.value = false
        }
    }

    //endregion

    //region Private Methods

    private fun validateReviewResult(reviewListResult: DataResult<List<Review>>){
        when(reviewListResult){
            is DataResult.Success -> {
                _reviews.value = reviewListResult.data
                _hasNotReviews.value = false
            }
            is DataResult.Error -> {
                _reviews.value = emptyList()
                _hasNotReviews.value = true
            }
        }
    }

    //endregion

}