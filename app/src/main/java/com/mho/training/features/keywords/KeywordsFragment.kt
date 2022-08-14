package com.mho.training.features.keywords

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android.domain.Keyword
import com.mho.training.R
import com.mho.training.adapters.keyword.KeywordListAdapter
import com.mho.training.coreandroid.extensions.getViewModel
import com.mho.training.databinding.FragmentKeywordsBinding
import com.mho.training.utils.Constants
import com.mho.training.utils.app
import kotlinx.android.synthetic.main.fragment_keywords.*

class KeywordsFragment : Fragment() {

    //region Fields

    private lateinit var listener: OnKeywordsFragmentListener
    private lateinit var keywordListAdapter: KeywordListAdapter
    private lateinit var component: KeywordsFragmentComponent

    private val viewModel: KeywordsViewModel by lazy {
        getViewModel { component.keywordsViewModel }
    }

    //endregion

    //region Override Methods & Callbacks

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            listener = context as OnKeywordsFragmentListener
        }catch (e: ClassCastException){
            throw ClassCastException("$context must implement OnKeywordsFragmentListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component = app.component.plus(KeywordsFragmentModule(
            arguments?.getInt(Constants.EXTRA_MOVIE_ID, 0) ?: 0
        ))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return DataBindingUtil.inflate<FragmentKeywordsBinding>(
            inflater,
            R.layout.fragment_keywords,
            container,
            false
        ).apply {
            viewmodel = viewModel
            lifecycleOwner = this@KeywordsFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        keywordListAdapter = KeywordListAdapter(::openKeyword)

        keywordListView.adapter = keywordListAdapter

        viewModel.onKeywordsFromMovie()
    }

    //endregion

    //region Private Methods

    private fun openKeyword(keyword: Keyword){
        listener.openKeyword(keyword)
    }

    //endregion

    //region Inner Classes & Callbacks

    interface OnKeywordsFragmentListener {
        fun openKeyword(keyword: Keyword)
    }

    //endregion

    companion object {

        fun newInstance(bundle: Bundle) = KeywordsFragment().apply {
            arguments = bundle
        }

    }

}