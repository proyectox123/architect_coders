package com.example.android.frameworkretrofit.data.requests.review

import com.example.android.frameworkretrofit.data.requests.BaseRequest

object ReviewRequest : BaseRequest<ReviewService>() {

    val service = getService<ReviewService>()

}