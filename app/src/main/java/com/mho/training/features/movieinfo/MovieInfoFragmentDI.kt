package com.mho.training.features.movieinfo

import com.example.android.data.repositories.MovieRepository
import com.example.android.domain.Movie
import com.example.android.usecases.GetMovieDetailByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.Dispatchers

@Module
class MovieInfoFragmentModule(
    private val movie: Movie
) {

    @Provides
    fun movieInfoViewModelProvider(
        getMovieDetailByIdUseCase: GetMovieDetailByIdUseCase
    ) = MovieInfoViewModel(
        movie,
        getMovieDetailByIdUseCase,
        Dispatchers.Main
    )

    @Provides
    fun getMovieDetailByIdUseCaseProvider(movieRepository: MovieRepository) =
        GetMovieDetailByIdUseCase(movieRepository)

}

@Subcomponent(modules = [(MovieInfoFragmentModule::class)])
interface MovieInfoFragmentComponent {
    val movieInfoViewModel: MovieInfoViewModel
}