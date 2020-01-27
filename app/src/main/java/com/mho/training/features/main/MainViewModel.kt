package com.mho.training.features.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.android.domain.Movie
import com.example.android.domain.MovieCarousel
import com.example.android.domain.result.DataResult
import com.example.android.usecases.GetFavoriteMovieListWithChangesUseCase
import com.example.android.usecases.GetMovieCarouselListUseCase
import com.mho.training.utils.Event
import com.mho.training.utils.Scope
import kotlinx.coroutines.launch

class MainViewModel(
    private val getFavoriteMovieListWithChangesUseCase: GetFavoriteMovieListWithChangesUseCase,
    private val getMovieCarouselListUseCase: GetMovieCarouselListUseCase
) :
    ViewModel(), Scope by Scope.Impl() {

    //region Constructors

    init {
        initScope()
    }

    //endregion

    //region Fields

    private val _movieCarousels = MutableLiveData<List<MovieCarousel>>()
    val movieCarousels: LiveData<List<MovieCarousel>> get() = _movieCarousels

    val favoriteMovies: LiveData<List<Movie>> get() = getFavoriteMovieListWithChangesUseCase.invoke().asLiveData()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

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

    fun onMovieListRefresh() {
        _events.value = Event(Navigation.RequestLocationPermission)
    }

    fun onMovieClicked(movie: Movie) {
        _events.value = Event(Navigation.NavigateToMovie(movie))
    }

    fun onCoarsePermissionRequested(hasPermission: Boolean) {
        Log.d(TAG, "onCoarsePermissionRequested hasPermission -> $hasPermission")
        if(hasPermission){
            refresh()
            return
        }

        _loading.value = false
        _error.value = true
    }

    //endregion

    //region Private Methods

    private fun refresh(){
        Log.d(TAG, "refresh")
        launch {
            _loading.value = true
            _error.value = false

            validateMovieCarouselsResult(getMovieCarouselListUseCase.invoke())

            _loading.value = false
        }
    }

    private fun validateMovieCarouselsResult(movieCarouselListResult: DataResult<List<MovieCarousel>>){
        when(movieCarouselListResult){
            is DataResult.Success -> {
                _error.value = false
                _movieCarousels.value = movieCarouselListResult.data
            }
            is DataResult.Error -> {
                _error.value = true
                _movieCarousels.value = mutableListOf()
            }
        }
    }

    //endregion

    //region Inner Classes & Interfaces

    sealed class Navigation {
        class NavigateToMovie(val movie: Movie): Navigation()
        object RequestLocationPermission: Navigation()
    }

    //endregion

    companion object {

        private val TAG = MainViewModel::class.java.simpleName

    }
}