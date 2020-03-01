package com.example.android.frameworkretrofit.data.requests.review

import com.example.android.frameworkretrofit.data.requests.BaseRequest

class ReviewRequest(baseUrl: String) : BaseRequest<ReviewService>(baseUrl) {

    val service = getService<ReviewService>()

}