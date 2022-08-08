package com.mho.training.features.reviews

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.android.domain.Review
import com.mho.training.R
import com.mho.training.adapters.review.ReviewListAdapter
import com.mho.training.databinding.FragmentReviewsBinding
import com.mho.training.features.reviews.di.ReviewsFragmentComponent
import com.mho.training.features.reviews.di.ReviewsFragmentModule
import com.mho.training.features.reviews.mvi.ReviewAction
import com.mho.training.features.reviews.mvi.ReviewIntent
import com.mho.training.features.reviews.mvi.ReviewResult
import com.mho.training.features.reviews.mvi.ReviewViewState
import com.mho.training.mviandroid.MviFragment
import com.mho.training.utils.Constants
import com.mho.training.utils.app
import com.mho.training.utils.getViewModel
import kotlinx.android.synthetic.main.fragment_reviews.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge

@FlowPreview
@ExperimentalCoroutinesApi
class ReviewsFragment : MviFragment<
        ReviewIntent,
        ReviewAction,
        ReviewViewState,
        ReviewResult,
        ReviewsViewModel,
        FragmentReviewsBinding,
>() {

    //region Fields

    private lateinit var listener: OnReviewsFragmentListener
    private lateinit var reviewListAdapter: ReviewListAdapter
    private lateinit var component: ReviewsFragmentComponent

    //endregion

    //region Override Methods & Callbacks

    override val fragmentLayout: Int
        get() = R.layout.fragment_reviews

    override val viewModel: ReviewsViewModel by lazy {
        getViewModel { component.reviewsViewModel }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            listener = context as OnReviewsFragmentListener
        }catch (e: ClassCastException){
            throw ClassCastException("$context must implement OnReviewsFragmentListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component = app.component.plus(
            ReviewsFragmentModule(
            arguments?.getInt(Constants.EXTRA_MOVIE_ID, 0) ?: 0
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reviewListAdapter = ReviewListAdapter(::openReview)

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

    private fun openReview(review: Review){
        listener.openReview(review)
    }

    private fun loadAllReviewIntent(): Flow<ReviewIntent> =
        flow {
            emit(ReviewIntent.LoadAllReviewIntent)
        }

    private fun openReviewIntent(): Flow<ReviewIntent> =
        flow {

        }

    //endregion

    //region Inner Classes & Callbacks

    interface OnReviewsFragmentListener {
        fun openReview(review: Review)
    }

    //endregion

    companion object {

        fun newInstance(bundle: Bundle) = ReviewsFragment().apply {
            arguments = bundle
        }
    }
}
