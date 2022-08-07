package com.mho.training.mvi

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
@FlowPreview
abstract class MviStateMachine<I : MviIntent, S : MviViewState, out R : MviResult>(
    private val intentProcessor: MviIntentProcessor<I, R>,
    private val reducer: MviViewStateReducer<S, R>,
    initialIntent: I,
    initialState: S
) {

    private val viewStateFlow: MutableStateFlow<S> = MutableStateFlow(initialState)

    private val intentsChannel: MutableSharedFlow<I> = MutableSharedFlow<I>(1).apply {
        tryEmit(initialIntent)
    }

    val viewState: Flow<S>
        get() = viewStateFlow

    val processor: Flow<S> = intentsChannel
        .flatMapMerge { action -> intentProcessor.intentToResult(action) }
        .scan(initialState) { previous, result -> reducer.reduce(previous, result) }
        .onEach { state -> viewStateFlow.value = state }

    fun processIntents(intents: Flow<I>): Flow<I> = intents.onEach { viewIntents ->
        intentsChannel.emit(viewIntents)
    }
}
