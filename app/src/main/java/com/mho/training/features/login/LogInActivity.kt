package com.mho.training.features.login

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.mho.training.R
import com.mho.training.databinding.ActivityLogInBinding
import com.mho.training.features.login.LogInViewModel.Navigation
import com.mho.training.features.main.MainActivity
import com.mho.training.utils.*

class LogInActivity: AppCompatActivity() {

    //region Fields

    private lateinit var component: LogInActivityComponent

    private val viewModel: LogInViewModel by lazy {
        getViewModel { component.logInViewModel }
    }

    //endregion

    //region Override Methods & Callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component = app.component.plus(LogInActivityModule())

        val binding: ActivityLogInBinding = DataBindingUtil.setContentView(this, R.layout.activity_log_in)
        binding.apply {
            viewmodel = viewModel
            lifecycleOwner = this@LogInActivity
        }

        viewModel.events.observe(this, Observer{ event ->
            event.getContentIfNotHandled()?.let {
                validateEvents(it)
            }
        })
    }

    //endregion

    //region Private Methods

    private fun validateEvents(navigation: Navigation) {
        when (navigation) {
            Navigation.NavigateToMain -> openMain()
            Navigation.ShowLogInError -> showLogInError()
        }
    }

    private fun openMain(){
        startActivity<MainActivity> {}
        finish()
    }

    private fun showLogInError(){
        Toast.makeText(this, getString(R.string.error_log_in), Toast.LENGTH_LONG).show()
    }

    //endregion

}