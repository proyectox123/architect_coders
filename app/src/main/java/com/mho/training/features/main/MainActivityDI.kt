package com.mho.training.features.main

import com.example.android.data.repositories.MovieRepository
import com.example.android.usecases.*
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.Dispatchers

@Module
class MainActivityModule {

    @Provides
    fun mainViewModelProvider(
        getFavoriteMovieListUseCase: GetFavoriteMovieListUseCase,
        getFavoriteMovieListWithChangesUseCase: GetFavoriteMovieListWithChangesUseCase,
        getPopularMovieListUseCase: GetPopularMovieListUseCase,
        getTopRatedMovieListUseCase: GetTopRatedMovieListUseCase,
        getInTheatersMovieListUseCase: GetInTheatersMovieListUseCase
    ) = MainViewModel(
        getFavoriteMovieListUseCase,
        getFavoriteMovieListWithChangesUseCase,
        getPopularMovieListUseCase,
        getTopRatedMovieListUseCase,
        getInTheatersMovieListUseCase,
        Dispatchers.Main
    )

    @Provides
    fun getFavoriteMovieListUseCaseProvider(movieRepository: MovieRepository) =
        GetFavoriteMovieListUseCase(movieRepository)

    @Provides
    fun getFavoriteMovieListWithChangesUseCaseProvider(movieRepository: MovieRepository) =
        GetFavoriteMovieListWithChangesUseCase(movieRepository)

    @Provides
    fun getPopularMovieListUseCaseProvider(movieRepository: MovieRepository) =
        GetPopularMovieListUseCase(movieRepository)

    @Provides
    fun getTopRatedMovieListUseCaseProvider(movieRepository: MovieRepository) =
        GetTopRatedMovieListUseCase(movieRepository)

    @Provides
    fun getInTheatersMovieListUseCaseProvider(movieRepository: MovieRepository) =
        GetInTheatersMovieListUseCase(movieRepository)
}

@Subcomponent(modules = [(MainActivityModule::class)])
interface MainActivityComponent {
    val mainViewModel: MainViewModel
}