package com.mho.training.features.moviedetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.mho.training.R
import com.mho.training.adapters.review.ReviewListAdapter
import com.mho.training.adapters.trailer.TrailerListAdapter
import com.mho.training.data.remote.models.Review
import com.mho.training.data.remote.models.Trailer
import com.mho.training.databinding.ActivityMovieDetailBinding
import com.mho.training.domain.Movie
import com.mho.training.utils.Constants
import com.mho.training.utils.getViewModel
import com.mho.training.utils.loadUrl
import kotlinx.android.synthetic.main.activity_movie_detail.*


class MovieDetailActivity : AppCompatActivity() {

    //region Fields

    private val TAG = MovieDetailActivity::class.java.simpleName

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var reviewListAdapter: ReviewListAdapter
    private lateinit var trailerListAdapter: TrailerListAdapter

    //endregion

    //region Override Methods & Callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModel {
            MovieDetailViewModel(
                intent.getParcelableExtra(Constants.EXTRA_MOVIE) as Movie?,
                resources
            )
        }

        val binding: ActivityMovieDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        binding.apply {
            viewmodel = viewModel
            lifecycleOwner = this@MovieDetailActivity
        }

        reviewListAdapter = ReviewListAdapter(::openReview)
        trailerListAdapter = TrailerListAdapter(::openTrailer)

        reviewListView.adapter = reviewListAdapter
        trailerListView.adapter = trailerListAdapter

        viewModel.model.observe(this, Observer(::updateUi))
        viewModel.reviews.observe(this, Observer(::showReviews))
        viewModel.trailers.observe(this, Observer(::showTrailers))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.movie_detail, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        if (item.itemId == R.id.action_favorite) {
            //updateFavoriteMovieStatus
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    //endregion

    //region Private Methods

    private fun openReview(review: Review){
        Log.d(TAG, "openReview -> $review")
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(review.url)
        }

        startActivity(intent)
    }

    private fun openTrailer(trailer: Trailer){
        Log.d(TAG, "openTrailer -> $trailer")
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(trailer.videoPath)
        }

        startActivity(intent)
    }

    private fun updateUi(model: MovieDetailViewModel.UiModel) = with(model.movie) {
        movieDetailPosterImageView.loadUrl(posterPath)
        movieDetailTitleTextView.text = title
        movieDetailReleaseDateTextView.text = releaseDate
        movieDetailVoteAverageTextView.text = getVoteAverageLabel(this@MovieDetailActivity)
        movieDetailDescriptionTextView.text = plotSynopsis
    }

    private fun showReviews(reviewList: List<Review>){
        reviewListAdapter.reviews = reviewList
    }

    private fun showTrailers(trailerList: List<Trailer>){
        trailerListAdapter.trailers = trailerList
    }

    //endregion
}
