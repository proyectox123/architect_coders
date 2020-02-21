package com.example.android.frameworkretrofit.data.requests.credit

import com.example.android.frameworkretrofit.data.requests.BaseRequest

class CreditRequest(baseUrl: String) : BaseRequest<CreditService>(baseUrl) {

    val service = getService<CreditService>()

}