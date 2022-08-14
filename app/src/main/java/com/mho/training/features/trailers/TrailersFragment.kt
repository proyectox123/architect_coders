package com.mho.training.features.trailers

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.mho.training.R
import com.mho.training.adapters.trailer.TrailerListAdapter
import com.mho.training.coreandroid.extensions.getViewModel
import com.mho.training.databinding.FragmentTrailersBinding
import com.mho.training.features.trailer.mvi.router.TrailerRouter
import com.mho.training.features.trailer.mvi.states.TrailerAction
import com.mho.training.features.trailer.mvi.states.TrailerIntent
import com.mho.training.features.trailer.mvi.states.TrailerResult
import com.mho.training.features.trailer.mvi.states.TrailerSideEffect
import com.mho.training.features.trailer.mvi.states.TrailerViewState
import com.mho.training.features.trailer.mvi.viewmodel.TrailerViewModel
import com.mho.training.features.trailers.di.TrailersFragmentComponent
import com.mho.training.features.trailers.di.TrailersFragmentModule
import com.mho.training.mvi.MviRouter
import com.mho.training.mviandroid.MviFragment
import com.mho.training.utils.Constants
import com.mho.training.utils.app
import kotlinx.android.synthetic.main.fragment_trailers.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge

@FlowPreview
@ExperimentalCoroutinesApi
class TrailersFragment : MviFragment<
        TrailerIntent,
        TrailerAction,
        TrailerViewState,
        TrailerResult,
        TrailerSideEffect,
        TrailerViewModel,
        FragmentTrailersBinding,
        >() {

    //region Fields

    private val openTrailerIntentChannel = Channel<TrailerIntent.OpenTrailerIntent>()

    private lateinit var trailerListAdapter: TrailerListAdapter
    private lateinit var component: TrailersFragmentComponent

    //endregion

    //region Override Methods & Callbacks

    override val fragmentLayout: Int
        get() = R.layout.fragment_trailers

    override val viewModel: TrailerViewModel by lazy {
        getViewModel { component.trailersViewModel }
    }

    override val router: MviRouter<TrailerSideEffect> by lazy {
        TrailerRouter(requireActivity())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component = app.component.plus(
            TrailersFragmentModule(
            arguments?.getInt(Constants.EXTRA_MOVIE_ID, 0) ?: 0
        )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trailerListAdapter = TrailerListAdapter(openTrailerIntentChannel)

        trailerListView.adapter = trailerListAdapter
    }

    override fun FragmentTrailersBinding.initializeDataBinding() {
        viewmodel = viewModel
        lifecycleOwner = this@TrailersFragment
    }

    override fun intents(): Flow<TrailerIntent> =
        merge(loadAllReviewIntent(), openTrailerIntent())

    override fun render(state: TrailerViewState) {
        trailerProgressBar.isVisible = state.isLoading
        trailerErrorText.isVisible = state.error != null

        (trailerListView.adapter as? TrailerListAdapter)?.let {
            it.trailers = state.trailers
        }
    }

    //endregion

    //region Private Methods

    private fun loadAllReviewIntent(): Flow<TrailerIntent> =
        flow {
            emit(TrailerIntent.LoadAllTrailerIntent)
        }

    private fun openTrailerIntent(): Flow<TrailerIntent> = openTrailerIntentChannel.consumeAsFlow()

    //endregion

    companion object {

        fun newInstance(bundle: Bundle) = TrailersFragment().apply {
            arguments = bundle
        }

    }

}