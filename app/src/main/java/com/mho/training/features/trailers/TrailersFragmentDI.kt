package com.mho.training.features.trailers

import com.example.android.data.repositories.TrailerRepository
import com.example.android.usecases.GetTrailerListUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.Dispatchers

@Module
class TrailersFragmentModule(
    private val movieId: Int
) {

    @Provides
    fun trailersViewModelProvider(
        getTrailerListUseCase: GetTrailerListUseCase
    ) = TrailersViewModel(
        movieId,
        getTrailerListUseCase,
        Dispatchers.Main
    )

    @Provides
    fun getTrailerListUseCaseProvider(trailerRepository: TrailerRepository) =
        GetTrailerListUseCase(trailerRepository)

}

@Subcomponent(modules = [(TrailersFragmentModule::class)])
interface TrailersFragmentComponent {
    val trailersViewModel: TrailersViewModel
}