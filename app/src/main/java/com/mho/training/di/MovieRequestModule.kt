package com.mho.training.di

import com.example.android.frameworkretrofit.data.requests.BaseRequest
import com.example.android.frameworkretrofit.data.requests.movie.MovieRequest
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class MovieRequestModule {

    @Provides
    @Singleton
    @Named("baseUrl")
    fun baseUrlProvider(): String = BaseRequest.BASE_MOVIE_DB_URL

    @Provides
    fun movieRequestProvider(
        @Named("baseUrl") baseUrl: String
    ) = MovieRequest(baseUrl)
}