package com.mho.training.features.moviedetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.domain.*
import com.example.android.domain.result.DataResult
import com.example.android.usecases.*
import com.mho.training.utils.Event
import com.mho.training.utils.Scope
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movie: Movie?,
    private val getMovieDetailByIdUseCase: GetMovieDetailByIdUseCase,
    private val getFavoriteMovieStatus: GetFavoriteMovieStatus,
    private val updateFavoriteMovieStatusUseCase: UpdateFavoriteMovieStatusUseCase,
    private val getKeywordListUseCase: GetKeywordListUseCase,
    private val getCreditListUseCase: GetCreditListUseCase,
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

    private val _movieDetail = MutableLiveData<MovieDetail>()
    val movieDetail: LiveData<MovieDetail> get() = _movieDetail

    private val _credits = MutableLiveData<List<Credit>>()
    val credits: LiveData<List<Credit>> get() = _credits

    private val _loadingCredits = MutableLiveData<Boolean>()
    val loadingCredits: LiveData<Boolean> get() = _loadingCredits

    private val _hasNotCredits = MutableLiveData<Boolean>()
    val hasNotCredits: LiveData<Boolean> get() = _hasNotCredits

    private val _keywords = MutableLiveData<List<Keyword>>()
    val keywords: LiveData<List<Keyword>> get() = _keywords

    private val _loadingKeywords = MutableLiveData<Boolean>()
    val loadingKeywords: LiveData<Boolean> get() = _loadingKeywords

    private val _hasNotKeywords = MutableLiveData<Boolean>()
    val hasNotKeywords: LiveData<Boolean> get() = _hasNotKeywords

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> get() = _reviews

    private val _loadingReviews = MutableLiveData<Boolean>()
    val loadingReviews: LiveData<Boolean> get() = _loadingReviews

    private val _hasNotReviews = MutableLiveData<Boolean>()
    val hasNotReviews: LiveData<Boolean> get() = _hasNotReviews

    private val _trailers = MutableLiveData<List<Trailer>>()
    val trailers: LiveData<List<Trailer>> get() = _trailers

    private val _loadingTrailers = MutableLiveData<Boolean>()
    val loadingTrailers: LiveData<Boolean> get() = _loadingTrailers

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
            validateMovieDetailResult(getMovieDetailByIdUseCase.invoke(movie.id))

            _loadingCredits.value = true
            validateCreditResult(getCreditListUseCase.invoke(movie.id))
            _loadingCredits.value = false

            _loadingKeywords.value = true
            validateKeywordResult(getKeywordListUseCase.invoke(movie.id))
            _loadingKeywords.value = false

            _loadingTrailers.value = true
            validateTrailerResult(getTrailerListUseCase.invoke(movie.id))
            _loadingTrailers.value = false

            _loadingReviews.value = true
            validateReviewResult(getReviewListUseCase.invoke(movie.id))
            _loadingReviews.value = false
        }
    }

    fun onValidateFavoriteMovieStatus(){
        launch {
            _isFavorite.value = getFavoriteMovieStatus.invoke(movie!!)
        }
    }

    fun updateFavoriteMovieStatus() {
        launch {
            _isFavorite.value = updateFavoriteMovieStatusUseCase.invoke(movie!!)
        }
    }

    //endregion

    //region Private Methods

    private fun validateMovieDetailResult(movieDetailResult: DataResult<MovieDetail>){
        when(movieDetailResult){
            is DataResult.Success -> {
                _movieDetail.value = movieDetailResult.data
            }
            is DataResult.Error -> {
                _movieDetail.value = null
            }
        }
    }

    private fun validateCreditResult(creditListResult: DataResult<List<Credit>>){
        when(creditListResult){
            is DataResult.Success -> {
                _credits.value = creditListResult.data
                _hasNotCredits.value = false
            }
            is DataResult.Error -> {
                Log.d(TAG, "validateCreditResult error message -> ${creditListResult.exception.message}")
                _credits.value = emptyList()
                _hasNotCredits.value = true
            }
        }
    }

    private fun validateKeywordResult(keywordListResult: DataResult<List<Keyword>>){
        when(keywordListResult){
            is DataResult.Success -> {
                _keywords.value = keywordListResult.data
                _hasNotKeywords.value = false
            }
            is DataResult.Error -> {
                Log.d(TAG, "validateKeywordResult error message -> ${keywordListResult.exception.message}")
                _keywords.value = emptyList()
                _hasNotKeywords.value = true
            }
        }
    }

    private fun validateTrailerResult(trailerListResult: DataResult<List<Trailer>>){
        when(trailerListResult){
            is DataResult.Success -> {
                _trailers.value = trailerListResult.data
                _hasNotTrailers.value = false
            }
            is DataResult.Error -> {
                Log.d(TAG, "validateTrailerResult error message -> ${trailerListResult.exception.message}")
                _trailers.value = emptyList()
                _hasNotTrailers.value = true
            }
        }
    }

    private fun validateReviewResult(reviewListResult: DataResult<List<Review>>){
        when(reviewListResult){
            is DataResult.Success -> {
                _reviews.value = reviewListResult.data
                _hasNotReviews.value = false
            }
            is DataResult.Error -> {
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