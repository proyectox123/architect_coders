package com.mho.training.di

import android.app.Application
import com.example.android.data.repositories.*
import com.example.android.data.sources.LocalMovieDataSource
import com.example.android.data.sources.LocationDataSource
import com.example.android.data.sources.RemoteMovieDataSource
import com.example.android.domain.Movie
import com.example.android.frameworkretrofit.data.requests.BaseRequest
import com.example.android.frameworkretrofit.data.requests.credit.CreditRequest
import com.example.android.frameworkretrofit.data.requests.keyword.KeywordRequest
import com.example.android.frameworkretrofit.data.requests.movie.MovieRequest
import com.example.android.frameworkretrofit.data.requests.person.PersonRequest
import com.example.android.frameworkretrofit.data.requests.review.ReviewRequest
import com.example.android.frameworkretrofit.data.requests.trailer.TrailerRequest
import com.example.android.frameworkroom.data.MovieDatabase
import com.example.android.usecases.*
import com.mho.training.R
import com.mho.training.features.credits.CreditsFragment
import com.mho.training.features.credits.CreditsViewModel
import com.mho.training.features.keywords.KeywordsFragment
import com.mho.training.features.keywords.KeywordsViewModel
import com.mho.training.features.main.MainActivity
import com.mho.training.features.main.MainViewModel
import com.mho.training.features.moviedetail.MovieDetailActivity
import com.mho.training.features.moviedetail.MovieDetailViewModel
import com.mho.training.features.movieinfo.MovieInfoFragment
import com.mho.training.features.movieinfo.MovieInfoViewModel
import com.mho.training.features.persondetail.PersonDetailActivity
import com.mho.training.features.persondetail.PersonDetailViewModel
import com.mho.training.features.relatedmoviesbyperson.RelatedMoviesByPersonFragment
import com.mho.training.features.relatedmoviesbyperson.RelatedMoviesByPersonViewModel
import com.mho.training.features.reviews.ReviewsFragment
import com.mho.training.features.reviews.ReviewsViewModel
import com.mho.training.features.trailers.TrailersFragment
import com.mho.training.features.trailers.TrailersViewModel
import com.mho.training.permissions.AndroidPermissionChecker
import com.mho.training.sources.MovieRoomMovieDataSource
import com.mho.training.sources.MovieServerMovieDataSource
import com.mho.training.sources.PlayServicesLocationDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, resourcesModule, requestModule, dataModule, scopesModule))
    }
}

private val appModule = module {
    single { MovieDatabase.getDatabase(get()) }
    single<CoroutineDispatcher> { Dispatchers.Main }
    factory<LocalMovieDataSource> { MovieRoomMovieDataSource(
        get(),
        get(named("errorUnableToFetchMovies")),
        get(named("errorUnableToFetchMovies")),
        get(named("formatVoteAverage"))
    ) }
    factory<RemoteMovieDataSource> { MovieServerMovieDataSource(
        get(named("errorUnableToFetchMovies")),
        get(named("errorUnableToFetchMovies")),
        get(named("formatVoteAverage")),
        get()
    ) }
    factory<LocationDataSource> { PlayServicesLocationDataSource(
        get()
    ) }
    factory<PermissionChecker> { AndroidPermissionChecker(
        get()
    ) }
}

private val dataModule = module {
    factory { CreditRepository(get()) }
    factory { KeywordRepository(get()) }
    factory { MovieRepository(get(), get(), get()) }
    factory { PersonRepository(get()) }
    factory { RegionRepository(get(), get()) }
    factory { ReviewRepository(get()) }
    factory { TrailerRepository(get()) }
}

private val requestModule = module {
    factory { MovieRequest(get(named("baseUrl"))) }
    factory { CreditRequest(get(named("baseUrl"))) }
    factory { KeywordRequest(get(named("baseUrl"))) }
    factory { PersonRequest(get(named("baseUrl"))) }
    factory { ReviewRequest(get(named("baseUrl"))) }
    factory { TrailerRequest(get(named("baseUrl"))) }
}

private val resourcesModule = module {
    single(named("baseUrl")) { BaseRequest.BASE_MOVIE_DB_URL }
    single(named("errorUnableToFetchCredits")) { androidApplication().getString(R.string.error_unable_to_fetch_credits) }
    single(named("errorDuringFetchingCredits")) { androidApplication().getString(R.string.error_during_fetching_credits) }
    single(named("errorUnableToFetchKeywords")) { androidApplication().getString(R.string.error_unable_to_fetch_keywords) }
    single(named("errorDuringFetchingKeywords")) { androidApplication().getString(R.string.error_during_fetching_keywords) }
    single(named("errorUnableToFetchMovies")) { androidApplication().getString(R.string.error_unable_to_fetch_movies) }
    single(named("errorDuringFetchingMovies")) { androidApplication().getString(R.string.error_during_fetching_movies) }
    single(named("errorUnableToFetchPerson")) { androidApplication().getString(R.string.error_unable_to_fetch_person) }
    single(named("errorDuringFetchingPerson")) { androidApplication().getString(R.string.error_during_fetching_person) }
    single(named("errorUnableToFetchReviews")) { androidApplication().getString(R.string.error_unable_to_fetch_reviews) }
    single(named("errorDuringFetchingReviews")) { androidApplication().getString(R.string.error_during_fetching_reviews) }
    single(named("errorUnableToFetchTrailers")) { androidApplication().getString(R.string.error_unable_to_fetch_trailers) }
    single(named("errorDuringFetchingTrailers")) { androidApplication().getString(R.string.error_during_fetching_trailers) }
    single(named("formatVoteAverage")) { androidApplication().getString(R.string.text_movie_detail_vote_average) }
}

private val scopesModule = module {
    scope(named<CreditsFragment>()) {
        viewModel { (movieId: Int) -> CreditsViewModel(movieId, get(), get()) }
        scoped { GetCreditListUseCase(get()) }

    }

    scope(named<KeywordsFragment>()) {
        viewModel { (movieId: Int) -> KeywordsViewModel(movieId, get(), get()) }
        scoped { GetKeywordListUseCase(get()) }
    }

    scope(named<MainActivity>()) {
        viewModel { MainViewModel(get(), get(), get(), get(), get(), get()) }
        scoped { GetFavoriteMovieListUseCase(get()) }
        scoped { GetFavoriteMovieListWithChangesUseCase(get()) }
        scoped { GetPopularMovieListUseCase(get()) }
        scoped { GetTopRatedMovieListUseCase(get()) }
        scoped { GetInTheatersMovieListUseCase(get()) }
    }

    scope(named<MovieDetailActivity>()) {
        viewModel { (movie: Movie?) -> MovieDetailViewModel(movie, get(), get(), get()) }
        scoped { GetFavoriteMovieStatus(get()) }
        scoped { UpdateFavoriteMovieStatusUseCase(get()) }
    }

    scope(named<MovieInfoFragment>()) {
        viewModel { (movie: Movie) -> MovieInfoViewModel(movie, get(), get()) }
        scoped { GetMovieDetailByIdUseCase(get()) }
    }

    scope(named<PersonDetailActivity>()) {
        viewModel { (personId: Int) -> PersonDetailViewModel(personId, get(), get()) }
        scoped { GetPersonInformationUseCase(get()) }
    }

    scope(named<RelatedMoviesByPersonFragment>()) {
        viewModel { (personId: Int) -> RelatedMoviesByPersonViewModel(personId, get(), get()) }
        scoped { GetMovieListByPersonUseCase(get()) }
    }

    scope(named<ReviewsFragment>()) {
        viewModel { (movieId: Int) -> ReviewsViewModel(movieId, get(), get()) }
        scoped { GetReviewListUseCase(get()) }
    }

    scope(named<TrailersFragment>()) {
        viewModel { (movieId: Int) -> TrailersViewModel(movieId, get(), get()) }
        scoped { GetTrailerListUseCase(get()) }
    }
}