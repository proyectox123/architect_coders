package com.example.android.frameworkretrofit.data.requests.trailer

import com.example.android.frameworkretrofit.data.requests.BaseRequest

class TrailerRequest(baseUrl: String) : BaseRequest<TrailerService>(baseUrl) {

    val service = getService<TrailerService>()

}