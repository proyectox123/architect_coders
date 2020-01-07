package com.mho.training.features.moviedetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.domain.Keyword
import com.example.android.domain.Movie
import com.example.android.domain.Review
import com.example.android.domain.Trailer
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

    private val _hasKeywords = MutableLiveData<Boolean>()
    val hasKeywords: LiveData<Boolean> get() = _hasKeywords

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> get() = _reviews

    private val _hasReviews = MutableLiveData<Boolean>()
    val hasReviews: LiveData<Boolean> get() = _hasReviews

    private val _trailers = MutableLiveData<List<Trailer>>()
    val trailers: LiveData<List<Trailer>> get() = _trailers

    private val _hasTrailers = MutableLiveData<Boolean>()
    val hasTrailers: LiveData<Boolean> get() = _hasTrailers

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
            val keywordList = getKeywordListUseCase.invoke(movie.id)
            _keywords.value = keywordList
            _hasKeywords.value = keywordList.isEmpty()

            val trailerList = getTrailerListUseCase.invoke(movie.id)
            _trailers.value = trailerList
            _hasTrailers.value = trailerList.isEmpty()

            val reviewList = getReviewListUseCase.invoke(movie.id)
            _reviews.value = reviewList
            _hasReviews.value = reviewList.isEmpty()
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

    //region Inner Classes & Interfaces

    sealed class Navigation {
        object CloseActivity: Navigation()
    }

    //endregion

    companion object {

        private val TAG = MovieDetailViewModel::class.java.simpleName

    }

}