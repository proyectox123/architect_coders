package com.example.android.framework.data.remote.requests.person

import com.example.android.framework.data.remote.models.ServerPerson
import com.example.android.framework.utils.Constants.REQUEST_GET_PERSON_BY_CREDIT_ID
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PersonService {

    @GET(REQUEST_GET_PERSON_BY_CREDIT_ID)
    suspend fun getPersonByCreditIdAsync(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String
    ): Response<ServerPerson>
}