package com.mho.training.di

import android.app.Application
import com.example.android.data.repositories.PermissionChecker
import com.example.android.data.sources.LocalMovieDataSource
import com.example.android.data.sources.LocationDataSource
import com.example.android.data.sources.RemoteCreditDataSource
import com.example.android.data.sources.RemoteKeywordDataSource
import com.example.android.data.sources.RemoteLogInDataSource
import com.example.android.data.sources.RemoteMovieDataSource
import com.example.android.data.sources.RemotePersonDataSource
import com.example.android.data.sources.RemoteReviewDataSource
import com.example.android.data.sources.RemoteTrailerDataSource
import com.example.android.frameworkretrofit.data.requests.credit.CreditRequest
import com.example.android.frameworkretrofit.data.requests.keyword.KeywordRequest
import com.example.android.frameworkretrofit.data.requests.movie.MovieRequest
import com.example.android.frameworkretrofit.data.requests.person.PersonRequest
import com.example.android.frameworkretrofit.data.requests.review.ReviewRequest
import com.example.android.frameworkretrofit.data.requests.trailer.TrailerRequest
import com.example.android.frameworkroom.data.MovieDatabase
import com.mho.training.features.trailer.mvi.data.TrailerServerDataSource
import com.mho.training.permissions.AndroidPermissionChecker
import com.mho.training.sources.CreditServerDataSource
import com.mho.training.sources.KeywordServerDataSource
import com.mho.training.sources.LogInServerDataSource
import com.mho.training.sources.MovieRoomMovieDataSource
import com.mho.training.sources.MovieServerMovieDataSource
import com.mho.training.sources.PersonServerDataSource
import com.mho.training.sources.PlayServicesLocationDataSource
import com.mho.training.sources.ReviewServerDataSource
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
        @Named("apiKey") apiKey: String,
        trailerRequest: TrailerRequest
    ): RemoteTrailerDataSource = TrailerServerDataSource(
        errorUnableToFetchTrailers,
        errorDuringFetchingTrailers,
        trailerRequest,
        apiKey,
    )

    @Provides
    fun locationDataSourceProvider(app: Application): LocationDataSource =
        PlayServicesLocationDataSource(app)

    @Provides
    fun permissionCheckerProvider(app: Application): PermissionChecker =
        AndroidPermissionChecker(app)

    @Provides
    fun remoteLogInServerDataSourceProvider(): RemoteLogInDataSource = LogInServerDataSource()
}