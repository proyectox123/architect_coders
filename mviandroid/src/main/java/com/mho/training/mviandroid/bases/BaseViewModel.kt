package com.mho.training.mviandroid.bases

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.mho.training.mviandroid.utils.Scope
import kotlinx.coroutines.CoroutineDispatcher

abstract class BaseViewModel(uiDispatcher: CoroutineDispatcher) : ViewModel(),
    Scope by Scope.Impl(uiDispatcher) {

    init {
        this.initScope()
    }

    @CallSuper
    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}
