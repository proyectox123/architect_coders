package com.mho.training.mvi

import kotlinx.coroutines.flow.Flow

interface MviActionProcessor<in A : Mvi.Action, out R : Mvi.Result> {
    fun actionToResult(action: A): Flow<R>
}
