package com.mho.training.mvi

import kotlinx.coroutines.flow.Flow

interface MviIntent

interface MviAction

interface MviResult

interface MviViewState

interface MviViewModel<I: MviIntent, S: MviViewState> {
    fun processIntent(intents: Flow<I>)
    fun states(): Flow<S>
}

interface MviView<I: MviIntent, in S: MviViewState> {
    fun intents(): Flow<I>
    fun render(state: S)
}

