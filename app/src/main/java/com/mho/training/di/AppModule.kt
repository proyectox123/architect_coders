package com.mho.training.di

import android.app.Application
import com.example.android.data.repositories.PermissionChecker
import com.example.android.data.sources.*
import com.example.android.frameworkretrofit.data.requests.credit.CreditRequest
import com.example.android.frameworkretrofit.data.requests.keyword.KeywordRequest
import com.example.android.frameworkretrofit.data.requests.movie.MovieRequest
import com.example.android.frameworkretrofit.data.requests.person.PersonRequest
import com.example.android.frameworkretrofit.data.requests.review.ReviewRequest
import com.example.android.frameworkretrofit.data.requests.trailer.TrailerRequest
import com.example.android.frameworkroom.data.MovieDatabase
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
        @Named("formatVoteAverage") formatVoteAverage: String
    ): LocalMovieDataSource =
        MovieRoomMovieDataSource(db, errorUnableToFetchMovies, errorDuringFetchingMovies, formatVoteAverage)

    @Provides
    fun remoteMovieDataSourceProvider(
        @Named("errorUnableToFetchMovies") errorUnableToFetchMovies: String,
        @Named("errorDuringFetchingMovies") errorDuringFetchingMovies: String,
        @Named("formatVoteAverage") formatVoteAverage: String,
        movieRequest: MovieRequest
    ): RemoteMovieDataSource = MovieServerMovieDataSource(
        errorUnableToFetchMovies,
        errorDuringFetchingMovies,
        formatVoteAverage,
        movieRequest
    )

    @Provides
    fun remoteCreditDataSourceProvider(
        @Named("errorUnableToFetchCredits") errorUnableToFetchCredits: String,
        @Named("errorDuringFetchingCredits") errorDuringFetchingCredits: String,
        creditRequest: CreditRequest
    ): RemoteCreditDataSource = CreditServerDataSource(
        errorUnableToFetchCredits,
        errorDuringFetchingCredits,
        creditRequest
    )

    @Provides
    fun remoteKeywordDataSourceProvider(
        @Named("errorUnableToFetchKeywords") errorUnableToFetchKeywords: String,
        @Named("errorDuringFetchingKeywords") errorDuringFetchingKeywords: String,
        keywordRequest: KeywordRequest
    ): RemoteKeywordDataSource = KeywordServerDataSource(
        errorUnableToFetchKeywords,
        errorDuringFetchingKeywords,
        keywordRequest
    )

    @Provides
    fun remotePersonDataSourceProvider(
        @Named("errorUnableToFetchPerson") errorUnableToFetchPerson: String,
        @Named("errorDuringFetchingPerson") errorDuringFetchingPerson: String,
        personRequest: PersonRequest
    ): RemotePersonDataSource = PersonServerDataSource(
        errorUnableToFetchPerson,
        errorDuringFetchingPerson,
        personRequest
    )

    @Provides
    fun remoteReviewDataSourceProvider(
        @Named("errorUnableToFetchReviews") errorUnableToFetchReviews: String,
        @Named("errorDuringFetchingReviews") errorDuringFetchingReviews: String,
        reviewRequest: ReviewRequest
    ): RemoteReviewDataSource = ReviewServerDataSource(
        errorUnableToFetchReviews,
        errorDuringFetchingReviews,
        reviewRequest
    )

    @Provides
    fun remoteTrailerDataSourceProvider(
        @Named("errorUnableToFetchTrailers") errorUnableToFetchTrailers: String,
        @Named("errorDuringFetchingTrailers") errorDuringFetchingTrailers: String,
        trailerRequest: TrailerRequest
    ): RemoteTrailerDataSource = TrailerServerDataSource(
        errorUnableToFetchTrailers,
        errorDuringFetchingTrailers,
        trailerRequest
    )

    @Provides
    fun locationDataSourceProvider(app: Application): LocationDataSource =
        PlayServicesLocationDataSource(app)

    @Provides
    fun permissionCheckerProvider(app: Application): PermissionChecker =
        AndroidPermissionChecker(app)

}