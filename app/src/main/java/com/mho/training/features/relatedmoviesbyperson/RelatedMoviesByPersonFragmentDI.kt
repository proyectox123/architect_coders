package com.mho.training.features.relatedmoviesbyperson

import com.example.android.data.repositories.MovieRepository
import com.example.android.usecases.GetMovieListByPersonUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.Dispatchers

@Module
class RelatedMoviesByPersonFragmentModule(
    private val personId: Int
) {

    @Provides
    fun relatedMoviesByPersonViewModelProvider(
        getMovieListByPersonUseCase: GetMovieListByPersonUseCase
    ) = RelatedMoviesByPersonViewModel(
        personId,
        getMovieListByPersonUseCase,
        Dispatchers.Main
    )

    @Provides
    fun getMovieListByPersonUseCaseProvider(movieRepository: MovieRepository) =
        GetMovieListByPersonUseCase(movieRepository)

}

@Subcomponent(modules = [(RelatedMoviesByPersonFragmentModule::class)])
interface RelatedMoviesByPersonFragmentComponent {
    val relatedMoviesByPersonViewModel: RelatedMoviesByPersonViewModel
}