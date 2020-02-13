package com.mho.training.features.persondetail

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.android.domain.Movie
import com.mho.training.R
import com.mho.training.adapters.movie.MovieListAdapter
import com.mho.training.data.translators.toParcelableMovie
import com.mho.training.databinding.ActivityPersonDetailBinding
import com.mho.training.features.moviedetail.MovieDetailActivity
import com.mho.training.features.persondetail.PersonDetailViewModel.Navigation
import com.mho.training.utils.Constants
import com.mho.training.utils.app
import com.mho.training.utils.getViewModel
import com.mho.training.utils.startActivity
import kotlinx.android.synthetic.main.activity_main.*


class PersonDetailActivity : AppCompatActivity() {

    //region Fields

    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var component: PersonDetailActivityComponent

    private val viewModel: PersonDetailViewModel by lazy {
        getViewModel { component.personDetailViewModel }
    }

    //endregion

    //region Override Methods & Callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component = app.component.plus(PersonDetailActivityModule(
            intent.getIntExtra(Constants.EXTRA_CREDIT_ID, 0)
        ))

        val binding: ActivityPersonDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_person_detail)
        binding.apply {
            viewmodel = viewModel
            lifecycleOwner = this@PersonDetailActivity
        }

        movieListAdapter = MovieListAdapter(viewModel::onMovieClicked)

        rvMovieList.adapter = movieListAdapter

        viewModel.events.observe(this, Observer{ event ->
            event.getContentIfNotHandled()?.let {
                validateEvents(it)
            }
        })

        viewModel.onCreditInformation()
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

    private fun validateEvents(navigation: Navigation){
        Log.d(TAG, "validateEvents navigation -> $navigation")
        when(navigation){
            is Navigation.NavigateToMovie -> navigation.run { openMovieDetails(movie) }
            Navigation.CloseActivity -> finish()
        }
    }

    private fun openMovieDetails(movie: Movie){
        startActivity<MovieDetailActivity> {
            putExtra(Constants.EXTRA_MOVIE, movie.toParcelableMovie())
        }
    }

    //endregion

    //region Companion Object

    companion object {

        private val TAG = PersonDetailActivity::class.java.simpleName

    }

    //endregion
}
