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
import com.example.android.data.repositories.MovieRepository
import com.example.android.data.repositories.RegionRepository
import com.example.android.data.repositories.ReviewRepository
import com.example.android.data.repositories.TrailerRepository
import com.example.android.domain.Review
import com.example.android.domain.Trailer
import com.example.android.usecases.GetFavoriteMovieStatus
import com.example.android.usecases.GetReviewListUseCase
import com.example.android.usecases.GetTrailerListUseCase
import com.example.android.usecases.UpdateFavoriteMovieStatus
import com.mho.training.R
import com.mho.training.adapters.review.ReviewListAdapter
import com.mho.training.adapters.trailer.TrailerListAdapter
import com.mho.training.data.translators.toDomainMovie
import com.mho.training.databinding.ActivityMovieDetailBinding
import com.mho.training.parcelables.MovieParcelable
import com.mho.training.permissions.AndroidPermissionChecker
import com.mho.training.sources.*
import com.mho.training.utils.Constants
import com.mho.training.utils.app
import com.mho.training.utils.getViewModel
import kotlinx.android.synthetic.main.activity_movie_detail.*


class MovieDetailActivity : AppCompatActivity() {

    //region Fields

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var reviewListAdapter: ReviewListAdapter
    private lateinit var trailerListAdapter: TrailerListAdapter

    private var favoriteMenuItem: MenuItem? = null

    //endregion

    //region Override Methods & Callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModel {
            MovieDetailViewModel(
                (intent.getParcelableExtra(Constants.EXTRA_MOVIE) as MovieParcelable?).toDomainMovie(),
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
                GetTrailerListUseCase(
                    TrailerRepository(
                        TrailerDataSource()
                    )
                ),
                GetReviewListUseCase(
                    ReviewRepository(
                        ReviewDataSource()
                    )
                )
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

        viewModel.isFavorite.observe(this, Observer(::updateFavoriteMovieStatus))

        viewModel.onMovieInformation()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.movie_detail, menu)

        this.favoriteMenuItem = menu.findItem(R.id.action_favorite)
        viewModel.onValidateFavoriteMovieStatus()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        if (item.itemId == R.id.action_favorite) {
            viewModel.onUpdateFavoriteMovieStatus()
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

    private fun updateFavoriteMovieStatus(isFavorite: Boolean){
        val icon = if(isFavorite) R.drawable.ic_favorite_full else R.drawable.ic_favorite_border

        favoriteMenuItem?.setIcon(icon)
        movieDetailFavorite.setImageResource(icon)
    }

    //endregion

    //region Companion Object

    companion object {

        private val TAG = MovieDetailActivity::class.java.simpleName

    }

    //endregion
}
