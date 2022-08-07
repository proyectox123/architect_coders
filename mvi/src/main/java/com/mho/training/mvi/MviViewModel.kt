package com.mho.training.mvi

import kotlinx.coroutines.flow.Flow

interface MviViewModel<I: MviIntent, S: MviViewState> {
    fun processIntent(intents: Flow<I>)
    fun states(): Flow<S>
}
