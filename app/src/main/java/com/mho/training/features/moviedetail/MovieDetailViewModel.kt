package com.mho.training.features.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.domain.Movie
import com.example.android.domain.Review
import com.example.android.domain.Trailer
import com.example.android.usecases.GetFavoriteMovieStatus
import com.example.android.usecases.GetReviewListUseCase
import com.example.android.usecases.GetTrailerListUseCase
import com.example.android.usecases.UpdateFavoriteMovieStatus
import com.mho.training.utils.Scope
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movie: Movie?,
    private val getFavoriteMovieStatus: GetFavoriteMovieStatus,
    private val updateFavoriteMovieStatus: UpdateFavoriteMovieStatus,
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

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

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

    fun onValidateFavoriteMovieStatus(){
        launch {
            _isFavorite.value = getFavoriteMovieStatus.invoke(movie!!)
        }
    }

    fun onUpdateFavoriteMovieStatus() {
        launch {
            _isFavorite.value = updateFavoriteMovieStatus.invoke(movie!!)
        }
    }

    //endregion

    companion object {

        private val TAG = MovieDetailViewModel::class.java.simpleName

    }

}