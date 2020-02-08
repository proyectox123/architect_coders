package com.mho.training.features.moviedetail

import com.example.android.data.repositories.*
import com.example.android.domain.Movie
import com.example.android.usecases.*
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class MovieDetailActivityModule(
    private val movie: Movie?
) {

    @Provides
    fun movieDetailViewModelProvider(
        getMovieDetailByIdUseCase: GetMovieDetailByIdUseCase,
        getFavoriteMovieStatus: GetFavoriteMovieStatus,
        updateFavoriteMovieStatusUseCase: UpdateFavoriteMovieStatusUseCase,
        getKeywordListUseCase: GetKeywordListUseCase,
        getCreditListUseCase: GetCreditListUseCase,
        getTrailerListUseCase: GetTrailerListUseCase,
        getReviewListUseCase: GetReviewListUseCase
    ) = MovieDetailViewModel(
        movie,
        getMovieDetailByIdUseCase,
        getFavoriteMovieStatus,
        updateFavoriteMovieStatusUseCase,
        getKeywordListUseCase,
        getCreditListUseCase,
        getTrailerListUseCase,
        getReviewListUseCase
    )

    @Provides
    fun getMovieDetailByIdUseCaseProvider(movieRepository: MovieRepository) =
        GetMovieDetailByIdUseCase(movieRepository)

    @Provides
    fun getFavoriteMovieStatusProvider(movieRepository: MovieRepository) =
        GetFavoriteMovieStatus(movieRepository)

    @Provides
    fun updateFavoriteMovieStatusProvider(movieRepository: MovieRepository) =
        UpdateFavoriteMovieStatusUseCase(movieRepository)

    @Provides
    fun getKeywordListUseCaseProvider(keywordRepository: KeywordRepository) =
        GetKeywordListUseCase(keywordRepository)

    @Provides
    fun getCreditListUseCaseProvider(creditRepository: CreditRepository) =
        GetCreditListUseCase(creditRepository)

    @Provides
    fun getTrailerListUseCaseProvider(trailerRepository: TrailerRepository) =
        GetTrailerListUseCase(trailerRepository)

    @Provides
    fun getReviewListUseCaseProvider(reviewRepository: ReviewRepository) =
        GetReviewListUseCase(reviewRepository)

}

@Subcomponent(modules = [(MovieDetailActivityModule::class)])
interface MovieDetailActivityComponent {
    val movieDetailViewModel: MovieDetailViewModel
}