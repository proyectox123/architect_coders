package com.mho.training.di

import android.app.Application
import com.example.android.data.repositories.PermissionChecker
import com.example.android.data.sources.*
import com.example.android.framework.data.local.database.MovieDatabase
import com.mho.training.permissions.AndroidPermissionChecker
import com.mho.training.sources.*
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun databaseProvider(app: Application) =
        MovieDatabase.getDatabase(app)

    @Provides
    fun localMovieDataSourceProvider(
        db: MovieDatabase,
        @Named("errorUnableToFetchMovies") errorUnableToFetchMovies: String,
        @Named("errorDuringFetchingMovies") errorDuringFetchingMovies: String,
        @Named("voteAverageLabel") voteAverageLabel: String
    ): LocalMovieDataSource =
        MovieRoomMovieDataSource(db, errorUnableToFetchMovies, errorDuringFetchingMovies, voteAverageLabel)

    @Provides
    fun remoteMovieDataSourceProvider(
        @Named("errorUnableToFetchMovies") errorUnableToFetchMovies: String,
        @Named("errorDuringFetchingMovies") errorDuringFetchingMovies: String,
        @Named("voteAverageLabel") voteAverageLabel: String
    ): RemoteMovieDataSource =
        MovieServerMovieDataSource(errorUnableToFetchMovies, errorDuringFetchingMovies, voteAverageLabel)

    @Provides
    fun remoteCreditDataSourceProvider(
        @Named("errorUnableToFetchCredits") errorUnableToFetchCredits: String,
        @Named("errorDuringFetchingCredits") errorDuringFetchingCredits: String
    ): RemoteCreditDataSource =
        CreditServerDataSource(errorUnableToFetchCredits, errorDuringFetchingCredits)

    @Provides
    fun remoteKeywordDataSourceProvider(
        @Named("errorUnableToFetchKeywords") errorUnableToFetchKeywords: String,
        @Named("errorDuringFetchingKeywords") errorDuringFetchingKeywords: String
    ): RemoteKeywordDataSource =
        KeywordServerDataSource(errorUnableToFetchKeywords, errorDuringFetchingKeywords)

    @Provides
    fun remotePersonDataSourceProvider(
        @Named("errorUnableToFetchPerson") errorUnableToFetchPerson: String,
        @Named("errorDuringFetchingPerson") errorDuringFetchingPerson: String
    ): RemotePersonDataSource =
        PersonServerDataSource(errorUnableToFetchPerson, errorDuringFetchingPerson)

    @Provides
    fun remoteReviewDataSourceProvider(
        @Named("errorUnableToFetchReviews") errorUnableToFetchReviews: String,
        @Named("errorDuringFetchingReviews") errorDuringFetchingReviews: String
    ): RemoteReviewDataSource =
        ReviewServerDataSource(errorUnableToFetchReviews, errorDuringFetchingReviews)

    @Provides
    fun remoteTrailerDataSourceProvider(
        @Named("errorUnableToFetchTrailers") errorUnableToFetchTrailers: String,
        @Named("errorDuringFetchingTrailers") errorDuringFetchingTrailers: String
    ): RemoteTrailerDataSource =
        TrailerServerDataSource(errorUnableToFetchTrailers, errorDuringFetchingTrailers)

    @Provides
    fun locationDataSourceProvider(app: Application): LocationDataSource =
        PlayServicesLocationDataSource(app)

    @Provides
    fun permissionCheckerProvider(app: Application): PermissionChecker =
        AndroidPermissionChecker(app)

}