package com.mho.training.mvi

import kotlinx.coroutines.flow.Flow

interface MviActionInterpreter<in A : MviAction, out R : MviResult> {
    fun actionToResult(action: A): Flow<R>
}
