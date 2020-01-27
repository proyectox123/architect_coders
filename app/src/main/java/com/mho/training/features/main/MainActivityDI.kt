package com.mho.training.features.main

import com.example.android.data.repositories.MovieRepository
import com.example.android.usecases.GetFavoriteMovieListWithChangesUseCase
import com.example.android.usecases.GetMovieCarouselListUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class MainActivityModule {

    @Provides
    fun mainViewModelProvider(
        getFavoriteMovieListWithChangesUseCase: GetFavoriteMovieListWithChangesUseCase,
        getMovieCarouselListUseCase: GetMovieCarouselListUseCase
    ) = MainViewModel(
        getFavoriteMovieListWithChangesUseCase,
        getMovieCarouselListUseCase
    )

    @Provides
    fun getFavoriteMovieListWithChangesUseCaseProvider(movieRepository: MovieRepository) =
        GetFavoriteMovieListWithChangesUseCase(movieRepository)

    @Provides
    fun getMovieCarouselListUseCaseProvider(movieRepository: MovieRepository) =
        GetMovieCarouselListUseCase(movieRepository)
}

@Subcomponent(modules = [(MainActivityModule::class)])
interface MainActivityComponent {
    val mainViewModel: MainViewModel
}