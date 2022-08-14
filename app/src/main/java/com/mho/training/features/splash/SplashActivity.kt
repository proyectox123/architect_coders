package com.mho.training.features.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mho.training.features.login.LogInActivity
import com.mho.training.coreandroid.extensions.startActivity

class SplashActivity : AppCompatActivity() {

    //region Override Methods & Callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity<LogInActivity> {}

        finish()
    }

    //endregion
}