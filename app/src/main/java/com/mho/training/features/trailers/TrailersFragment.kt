package com.mho.training.features.trailers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android.domain.Trailer
import com.mho.training.R
import com.mho.training.adapters.trailer.TrailerListAdapter
import com.mho.training.databinding.FragmentTrailersBinding
import com.mho.training.utils.Constants
import com.mho.training.utils.app
import com.mho.training.utils.getViewModel
import kotlinx.android.synthetic.main.fragment_trailers.*

class TrailersFragment : Fragment() {

    //region Fields

    private lateinit var listener: OnTrailersFragmentListener
    private lateinit var trailerListAdapter: TrailerListAdapter
    private lateinit var component: TrailersFragmentComponent

    private val viewModel: TrailersViewModel by lazy {
        getViewModel { component.trailersViewModel }
    }

    //endregion

    //region Override Methods & Callbacks

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            listener = context as OnTrailersFragmentListener
        }catch (e: ClassCastException){
            throw ClassCastException("$context must implement OnTrailersFragmentListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component = app.component.plus(TrailersFragmentModule(
            arguments?.getInt(Constants.EXTRA_MOVIE_ID, 0) ?: 0
        ))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return DataBindingUtil.inflate<FragmentTrailersBinding>(
            inflater,
            R.layout.fragment_trailers,
            container,
            false
        ).apply {
            viewmodel = viewModel
            lifecycleOwner = this@TrailersFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        trailerListAdapter = TrailerListAdapter(::openTrailer)

        trailerListView.adapter = trailerListAdapter

        viewModel.onTrailersFromMovie()
    }

    //endregion

    //region Private Methods

    private fun openTrailer(trailer: Trailer){
        listener.openTrailer(trailer)
    }

    //endregion

    //region Inner Classes & Callbacks

    interface OnTrailersFragmentListener {
        fun openTrailer(trailer: Trailer)
    }

    //endregion

    companion object {

        fun newInstance(bundle: Bundle) = TrailersFragment().apply {
            arguments = bundle
        }

    }

}