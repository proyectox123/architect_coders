package com.mho.training.di

import com.example.android.frameworkretrofit.data.requests.credit.CreditRequest
import com.example.android.frameworkretrofit.data.requests.keyword.KeywordRequest
import com.example.android.frameworkretrofit.data.requests.movie.MovieRequest
import com.example.android.frameworkretrofit.data.requests.person.PersonRequest
import com.example.android.frameworkretrofit.data.requests.review.ReviewRequest
import com.example.android.frameworkretrofit.data.requests.trailer.TrailerRequest
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class RequestModule {

    @Provides
    fun movieRequestProvider(
        @Named("baseUrl") baseUrl: String
    ) = MovieRequest(baseUrl)

    @Provides
    fun creditRequestProvider(
        @Named("baseUrl") baseUrl: String
    ) = CreditRequest(baseUrl)

    @Provides
    fun keywordRequestProvider(
        @Named("baseUrl") baseUrl: String
    ) = KeywordRequest(baseUrl)

    @Provides
    fun personRequestProvider(
        @Named("baseUrl") baseUrl: String
    ) = PersonRequest(baseUrl)

    @Provides
    fun reviewRequestProvider(
        @Named("baseUrl") baseUrl: String
    ) = ReviewRequest(baseUrl)

    @Provides
    fun trailerRequestProvider(
        @Named("baseUrl") baseUrl: String
    ) = TrailerRequest(baseUrl)

}