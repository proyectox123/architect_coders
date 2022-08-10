package com.mho.training.mviandroid.utils

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.SendChannel

@ExperimentalCoroutinesApi
fun <E> SendChannel<E>.safeOffer(value: E): Boolean = !isClosedForSend && try {
    trySend(value).isSuccess
} catch (e: CancellationException) {
    false
}
