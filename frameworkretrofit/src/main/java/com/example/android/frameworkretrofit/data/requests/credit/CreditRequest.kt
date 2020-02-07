package com.example.android.frameworkretrofit.data.requests.credit

import com.example.android.frameworkretrofit.data.requests.BaseRequest

object CreditRequest : BaseRequest<CreditService>() {

    val service = getService<CreditService>()

}