package com.mho.training.mviandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.mho.training.mvi.MviAction
import com.mho.training.mvi.MviIntent
import com.mho.training.mvi.MviResult
import com.mho.training.mvi.MviViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
@FlowPreview
abstract class MviFragment<
        I: MviIntent,
        A: MviAction,
        S: MviViewState,
        R: MviResult,
        VM: MviViewModel<I, A, S, R>,
        B: ViewDataBinding
> : Fragment() {

    @get:LayoutRes
    abstract val fragmentLayout: Int

    abstract val viewModel: VM

    abstract fun B.initializeDataBinding()

    abstract fun intents(): Flow<I>

    abstract fun render(state: S)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return DataBindingUtil.inflate<B>(
            inflater,
            fragmentLayout,
            container,
            false
        ).apply {
            initializeDataBinding()
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.processIntent(intents())
        viewModel.states()
            .onEach { render(it) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}
