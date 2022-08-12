package com.mho.training.features.trailers.di

import com.example.android.data.repositories.TrailerRepository
import com.example.android.usecases.GetTrailerListUseCase
import com.mho.training.features.trailers.TrailersViewModel
import com.mho.training.features.trailers.alias.*
import com.mho.training.features.trailers.mvi.*
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@Module
class TrailersFragmentModule(
    private val movieId: Int
) {

    @Provides
    fun intentInterpreterForTrailerProvider(): IntentInterpreterForTrailer =
        TrailerIntentInterpreter(movieId)

    @Provides
    fun actionProcessorForTrailerProvider(
        getTrailerListUseCase: GetTrailerListUseCase
    ): ActionProcessorForTrailer =
        TrailerActionProcessor(getTrailerListUseCase)

    @Provides
    fun viewStateReducerForTrailerProvider(): ViewStateReducerForTrailer = TrailerViewStateReducer()

    @Provides
    fun sideEffectFactoryForTrailerProvider(): SideEffectFactoryForTrailer =
        TrailerSideEffectFactory()

    @Provides
    fun stateMachineForTrailerProvider(
        intentInterpreter: IntentInterpreterForTrailer,
        actionProcessor: ActionProcessorForTrailer,
        reducer: ViewStateReducerForTrailer,
        sideEffectFactory: SideEffectFactoryForTrailer,
    ): StateMachineForTrailer =
        TrailerStateMachine(
            intentInterpreter,
            actionProcessor,
            reducer,
            sideEffectFactory
        )

    @Provides
    fun reviewsViewModelProvider(
        reviewStateMachine: StateMachineForTrailer
    ) = TrailersViewModel(
        reviewStateMachine,
        Dispatchers.Main
    )

    @Provides
    fun getTrailerListUseCaseProvider(trailerRepository: TrailerRepository) =
        GetTrailerListUseCase(trailerRepository)

}

@ExperimentalCoroutinesApi
@FlowPreview
@Subcomponent(modules = [(TrailersFragmentModule::class)])
interface TrailersFragmentComponent {
    val trailersViewModel: TrailersViewModel
}