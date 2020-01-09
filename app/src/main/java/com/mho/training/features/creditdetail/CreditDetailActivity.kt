package com.mho.training.features.creditdetail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.android.domain.Credit
import com.mho.training.R
import com.mho.training.databinding.ActivityCreditDetailBinding
import com.mho.training.features.creditdetail.CreditDetailViewModel.Navigation
import com.mho.training.utils.Constants
import com.mho.training.utils.EventObserver
import com.mho.training.utils.getViewModel


class CreditDetailActivity : AppCompatActivity() {

    //region Fields

    private lateinit var viewModel: CreditDetailViewModel

    //endregion

    //region Override Methods & Callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModel {
            CreditDetailViewModel(
                intent.getParcelableExtra(Constants.EXTRA_CREDIT) as Credit?
            )
        }

        val binding: ActivityCreditDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_credit_detail)
        binding.apply {
            viewmodel = viewModel
            lifecycleOwner = this@CreditDetailActivity
        }

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
        when(navigation){
            Navigation.CloseActivity -> finish()
        }
    }

    //endregion

    //region Companion Object

    companion object {

        private val TAG = CreditDetailActivity::class.java.simpleName

    }

    //endregion
}
