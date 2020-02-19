package com.mho.training.features.moviedetail

import com.example.android.data.repositories.MovieRepository
import com.example.android.domain.Movie
import com.example.android.usecases.GetFavoriteMovieStatus
import com.example.android.usecases.GetMovieDetailByIdUseCase
import com.example.android.usecases.UpdateFavoriteMovieStatusUseCase
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
        updateFavoriteMovieStatusUseCase: UpdateFavoriteMovieStatusUseCase
    ) = MovieDetailViewModel(
        movie,
        getMovieDetailByIdUseCase,
        getFavoriteMovieStatus,
        updateFavoriteMovieStatusUseCase,
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

}

@Subcomponent(modules = [(MovieDetailActivityModule::class)])
interface MovieDetailActivityComponent {
    val movieDetailViewModel: MovieDetailViewModel
}