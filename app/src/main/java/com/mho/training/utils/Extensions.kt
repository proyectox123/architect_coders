package com.mho.training.utils

import android.content.Context
import androidx.fragment.app.Fragment
import com.mho.training.MoviesApp


val Context.app: MoviesApp
    get() = applicationContext as MoviesApp

val Fragment.app: MoviesApp
    get() = (activity?.app) as MoviesApp
