package com.mho.training.features.relatedmoviesbyperson

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android.domain.Movie
import com.mho.training.R
import com.mho.training.adapters.movie.MovieListAdapter
import com.mho.training.coreandroid.extensions.getViewModel
import com.mho.training.databinding.FragmentRelatedMoviesByPersonBinding
import com.mho.training.utils.Constants
import com.mho.training.utils.app
import kotlinx.android.synthetic.main.fragment_related_movies_by_person.*

class RelatedMoviesByPersonFragment : Fragment() {

    //region Fields

    private lateinit var listener: OnRelatedMoviesByPersonFragmentListener
    private lateinit var movieListAdapter: MovieListAdapter
    private lateinit var component: RelatedMoviesByPersonFragmentComponent

    private val viewModel: RelatedMoviesByPersonViewModel by lazy {
        getViewModel { component.relatedMoviesByPersonViewModel }
    }

    //endregion

    //region Override Methods & Callbacks

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            listener = context as OnRelatedMoviesByPersonFragmentListener
        }catch (e: ClassCastException){
            throw ClassCastException("$context must implement OnRelatedMoviesByPersonFragmentListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component = app.component.plus(RelatedMoviesByPersonFragmentModule(
            arguments?.getInt(Constants.EXTRA_CREDIT_ID, 0) ?: 0
        ))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return DataBindingUtil.inflate<FragmentRelatedMoviesByPersonBinding>(
            inflater,
            R.layout.fragment_related_movies_by_person,
            container,
            false
        ).apply {
            viewmodel = viewModel
            lifecycleOwner = this@RelatedMoviesByPersonFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieListAdapter = MovieListAdapter(viewModel::onMovieClicked)

        rvMovieList.adapter = movieListAdapter

        viewModel.events.observe(this) { event ->
            event.getContentIfNotHandled()?.let {
                validateEvents(it)
            }
        }

        viewModel.onRelatedMoviesByPerson()
    }

    //endregion

    //region Private Methods

    private fun validateEvents(navigation: RelatedMoviesByPersonViewModel.Navigation){
        when(navigation){
            is RelatedMoviesByPersonViewModel.Navigation.NavigateToMovie -> navigation.run {
                listener.openMovieDetails(movie)
            }
        }
    }

    //endregion

    //region Inner Classes & Callbacks

    interface OnRelatedMoviesByPersonFragmentListener {
        fun openMovieDetails(movie: Movie)
    }

    //endregion

    companion object {

        fun newInstance(bundle: Bundle) = RelatedMoviesByPersonFragment().apply {
            arguments = bundle
        }

    }

}