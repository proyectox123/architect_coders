package com.mho.training.app

import com.mho.training.MoviesApp
import com.mho.training.di.DaggerUiTestComponent

class TestApplication: MoviesApp() {

    override fun initMoviesComponent() = DaggerUiTestComponent.factory().create(this)

}