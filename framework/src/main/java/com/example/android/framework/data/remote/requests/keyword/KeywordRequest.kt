package com.example.android.framework.data.remote.requests.keyword

import com.example.android.framework.data.remote.requests.BaseRequest

object KeywordRequest : BaseRequest<KeywordService>() {

    val service = getService<KeywordService>()

}