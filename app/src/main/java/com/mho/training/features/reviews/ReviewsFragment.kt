package com.mho.training.features.reviews

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.mho.training.R
import com.mho.training.adapters.review.ReviewListAdapter
import com.mho.training.coreandroid.extensions.getViewModel
import com.mho.training.databinding.FragmentReviewsBinding
import com.mho.training.features.reviews.di.ReviewsFragmentComponent
import com.mho.training.features.reviews.di.ReviewsFragmentModule
import com.mho.training.features.reviews.mvi.ReviewAction
import com.mho.training.features.reviews.mvi.ReviewIntent
import com.mho.training.features.reviews.mvi.ReviewResult
import com.mho.training.features.reviews.mvi.ReviewSideEffect
import com.mho.training.features.reviews.mvi.ReviewViewState
import com.mho.training.features.reviews.router.ReviewRouter
import com.mho.training.mvi.MviRouter
import com.mho.training.mviandroid.MviFragment
import com.mho.training.utils.Constants
import com.mho.training.utils.app
import kotlinx.android.synthetic.main.fragment_reviews.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge

@FlowPreview
@ExperimentalCoroutinesApi
class ReviewsFragment : MviFragment<
        ReviewIntent,
        ReviewAction,
        ReviewViewState,
        ReviewResult,
        ReviewSideEffect,
        ReviewsViewModel,
        FragmentReviewsBinding,
>() {

    //region Fields

    private val openReviewIntentChannel = Channel<ReviewIntent.OpenReviewIntent>()

    private lateinit var reviewListAdapter: ReviewListAdapter
    private lateinit var component: ReviewsFragmentComponent

    //endregion

    //region Override Methods & Callbacks

    override val fragmentLayout: Int
        get() = R.layout.fragment_reviews

    override val viewModel: ReviewsViewModel by lazy {
        getViewModel { component.reviewsViewModel }
    }

    override val router: MviRouter<ReviewSideEffect> by lazy {
        ReviewRouter(requireActivity())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component = app.component.plus(
            ReviewsFragmentModule(
                arguments?.getInt(Constants.EXTRA_MOVIE_ID, 0) ?: 0,
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reviewListAdapter = ReviewListAdapter(openReviewIntentChannel)

        reviewListView.adapter = reviewListAdapter
    }

    override fun FragmentReviewsBinding.initializeDataBinding() {
        viewmodel = viewModel
        lifecycleOwner = this@ReviewsFragment
    }

    override fun intents(): Flow<ReviewIntent> =
        merge(loadAllReviewIntent(), openReviewIntent())

    override fun render(state: ReviewViewState) {
        reviewProgressBar.isVisible = state.isLoading
        reviewErrorText.isVisible = state.error != null

        (reviewListView.adapter as? ReviewListAdapter)?.let {
            it.reviews = state.reviews
        }
    }

    //endregion

    //region Private Methods

    private fun loadAllReviewIntent(): Flow<ReviewIntent> =
        flow {
            emit(ReviewIntent.LoadAllReviewIntent)
        }

    private fun openReviewIntent(): Flow<ReviewIntent> = openReviewIntentChannel.consumeAsFlow()

    //endregion

    companion object {

        fun newInstance(bundle: Bundle) = ReviewsFragment().apply {
            arguments = bundle
        }
    }
}
