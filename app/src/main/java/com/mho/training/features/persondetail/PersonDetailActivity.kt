package com.mho.training.features.persondetail

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.android.data.repositories.MovieRepository
import com.example.android.data.repositories.PersonRepository
import com.example.android.data.repositories.RegionRepository
import com.example.android.domain.Movie
import com.example.android.usecases.GetMovieListByPersonUseCase
import com.example.android.usecases.GetPersonInformationUseCase
import com.mho.training.R
import com.mho.training.adapters.movie.MovieListAdapter
import com.mho.training.data.translators.toParcelableMovie
import com.mho.training.databinding.ActivityPersonDetailBinding
import com.mho.training.features.moviedetail.MovieDetailActivity
import com.mho.training.features.persondetail.PersonDetailViewModel.Navigation
import com.mho.training.permissions.AndroidPermissionChecker
import com.mho.training.sources.MovieServerDataSource
import com.mho.training.sources.PersonServerDataSource
import com.mho.training.sources.PlayServicesLocationDataSource
import com.mho.training.sources.MovieRoomDataSource
import com.mho.training.utils.*
import kotlinx.android.synthetic.main.activity_main.*


class PersonDetailActivity : AppCompatActivity() {

    //region Fields

    private lateinit var movieListAdapter: MovieListAdapter

    private lateinit var viewModel: PersonDetailViewModel

    //endregion

    //region Override Methods & Callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModel {
            PersonDetailViewModel(
                intent.getIntExtra(Constants.EXTRA_CREDIT_ID, 0),
                GetPersonInformationUseCase(
                    PersonRepository(
                        PersonServerDataSource(
                            resources
                        )
                    )
                ),
                GetMovieListByPersonUseCase(
                    MovieRepository(
                        MovieRoomDataSource(
                            app.db,
                            resources,
                            resources.getString(R.string.error_unable_to_fetch_movies),
                            resources.getString(R.string.error_during_fetching_movies)
                        ), MovieServerDataSource(
                            resources,
                            resources.getString(R.string.error_unable_to_fetch_movies),
                            resources.getString(R.string.error_during_fetching_movies)
                        ), RegionRepository(
                            PlayServicesLocationDataSource(app),
                            AndroidPermissionChecker(app)
                        )
                    )
                )
            )
        }

        val binding: ActivityPersonDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_person_detail)
        binding.apply {
            viewmodel = viewModel
            lifecycleOwner = this@PersonDetailActivity
        }

        movieListAdapter = MovieListAdapter(viewModel::onMovieClicked)

        rvMovieList.adapter = movieListAdapter

        viewModel.events.observe(this, EventObserver(::validateEvents))

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
