package com.mho.training.features.persondetail

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.android.domain.Movie
import com.mho.training.R
import com.mho.training.coreandroid.extensions.addFragment
import com.mho.training.coreandroid.extensions.getViewModel
import com.mho.training.coreandroid.extensions.startActivity
import com.mho.training.data.translators.toParcelableMovie
import com.mho.training.databinding.ActivityPersonDetailBinding
import com.mho.training.features.moviedetail.MovieDetailActivity
import com.mho.training.features.persondetail.PersonDetailViewModel.Navigation
import com.mho.training.features.relatedmoviesbyperson.RelatedMoviesByPersonFragment
import com.mho.training.utils.*

class PersonDetailActivity : AppCompatActivity(),
    RelatedMoviesByPersonFragment.OnRelatedMoviesByPersonFragmentListener {

    //region Fields

    private lateinit var component: PersonDetailActivityComponent

    private val viewModel: PersonDetailViewModel by lazy {
        getViewModel { component.personDetailViewModel }
    }

    //endregion

    //region Override Methods & Callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val creditId = intent.getIntExtra(Constants.EXTRA_CREDIT_ID, 0)

        component = app.component.plus(PersonDetailActivityModule(
            creditId
        ))

        val binding: ActivityPersonDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_person_detail)
        binding.apply {
            viewmodel = viewModel
            lifecycleOwner = this@PersonDetailActivity
        }

        val fragmentId = R.id.fragmentRelatedMoviesByPerson
        var relatedMoviesByPersonFragment = supportFragmentManager.findFragmentById(fragmentId) as RelatedMoviesByPersonFragment?
        if (relatedMoviesByPersonFragment == null) {
            val args = Bundle().apply {
                putInt(Constants.EXTRA_CREDIT_ID, creditId)
            }

            relatedMoviesByPersonFragment = RelatedMoviesByPersonFragment.newInstance(args)

            addFragment(fragmentId, relatedMoviesByPersonFragment)
        }

        viewModel.events.observe(this) { event ->
            event.getContentIfNotHandled()?.let {
                validateEvents(it)
            }
        }

        viewModel.onPersonDetailInformation()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun openMovieDetails(movie: Movie) {
        startActivity<MovieDetailActivity> {
            putExtra(Constants.EXTRA_MOVIE, movie.toParcelableMovie())
        }
    }

    //endregion

    //region Private Methods

    private fun validateEvents(navigation: Navigation){
        Log.d(TAG, "validateEvents navigation -> $navigation")
        when(navigation){
            Navigation.CloseActivity -> finish()
        }
    }

    //endregion

    //region Companion Object

    companion object {

        private val TAG = PersonDetailActivity::class.java.simpleName

    }

    //endregion
}
