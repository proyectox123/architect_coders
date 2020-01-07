package com.example.android.framework.data.remote.requests.review

import com.example.android.framework.data.remote.requests.BaseRequest

object ReviewRequest : BaseRequest<ReviewService>() {

    val service = getService<ReviewService>()

}