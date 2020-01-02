package com.mho.training.features.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mho.training.data.remote.models.ServerReview
import com.mho.training.domain.Movie
import com.mho.training.domain.Review
import com.mho.training.domain.Trailer
import com.mho.training.usecases.GetReviewListUseCase
import com.mho.training.usecases.GetTrailerListUseCase
import com.mho.training.utils.Scope
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movie: Movie?,
    private val getTrailerListUseCase: GetTrailerListUseCase,
    private val getReviewListUseCase: GetReviewListUseCase
) : ViewModel(), Scope by Scope.Impl() {

    //region Constructors

    init {
        initScope()
    }

    //endregion

    //region Fields

    private val _infoMovie = MutableLiveData<Movie>()
    val infoMovie: LiveData<Movie> get() = _infoMovie

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> get() = _reviews

    private val _trailers = MutableLiveData<List<Trailer>>()
    val trailers: LiveData<List<Trailer>> get() = _trailers

    //endregion

    //region Override Methods & Callbacks

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    //endregion

    //region Public Methods

    fun onMovieInformation(){
        if(movie == null){
            //finish
            return
        }

        _infoMovie.value = movie

        launch {
            _trailers.value = getTrailerListUseCase.invoke(movie.id)
            _reviews.value = getReviewListUseCase.invoke(movie.id)
        }
    }

    //endregion

}