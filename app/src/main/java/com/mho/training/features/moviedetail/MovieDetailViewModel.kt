package com.mho.training.features.moviedetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.domain.Keyword
import com.example.android.domain.Movie
import com.example.android.domain.Review
import com.example.android.domain.Trailer
import com.example.android.framework.data.remote.requests.Result
import com.example.android.usecases.*
import com.mho.training.utils.Event
import com.mho.training.utils.Scope
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movie: Movie?,
    private val getFavoriteMovieStatus: GetFavoriteMovieStatus,
    private val updateFavoriteMovieStatus: UpdateFavoriteMovieStatus,
    private val getKeywordListUseCase: GetKeywordListUseCase,
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

    private val _keywords = MutableLiveData<List<Keyword>>()
    val keywords: LiveData<List<Keyword>> get() = _keywords

    private val _hasNotKeywords = MutableLiveData<Boolean>()
    val hasNotKeywords: LiveData<Boolean> get() = _hasNotKeywords

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> get() = _reviews

    private val _hasNotReviews = MutableLiveData<Boolean>()
    val hasNotReviews: LiveData<Boolean> get() = _hasNotReviews

    private val _trailers = MutableLiveData<List<Trailer>>()
    val trailers: LiveData<List<Trailer>> get() = _trailers

    private val _hasNotTrailers = MutableLiveData<Boolean>()
    val hasNotTrailers: LiveData<Boolean> get() = _hasNotTrailers

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    private val _events = MutableLiveData<Event<Navigation>>()
    val events: LiveData<Event<Navigation>> get() = _events

    //endregion

    //region Override Methods & Callbacks

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    //endregion

    //region Public Methods

    fun onMovieInformation(){
        Log.d(TAG, "onMovieInformation movie -> $movie")
        if(movie == null){
            _events.value = Event(Navigation.CloseActivity)
            return
        }

        _infoMovie.value = movie

        launch {
            validateKeywordResult(getKeywordListUseCase.invoke(movie.id))
            validateTrailerResult(getTrailerListUseCase.invoke(movie.id))
            validateReviewResult(getReviewListUseCase.invoke(movie.id))
        }
    }

    fun onValidateFavoriteMovieStatus(){
        launch {
            _isFavorite.value = getFavoriteMovieStatus.invoke(movie!!)
        }
    }

    fun updateFavoriteMovieStatus() {
        launch {
            _isFavorite.value = updateFavoriteMovieStatus.invoke(movie!!)
        }
    }

    //endregion

    //region Private Methods

    private fun validateKeywordResult(keywordListResult: Result<List<Keyword>>){
        when(keywordListResult){
            is Result.Success -> {
                _keywords.value = keywordListResult.data
                _hasNotKeywords.value = false
            }
            is Result.Error -> {
                Log.d(TAG, "validateKeywordResult error message -> ${keywordListResult.exception.message}")
                _keywords.value = emptyList()
                _hasNotKeywords.value = true
            }
        }
    }

    private fun validateTrailerResult(trailerListResult: Result<List<Trailer>>){
        when(trailerListResult){
            is Result.Success -> {
                _trailers.value = trailerListResult.data
                _hasNotTrailers.value = false
            }
            is Result.Error -> {
                Log.d(TAG, "validateTrailerResult error message -> ${trailerListResult.exception.message}")
                _trailers.value = emptyList()
                _hasNotTrailers.value = true
            }
        }
    }

    private fun validateReviewResult(reviewListResult: Result<List<Review>>){
        when(reviewListResult){
            is Result.Success -> {
                _reviews.value = reviewListResult.data
                _hasNotReviews.value = false
            }
            is Result.Error -> {
                Log.d(TAG, "validateReviewResult error message -> ${reviewListResult.exception.message}")
                _reviews.value = emptyList()
                _hasNotReviews.value = true
            }
        }
    }

    //endregion

    //region Inner Classes & Interfaces

    sealed class Navigation {
        object CloseActivity: Navigation()
    }

    //endregion

    companion object {

        private val TAG = MovieDetailViewModel::class.java.simpleName

    }

}