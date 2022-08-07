package com.mho.training.features.reviews

import androidx.lifecycle.viewModelScope
import com.mho.training.bases.BaseViewModel
import com.mho.training.mvi.MviViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn

@FlowPreview
@ExperimentalCoroutinesApi
class ReviewsViewModel(
    private val reviewStateMachine: StateMachineForReview,
    uiDispatcher: CoroutineDispatcher
) : BaseViewModel(uiDispatcher), MviViewModel<ReviewIntent, ReviewViewState> {

    //region Constructors

    init {
        initScope()

        reviewStateMachine.processor.launchIn(viewModelScope)
    }

    //endregion

    //region Override Methods & Callbacks

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    override fun processIntent(intents: Flow<ReviewIntent>) {
        reviewStateMachine
            .processIntents(intents)
            .launchIn(viewModelScope)
    }

    override fun states(): Flow<ReviewViewState> = reviewStateMachine.viewState

    //endregion
}
