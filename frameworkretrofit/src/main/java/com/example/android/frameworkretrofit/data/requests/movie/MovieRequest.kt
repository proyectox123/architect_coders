package com.example.android.frameworkretrofit.data.requests.movie

import com.example.android.frameworkretrofit.data.requests.BaseRequest

object MovieRequest : BaseRequest<MovieService>() {

    val service = getService<MovieService>()

}