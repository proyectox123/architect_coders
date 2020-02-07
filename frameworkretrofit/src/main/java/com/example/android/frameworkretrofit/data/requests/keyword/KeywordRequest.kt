package com.example.android.frameworkretrofit.data.requests.keyword

import com.example.android.frameworkretrofit.data.requests.BaseRequest

object KeywordRequest : BaseRequest<KeywordService>() {

    val service = getService<KeywordService>()

}