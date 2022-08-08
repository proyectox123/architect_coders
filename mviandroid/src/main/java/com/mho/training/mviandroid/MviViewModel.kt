package com.mho.training.mviandroid

import androidx.lifecycle.viewModelScope
import com.mho.training.mvi.*
import com.mho.training.mviandroid.bases.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn

@FlowPreview
@ExperimentalCoroutinesApi
abstract class MviViewModel<I: MviIntent, A: MviAction, S: MviViewState, R: MviResult>(
    private val stateMachine: MviStateMachine<I, A, S, R>,
    uiDispatcher: CoroutineDispatcher,
) : BaseViewModel(uiDispatcher) {

    //region Constructors

    init {
        stateMachine.processor.launchIn(viewModelScope)
    }

    //endregion

    //region Public Methods

    fun processIntent(intents: Flow<I>) {
        stateMachine
            .processIntents(intents)
            .launchIn(viewModelScope)
    }

    fun states(): Flow<S> = stateMachine.viewState

    //endregion
}
