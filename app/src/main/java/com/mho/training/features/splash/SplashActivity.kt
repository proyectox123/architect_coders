package com.mho.training.features.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mho.training.features.main.MainActivity
import com.mho.training.utils.startActivity

class SplashActivity : AppCompatActivity() {

    //region Override Methods & Callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity<MainActivity> {}

        finish()
    }

    //endregion
}