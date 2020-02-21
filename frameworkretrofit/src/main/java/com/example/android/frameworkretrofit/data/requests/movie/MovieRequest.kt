package com.example.android.frameworkretrofit.data.requests.movie

import com.example.android.frameworkretrofit.data.requests.BaseRequest

class MovieRequest(baseUrl: String) : BaseRequest<MovieService>(baseUrl) {

    val service = getService<MovieService>()

}