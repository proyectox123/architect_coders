package com.mho.training.mvi

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
@FlowPreview
abstract class MviStateMachine<I : MviIntent, A : MviAction, S : MviViewState, out R : MviResult>(
    private val intentInterpreter: MviIntentInterpreter<I, A>,
    private val actionProcessor: MviActionProcessor<A, R>,
    private val reducer: MviViewStateReducer<S, R>,
    initialState: S
) {

    private val viewStateFlow: MutableStateFlow<S> = MutableStateFlow(initialState)

    private val intentsChannel: MutableSharedFlow<I> = MutableSharedFlow(1)

    val viewState: Flow<S>
        get() = viewStateFlow

    val processor: Flow<S> = intentsChannel
        .map { intent -> intentInterpreter.intentToAction(intent) }
        .flatMapMerge { action -> actionProcessor.actionToResult(action) }
        .scan(initialState) { previous, result -> reducer.reduce(previous, result) }
        .onEach { state -> viewStateFlow.value = state }

    fun processIntents(intents: Flow<I>): Flow<I> = intents.onEach { viewIntents ->
        intentsChannel.emit(viewIntents)
    }
}
