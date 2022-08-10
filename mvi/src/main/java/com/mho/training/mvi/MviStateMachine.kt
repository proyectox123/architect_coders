package com.mho.training.mvi

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
@FlowPreview
abstract class MviStateMachine<
        I : Mvi.Intent,
        A : Mvi.Action,
        S : Mvi.ViewState,
        out R : Mvi.Result,
        out SE: Mvi.SideEffect,
>(
    private val intentInterpreter: MviIntentInterpreter<I, A>,
    private val actionProcessor: MviActionProcessor<A, R>,
    private val reducer: MviViewStateReducer<S, R>,
    private val sideEffectFactory: MviSideEffectFactory<R, SE>,
    initialState: S,
    initialSideEffect: SE,
) {

    private val viewStateFlow: MutableStateFlow<S> = MutableStateFlow(initialState)

    private val sideEffectFlow: MutableStateFlow<SE> = MutableStateFlow(initialSideEffect)

    private val intentsChannel: MutableSharedFlow<I> = MutableSharedFlow(1)

    val viewState: Flow<S>
        get() = viewStateFlow

    val sideEffect: Flow<SE>
        get() = sideEffectFlow

    val processor: Flow<S> = intentsChannel
        .map { intent -> intentInterpreter.intentToAction(intent) }
        .flatMapMerge { action -> actionProcessor.actionToResult(action) }
        .scan(initialState) { previous, result ->
            sideEffectFlow.value = sideEffectFactory.resultToSideEffect(result)
            reducer.reduce(previous, result)
        }
        .onEach { state -> viewStateFlow.value = state }

    fun processIntents(intents: Flow<I>): Flow<I> = intents.onEach { viewIntents ->
        intentsChannel.emit(viewIntents)
    }
}
