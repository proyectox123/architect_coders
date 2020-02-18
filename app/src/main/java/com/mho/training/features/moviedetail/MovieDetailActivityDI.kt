package com.mho.training.features.moviedetail

import com.example.android.data.repositories.CreditRepository
import com.example.android.data.repositories.KeywordRepository
import com.example.android.data.repositories.MovieRepository
import com.example.android.domain.Movie
import com.example.android.usecases.*
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.Dispatchers

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
        getCreditListUseCase: GetCreditListUseCase
    ) = MovieDetailViewModel(
        movie,
        getMovieDetailByIdUseCase,
        getFavoriteMovieStatus,
        updateFavoriteMovieStatusUseCase,
        getKeywordListUseCase,
        getCreditListUseCase,
        Dispatchers.Main
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

}

@Subcomponent(modules = [(MovieDetailActivityModule::class)])
interface MovieDetailActivityComponent {
    val movieDetailViewModel: MovieDetailViewModel
}