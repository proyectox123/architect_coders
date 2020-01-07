package com.example.android.framework.data.remote.requests.movie

import com.example.android.framework.data.remote.requests.BaseRequest

object MovieRequest : BaseRequest<MovieService>() {

    val service = getService<MovieService>()

}