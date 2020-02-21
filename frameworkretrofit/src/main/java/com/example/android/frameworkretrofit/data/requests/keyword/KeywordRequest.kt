package com.example.android.frameworkretrofit.data.requests.keyword

import com.example.android.frameworkretrofit.data.requests.BaseRequest

class KeywordRequest(baseUrl: String) : BaseRequest<KeywordService>(baseUrl) {

    val service = getService<KeywordService>()

}