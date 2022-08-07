package com.mho.training.mvi

import kotlinx.coroutines.flow.Flow

interface MviIntentProcessor<in I : MviIntent, out R : MviResult> {
    fun intentToResult(viewIntent: I): Flow<R>
}
