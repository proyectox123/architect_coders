package com.example.android.frameworkretrofit.data.requests.person

import com.example.android.frameworkretrofit.data.models.person.ServerPerson
import com.example.android.frameworkretrofit.utils.Constants.REQUEST_GET_PERSON_BY_CREDIT_ID
import com.example.android.frameworkretrofit.utils.Constants.REQUEST_PARAM_API_KEY
import com.example.android.frameworkretrofit.utils.Constants.REQUEST_PARAM_PERSON_ID
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonService {

    @GET(REQUEST_GET_PERSON_BY_CREDIT_ID)
    suspend fun getPersonByCreditIdAsync(
        @Path(REQUEST_PARAM_PERSON_ID) personId: Int,
        @Query(REQUEST_PARAM_API_KEY) apiKey: String
    ): Response<ServerPerson>
}