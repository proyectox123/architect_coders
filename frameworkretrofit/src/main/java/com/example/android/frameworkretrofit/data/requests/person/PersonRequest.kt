package com.example.android.frameworkretrofit.data.requests.person

import com.example.android.frameworkretrofit.data.requests.BaseRequest

class PersonRequest(baseUrl: String) : BaseRequest<PersonService>(baseUrl) {

    val service = getService<PersonService>()

}