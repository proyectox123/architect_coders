package com.mho.training

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.mho.training.di.DaggerMyMoviesComponent
import com.mho.training.di.MyMoviesComponent

class MoviesApp : Application() {

    lateinit var component: MyMoviesComponent
        private set

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        component = DaggerMyMoviesComponent
            .factory()
            .create(this)
    }
}