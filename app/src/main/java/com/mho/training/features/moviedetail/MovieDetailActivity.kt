package com.mho.training.features.moviedetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.android.data.repositories.*
import com.example.android.domain.Credit
import com.example.android.domain.Keyword
import com.example.android.domain.Review
import com.example.android.domain.Trailer
import com.example.android.usecases.*
import com.mho.training.R
import com.mho.training.adapters.credit.CreditListAdapter
import com.mho.training.adapters.keyword.KeywordListAdapter
import com.mho.training.adapters.review.ReviewListAdapter
import com.mho.training.adapters.trailer.TrailerListAdapter
import com.mho.training.data.translators.toDomainMovie
import com.mho.training.databinding.ActivityMovieDetailBinding
import com.mho.training.features.moviedetail.MovieDetailViewModel.Navigation
import com.mho.training.features.persondetail.PersonDetailActivity
import com.mho.training.models.ParcelableMovie
import com.mho.training.permissions.AndroidPermissionChecker
import com.mho.training.sources.*
import com.mho.training.utils.*
import kotlinx.android.synthetic.main.section_credit_list.*
import kotlinx.android.synthetic.main.section_keyword_list.*
import kotlinx.android.synthetic.main.section_review_list.*
import kotlinx.android.synthetic.main.section_trailer_list.*


class MovieDetailActivity : AppCompatActivity() {

    //region Fields

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var creditListAdapter: CreditListAdapter
    private lateinit var keywordListAdapter: KeywordListAdapter
    private lateinit var reviewListAdapter: ReviewListAdapter
    private lateinit var trailerListAdapter: TrailerListAdapter

    private val movieRepository: MovieRepository by lazy {
        MovieRepository(
            MovieRoomDataSource(
                app.db,
                resources.getString(R.string.error_unable_to_fetch_movies),
                resources.getString(R.string.error_during_fetching_movies),
                resources.getString(R.string.text_movie_detail_vote_average)
            ), MovieServerDataSource(
                resources.getString(R.string.error_unable_to_fetch_movies),
                resources.getString(R.string.error_during_fetching_movies),
                resources.getString(R.string.text_movie_detail_vote_average)
            ), RegionRepository(
                PlayServicesLocationDataSource(app),
                AndroidPermissionChecker(app)
            )
        )
    }

    //endregion

    //region Override Methods & Callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModel {
            MovieDetailViewModel(
                (intent.getParcelableExtra(Constants.EXTRA_MOVIE) as ParcelableMovie?)?.toDomainMovie(),
                GetMovieDetailByIdUseCase(
                    movieRepository
                ),
                GetFavoriteMovieStatus(
                    movieRepository
                ),
                UpdateFavoriteMovieStatus(
                    movieRepository
                ),
                GetKeywordListUseCase(
                    KeywordRepository(
                        KeywordServerDataSource(
                            resources.getString(R.string.error_unable_to_fetch_keywords),
                            resources.getString(R.string.error_during_fetching_keywords)
                        )
                    )
                ),
                GetCreditListUseCase(
                    CreditRepository(
                        CreditServerDataSource(
                            resources.getString(R.string.error_unable_to_fetch_credits),
                            resources.getString(R.string.error_during_fetching_credits)
                        )
                    )
                ),
                GetTrailerListUseCase(
                    TrailerRepository(
                        TrailerServerDataSource(
                            resources.getString(R.string.error_unable_to_fetch_trailers),
                            resources.getString(R.string.error_during_fetching_trailers)
                        )
                    )
                ),
                GetReviewListUseCase(
                    ReviewRepository(
                        ReviewServerDataSource(
                            resources.getString(R.string.error_unable_to_fetch_reviews),
                            resources.getString(R.string.error_during_fetching_reviews)
                        )
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
        overridePendingTransition(R.anim.anim_entry, R.anim.anim_exit)
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
