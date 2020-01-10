package com.example.android.framework.data.remote.requests.person

import com.example.android.framework.data.remote.requests.BaseRequest

object PersonRequest : BaseRequest<PersonService>() {

    val service = getService<PersonService>()

}