package com.mho.training.features.moviedetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.android.domain.Credit
import com.example.android.domain.Keyword
import com.example.android.domain.Review
import com.example.android.domain.Trailer
import com.mho.training.R
import com.mho.training.adapters.credit.CreditListAdapter
import com.mho.training.adapters.keyword.KeywordListAdapter
import com.mho.training.data.translators.toDomainMovie
import com.mho.training.databinding.ActivityMovieDetailBinding
import com.mho.training.features.moviedetail.MovieDetailViewModel.Navigation
import com.mho.training.features.persondetail.PersonDetailActivity
import com.mho.training.features.reviews.ReviewsFragment
import com.mho.training.features.trailers.TrailersFragment
import com.mho.training.models.ParcelableMovie
import com.mho.training.utils.*
import kotlinx.android.synthetic.main.section_credit_list.*
import kotlinx.android.synthetic.main.section_keyword_list.*


class MovieDetailActivity : AppCompatActivity(), ReviewsFragment.OnReviewsFragmentListener,
    TrailersFragment.OnTrailersFragmentListener {

    //region Fields

    private lateinit var creditListAdapter: CreditListAdapter
    private lateinit var keywordListAdapter: KeywordListAdapter
    private lateinit var component: MovieDetailActivityComponent

    private val viewModel: MovieDetailViewModel by lazy {
        getViewModel { component.movieDetailViewModel }
    }

    //endregion

    //region Override Methods & Callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movie = (intent.getParcelableExtra(Constants.EXTRA_MOVIE) as ParcelableMovie?)?.toDomainMovie()

        component = app.component.plus(MovieDetailActivityModule(
            movie
        ))

        val binding: ActivityMovieDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        binding.apply {
            viewmodel = viewModel
            lifecycleOwner = this@MovieDetailActivity
        }

        validateReviewSection(movie?.id ?: 0)
        validateTrailerSection(movie?.id ?: 0)

        creditListAdapter = CreditListAdapter(::openCredit)
        keywordListAdapter = KeywordListAdapter(::openKeyword)

        creditListView.adapter = creditListAdapter
        keywordListView.adapter = keywordListAdapter

        viewModel.events.observe(this, Observer{ event ->
            event.getContentIfNotHandled()?.let {
                validateEvents(it)
            }
        })

        viewModel.onMovieInformation()
        viewModel.onValidateFavoriteMovieStatus()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun openReview(review: Review){
        Log.d(TAG, "openReview -> $review")
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(review.url)
        }

        startActivity(intent)
    }

    override fun openTrailer(trailer: Trailer){
        Log.d(TAG, "openTrailer -> $trailer")
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(trailer.videoPath)
        }

        startActivity(intent)
    }

    //endregion

    //region Private Methods

    private fun validateReviewSection(movieId: Int){
        var reviewsFragment = supportFragmentManager.findFragmentById(R.id.fragmentReviews) as ReviewsFragment?
        if (reviewsFragment == null) {
            val args = Bundle().apply {
                putInt(Constants.EXTRA_MOVIE_ID, movieId)
            }

            reviewsFragment = ReviewsFragment.newInstance(args)

            addFragment(R.id.fragmentReviews, reviewsFragment)
        }
    }

    private fun validateTrailerSection(movieId: Int){
        var trailersFragment = supportFragmentManager.findFragmentById(R.id.fragmentTrailers) as TrailersFragment?
        if (trailersFragment == null) {
            val args = Bundle().apply {
                putInt(Constants.EXTRA_MOVIE_ID, movieId)
            }

            trailersFragment = TrailersFragment.newInstance(args)

            addFragment(R.id.fragmentTrailers, trailersFragment)
        }
    }

    private fun openCredit(credit: Credit){
        Log.d(TAG, "openCredit -> $credit")
        startActivity<PersonDetailActivity> {
            putExtra(Constants.EXTRA_CREDIT_ID, credit.id)
        }
        overridePendingTransition(R.anim.anim_entry, R.anim.anim_exit)
    }

    private fun openKeyword(keyword: Keyword){
        Log.d(TAG, "openKeyword -> $keyword")
    }

    private fun validateEvents(navigation: Navigation){
        when(navigation){
            Navigation.CloseActivity -> finish()
        }
    }

    //endregion

    //region Companion Object

    companion object {

        private val TAG = MovieDetailActivity::class.java.simpleName

    }

    //endregion
}
