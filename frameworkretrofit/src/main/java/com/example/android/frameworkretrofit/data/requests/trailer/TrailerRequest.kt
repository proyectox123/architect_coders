package com.example.android.frameworkretrofit.data.requests.trailer

import com.example.android.frameworkretrofit.data.requests.BaseRequest

object TrailerRequest : BaseRequest<TrailerService>() {

    val service = getService<TrailerService>()

}