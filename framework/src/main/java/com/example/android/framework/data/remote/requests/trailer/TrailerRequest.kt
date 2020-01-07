package com.example.android.framework.data.remote.requests.trailer

import com.example.android.framework.data.remote.requests.BaseRequest

object TrailerRequest : BaseRequest<TrailerService>() {

    val service = getService<TrailerService>()

}