package com.mho.training.features.moviedetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.android.domain.Credit
import com.example.android.domain.Keyword
import com.example.android.domain.Movie
import com.example.android.domain.Trailer
import com.mho.training.R
import com.mho.training.data.translators.toDomainMovie
import com.mho.training.data.translators.toParcelableMovie
import com.mho.training.databinding.ActivityMovieDetailBinding
import com.mho.training.features.credits.CreditsFragment
import com.mho.training.features.keywords.KeywordsFragment
import com.mho.training.features.moviedetail.MovieDetailViewModel.Navigation
import com.mho.training.features.movieinfo.MovieInfoFragment
import com.mho.training.features.persondetail.PersonDetailActivity
import com.mho.training.features.reviews.ReviewsFragment
import com.mho.training.features.trailers.TrailersFragment
import com.mho.training.models.ParcelableMovie
import com.mho.training.utils.*


class MovieDetailActivity : AppCompatActivity(),
    CreditsFragment.OnCreditsFragmentListener,
    KeywordsFragment.OnKeywordsFragmentListener,
    TrailersFragment.OnTrailersFragmentListener {

    //region Fields

    private lateinit var component: MovieDetailActivityComponent

    private val viewModel: MovieDetailViewModel by lazy {
        getViewModel { component.movieDetailViewModel }
    }

    //endregion

    //region Override Methods & Callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movie: Movie? = (intent.getParcelableExtra(Constants.EXTRA_MOVIE) as ParcelableMovie?)?.toDomainMovie()

        component = app.component.plus(MovieDetailActivityModule(movie))

        val binding: ActivityMovieDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        binding.apply {
            viewmodel = viewModel
            lifecycleOwner = this@MovieDetailActivity
        }

        viewModel.events.observe(this) { event ->
            event.getContentIfNotHandled()?.let {
                validateEvents(it)
            }
        }

        viewModel.onMovieValidation()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun openCredit(credit: Credit){
        Log.d(TAG, "openCredit -> $credit")
        startActivity<PersonDetailActivity> {
            putExtra(Constants.EXTRA_CREDIT_ID, credit.id)
        }
        overridePendingTransition(R.anim.anim_entry, R.anim.anim_exit)
    }

    override fun openKeyword(keyword: Keyword){
        Log.d(TAG, "openKeyword -> $keyword")
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

    private fun validateEvents(navigation: Navigation){
        when(navigation){
            Navigation.CloseActivity -> finish()
            is Navigation.InitializeMovieDetail -> navigation.run { initializeMovieDetail(movie) }
        }
    }

    private fun initializeMovieDetail(movie: Movie){
        validateMovieInfoSection(movie)
        validateCreditsSection(movie.id )
        validateKeywordsSection(movie.id)
        validateReviewSection(movie.id)
        validateTrailerSection(movie.id)

        viewModel.onValidateFavoriteMovieStatus()
    }

    private fun validateMovieInfoSection(movie: Movie){
        val fragmentId = R.id.fragmentMovieInfo
        var movieInfoFragment = supportFragmentManager.findFragmentById(fragmentId) as MovieInfoFragment?
        if (movieInfoFragment == null) {
            val args = Bundle().apply {
                putParcelable(Constants.EXTRA_MOVIE, movie.toParcelableMovie())
            }

            movieInfoFragment = MovieInfoFragment.newInstance(args)

            addFragment(fragmentId, movieInfoFragment)
        }
    }

    private fun validateCreditsSection(movieId: Int){
        val fragmentId = R.id.fragmentCredits
        var creditsFragment = supportFragmentManager.findFragmentById(fragmentId) as CreditsFragment?
        if (creditsFragment == null) {
            val args = Bundle().apply {
                putInt(Constants.EXTRA_MOVIE_ID, movieId)
            }

            creditsFragment = CreditsFragment.newInstance(args)

            addFragment(fragmentId, creditsFragment)
        }
    }

    private fun validateKeywordsSection(movieId: Int){
        val fragmentId = R.id.fragmentKeywords
        var keywordsFragment = supportFragmentManager.findFragmentById(fragmentId) as KeywordsFragment?
        if (keywordsFragment == null) {
            val args = Bundle().apply {
                putInt(Constants.EXTRA_MOVIE_ID, movieId)
            }

            keywordsFragment = KeywordsFragment.newInstance(args)

            addFragment(fragmentId, keywordsFragment)
        }
    }

    private fun validateReviewSection(movieId: Int){
        val fragmentId = R.id.fragmentReviews
        var reviewsFragment = supportFragmentManager.findFragmentById(fragmentId) as ReviewsFragment?
        if (reviewsFragment == null) {
            val args = Bundle().apply {
                putInt(Constants.EXTRA_MOVIE_ID, movieId)
            }

            reviewsFragment = ReviewsFragment.newInstance(args)

            addFragment(fragmentId, reviewsFragment)
        }
    }

    private fun validateTrailerSection(movieId: Int){
        val fragmentId = R.id.fragmentTrailers
        var trailersFragment = supportFragmentManager.findFragmentById(fragmentId) as TrailersFragment?
        if (trailersFragment == null) {
            val args = Bundle().apply {
                putInt(Constants.EXTRA_MOVIE_ID, movieId)
            }

            trailersFragment = TrailersFragment.newInstance(args)

            addFragment(fragmentId, trailersFragment)
        }
    }

    //endregion

    //region Companion Object

    companion object {

        private val TAG = MovieDetailActivity::class.java.simpleName

    }

    //endregion
}
