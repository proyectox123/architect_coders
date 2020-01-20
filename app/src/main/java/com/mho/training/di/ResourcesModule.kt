package com.mho.training.di

import android.app.Application
import com.mho.training.R
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ResourcesModule {

    @Provides
    @Singleton
    @Named("errorUnableToFetchCredits")
    fun errorUnableToFetchCreditsProvider(app: Application): String =
        app.getString(R.string.error_unable_to_fetch_credits)

    @Provides
    @Singleton
    @Named("errorDuringFetchingCredits")
    fun errorDuringFetchingCreditsProvider(app: Application): String =
        app.getString(R.string.error_during_fetching_credits)

    @Provides
    @Singleton
    @Named("errorUnableToFetchKeywords")
    fun errorUnableToFetchKeywordsProvider(app: Application): String =
        app.getString(R.string.error_unable_to_fetch_keywords)

    @Provides
    @Singleton
    @Named("errorDuringFetchingKeywords")
    fun errorDuringFetchingKeywordsProvider(app: Application): String =
        app.getString(R.string.error_during_fetching_keywords)

    @Provides
    @Singleton
    @Named("errorUnableToFetchMovies")
    fun errorUnableToFetchMoviesProvider(app: Application): String =
        app.getString(R.string.error_unable_to_fetch_movies)

    @Provides
    @Singleton
    @Named("errorDuringFetchingMovies")
    fun errorDuringFetchingMoviesProvider(app: Application): String =
        app.getString(R.string.error_during_fetching_movies)

    @Provides
    @Singleton
    @Named("errorUnableToFetchPerson")
    fun errorUnableToFetchPersonProvider(app: Application): String =
        app.getString(R.string.error_unable_to_fetch_person)

    @Provides
    @Singleton
    @Named("errorDuringFetchingPerson")
    fun errorDuringFetchingPersonProvider(app: Application): String =
        app.getString(R.string.error_during_fetching_person)

    @Provides
    @Singleton
    @Named("errorUnableToFetchReviews")
    fun errorUnableToFetchReviewsProvider(app: Application): String =
        app.getString(R.string.error_unable_to_fetch_reviews)

    @Provides
    @Singleton
    @Named("errorDuringFetchingReviews")
    fun errorDuringFetchingReviewsProvider(app: Application): String =
        app.getString(R.string.error_during_fetching_reviews)

    @Provides
    @Singleton
    @Named("errorUnableToFetchTrailers")
    fun errorUnableToFetchTrailersProvider(app: Application): String =
        app.getString(R.string.error_unable_to_fetch_trailers)

    @Provides
    @Singleton
    @Named("errorDuringFetchingTrailers")
    fun errorDuringFetchingTrailersProvider(app: Application): String =
        app.getString(R.string.error_during_fetching_trailers)

    @Provides
    @Singleton
    @Named("formatVoteAverage")
    fun formatVoteAverageProvider(app: Application): String =
        app.getString(R.string.text_movie_detail_vote_average)
}