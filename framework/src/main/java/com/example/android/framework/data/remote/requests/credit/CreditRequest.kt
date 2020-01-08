package com.example.android.framework.data.remote.requests.credit

import com.example.android.framework.data.remote.requests.BaseRequest

object CreditRequest : BaseRequest<CreditService>() {

    val service = getService<CreditService>()

}