package com.mho.training.features.reviews

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android.domain.Review
import com.mho.training.R
import com.mho.training.adapters.review.ReviewListAdapter
import com.mho.training.databinding.FragmentReviewsBinding
import com.mho.training.utils.Constants
import com.mho.training.utils.app
import com.mho.training.utils.getViewModel
import kotlinx.android.synthetic.main.fragment_reviews.*

class ReviewsFragment : Fragment() {

    //region Fields

    private lateinit var listener: OnReviewsFragmentListener
    private lateinit var reviewListAdapter: ReviewListAdapter
    private lateinit var component: ReviewsFragmentComponent

    private val viewModel: ReviewsViewModel by lazy {
        getViewModel { component.reviewsViewModel }
    }

    //endregion

    //region Override Methods & Callbacks

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

        component = app.component.plus(ReviewsFragmentModule(
            arguments?.getInt(Constants.EXTRA_MOVIE_ID, 0) ?: 0
        ))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return DataBindingUtil.inflate<FragmentReviewsBinding>(
            inflater,
            R.layout.fragment_reviews,
            container,
            false
        ).apply {
            viewmodel = viewModel
            lifecycleOwner = this@ReviewsFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reviewListAdapter = ReviewListAdapter(::openReview)

        reviewListView.adapter = reviewListAdapter

        viewModel.onReviewsFromMovie()
    }

    //endregion

    //region Private Methods

    private fun openReview(review: Review){
        listener.openReview(review)
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