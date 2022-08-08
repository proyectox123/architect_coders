package com.mho.training.mvi

import kotlinx.coroutines.flow.Flow

interface MviActionProcessor<in A : MviAction, out R : MviResult> {
    fun actionToResult(action: A): Flow<R>
}
