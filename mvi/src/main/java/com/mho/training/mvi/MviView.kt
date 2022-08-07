package com.mho.training.mvi

import kotlinx.coroutines.flow.Flow

interface MviView<I: MviIntent, in S: MviViewState> {
    fun intents(): Flow<I>
    fun render(state: S)
}
