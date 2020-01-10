package com.mho.training.features.moviedetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.android.data.repositories.*
import com.example.android.domain.*
import com.example.android.usecases.*
import com.mho.training.R
import com.mho.training.adapters.credit.CreditListAdapter
import com.mho.training.adapters.keyword.KeywordListAdapter
import com.mho.training.adapters.review.ReviewListAdapter
import com.mho.training.adapters.trailer.TrailerListAdapter
import com.mho.training.databinding.ActivityMovieDetailBinding
import com.mho.training.features.moviedetail.MovieDetailViewModel.Navigation
import com.mho.training.features.persondetail.PersonDetailActivity
import com.mho.training.permissions.AndroidPermissionChecker
import com.mho.training.sources.*
import com.mho.training.utils.*
import kotlinx.android.synthetic.main.activity_movie_detail.*


class MovieDetailActivity : AppCompatActivity() {

    //region Fields

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var creditListAdapter: CreditListAdapter
    private lateinit var keywordListAdapter: KeywordListAdapter
    private lateinit var reviewListAdapter: ReviewListAdapter
    private lateinit var trailerListAdapter: TrailerListAdapter

    //endregion

    //region Override Methods & Callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModel {
            MovieDetailViewModel(
                intent.getParcelableExtra(Constants.EXTRA_MOVIE) as Movie?,
                GetFavoriteMovieStatus(
                    MovieRepository(
                        RoomDataSource(
                            app.db,
                            resources
                        ), MovieDataSource(
                            resources
                        ), RegionRepository(
                            PlayServicesLocationDataSource(app),
                            AndroidPermissionChecker(app)
                        )
                    )
                ),
                UpdateFavoriteMovieStatus(
                    MovieRepository(
                        RoomDataSource(
                            app.db,
                            resources
                        ), MovieDataSource(
                            resources
                        ), RegionRepository(
                            PlayServicesLocationDataSource(app),
                            AndroidPermissionChecker(app)
                        )
                    )
                ),
                GetKeywordListUseCase(
                    KeywordRepository(
                        KeywordDataSource(resources)
                    )
                ),
                GetCreditListUseCase(
                    CreditRepository(
                        CreditDataSource(resources)
                    )
                ),
                GetTrailerListUseCase(
                    TrailerRepository(
                        TrailerDataSource(resources)
                    )
                ),
                GetReviewListUseCase(
                    ReviewRepository(
                        ReviewDataSource(resources)
                    )
                )
            )
        }

        val binding: ActivityMovieDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        binding.apply {
            viewmodel = viewModel
            lifecycleOwner = this@MovieDetailActivity
        }

        creditListAdapter = CreditListAdapter(::openCredit)
        keywordListAdapter = KeywordListAdapter(::openKeyword)
        reviewListAdapter = ReviewListAdapter(::openReview)
        trailerListAdapter = TrailerListAdapter(::openTrailer)

        creditListView.adapter = creditListAdapter
        keywordListView.adapter = keywordListAdapter
        reviewListView.adapter = reviewListAdapter
        trailerListView.adapter = trailerListAdapter

        viewModel.events.observe(this, EventObserver(::validateEvents))

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

    //endregion

    //region Private Methods

    private fun openCredit(credit: Credit){
        Log.d(TAG, "openCredit -> $credit")
        startActivity<PersonDetailActivity> {
            putExtra(Constants.EXTRA_CREDIT_ID, credit.id)
        }
    }

    private fun openKeyword(keyword: Keyword){
        Log.d(TAG, "openKeyword -> $keyword")
    }

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
