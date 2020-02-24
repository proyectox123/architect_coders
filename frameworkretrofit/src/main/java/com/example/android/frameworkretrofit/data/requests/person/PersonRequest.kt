package com.example.android.frameworkretrofit.data.requests.person

import com.example.android.frameworkretrofit.data.requests.BaseRequest

object PersonRequest : BaseRequest<PersonService>() {

    val service = getService<PersonService>()

}