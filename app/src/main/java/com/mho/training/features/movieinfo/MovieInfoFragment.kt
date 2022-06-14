package com.mho.training.features.movieinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.mho.training.R
import com.mho.training.data.translators.toDomainMovie
import com.mho.training.databinding.FragmentMovieInfoBinding
import com.mho.training.models.ParcelableMovie
import com.mho.training.utils.Constants
import com.mho.training.utils.app
import com.mho.training.utils.getViewModel

class MovieInfoFragment : Fragment() {

    //region Fields

    private lateinit var component: MovieInfoFragmentComponent

    private val viewModel: MovieInfoViewModel by lazy {
        getViewModel { component.movieInfoViewModel }
    }

    //endregion

    //region Override Methods & Callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component = app.component.plus(MovieInfoFragmentModule(
            arguments!!.getParcelable<ParcelableMovie>(Constants.EXTRA_MOVIE)?.toDomainMovie()!!
        ))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return DataBindingUtil.inflate<FragmentMovieInfoBinding>(
            inflater,
            R.layout.fragment_movie_info,
            container,
            false
        ).apply {
            viewmodel = viewModel
            lifecycleOwner = this@MovieInfoFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onMovieInformation()
    }

    //endregion

    //region Private Methods



    //endregion

    companion object {

        fun newInstance(bundle: Bundle) = MovieInfoFragment().apply {
            arguments = bundle
        }

    }

}