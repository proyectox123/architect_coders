package com.mho.training.features.persondetail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.android.data.repositories.PersonRepository
import com.example.android.usecases.GetPersonInformationUseCase
import com.mho.training.R
import com.mho.training.databinding.ActivityPersonDetailBinding
import com.mho.training.features.persondetail.PersonDetailViewModel.Navigation
import com.mho.training.sources.PersonDataSource
import com.mho.training.utils.Constants
import com.mho.training.utils.EventObserver
import com.mho.training.utils.getViewModel


class PersonDetailActivity : AppCompatActivity() {

    //region Fields

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
                        PersonDataSource(
                            resources
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

        private val TAG = PersonDetailActivity::class.java.simpleName

    }

    //endregion
}
