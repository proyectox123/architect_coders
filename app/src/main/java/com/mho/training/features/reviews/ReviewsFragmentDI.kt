package com.mho.training.features.reviews

import com.example.android.data.repositories.ReviewRepository
import com.example.android.usecases.GetReviewListUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import kotlinx.coroutines.Dispatchers

@Module
class ReviewsFragmentModule(
    private val movieId: Int
) {

    @Provides
    fun reviewsViewModelProvider(
        getReviewListUseCase: GetReviewListUseCase
    ) = ReviewsViewModel(
        movieId,
        getReviewListUseCase,
        Dispatchers.Main
    )

    @Provides
    fun getReviewListUseCaseProvider(reviewRepository: ReviewRepository) =
        GetReviewListUseCase(reviewRepository)

}

@Subcomponent(modules = [(ReviewsFragmentModule::class)])
interface ReviewsFragmentComponent {
    val reviewsViewModel: ReviewsViewModel
}