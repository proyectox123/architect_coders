package com.mho.training.features.credits

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.android.domain.Credit
import com.mho.training.R
import com.mho.training.adapters.credit.CreditListAdapter
import com.mho.training.coreandroid.extensions.getViewModel
import com.mho.training.databinding.FragmentCreditsBinding
import com.mho.training.utils.Constants
import com.mho.training.utils.app
import kotlinx.android.synthetic.main.fragment_credits.*

class CreditsFragment : Fragment() {

    //region Fields

    private lateinit var listener: OnCreditsFragmentListener
    private lateinit var creditListAdapter: CreditListAdapter
    private lateinit var component: CreditsFragmentComponent

    private val viewModel: CreditsViewModel by lazy {
        getViewModel { component.creditsViewModel }
    }

    //endregion

    //region Override Methods & Callbacks

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            listener = context as OnCreditsFragmentListener
        }catch (e: ClassCastException){
            throw ClassCastException("$context must implement OnCreditsFragmentListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component = app.component.plus(CreditsFragmentModule(
            arguments?.getInt(Constants.EXTRA_MOVIE_ID, 0) ?: 0
        ))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return DataBindingUtil.inflate<FragmentCreditsBinding>(
            inflater,
            R.layout.fragment_credits,
            container,
            false
        ).apply {
            viewmodel = viewModel
            lifecycleOwner = this@CreditsFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        creditListAdapter = CreditListAdapter(::openCredit)

        creditListView.adapter = creditListAdapter

        viewModel.onCreditsFromMovie()
    }

    //endregion

    //region Private Methods

    private fun openCredit(credit: Credit){
        listener.openCredit(credit)
    }

    //endregion

    //region Inner Classes & Callbacks

    interface OnCreditsFragmentListener {
        fun openCredit(credit: Credit)
    }

    //endregion

    companion object {

        fun newInstance(bundle: Bundle) = CreditsFragment().apply {
            arguments = bundle
        }

    }

}