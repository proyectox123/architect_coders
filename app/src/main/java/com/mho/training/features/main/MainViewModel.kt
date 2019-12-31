package com.mho.training.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mho.training.domain.Movie
import com.mho.training.enums.MovieCategoryEnum
import com.mho.training.usecases.GetFavoriteMovieListUseCase
import com.mho.training.usecases.GetPopularMovieListUseCase
import com.mho.training.usecases.GetTopRatedMovieListUseCase
import com.mho.training.utils.Event
import com.mho.training.utils.Scope
import kotlinx.coroutines.launch

class MainViewModel(
    private val getFavoriteMovieListUseCase: GetFavoriteMovieListUseCase,
    private val getPopularMovieListUseCase: GetPopularMovieListUseCase,
    private val getTopRatedMovieListUseCase: GetTopRatedMovieListUseCase
) :
    ViewModel(), Scope by Scope.Impl() {

    //region Constructors

    init {
        initScope()
    }

    //endregion

    //region Fields

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _requestLocationPermission = MutableLiveData<Event<Unit>>()
    val requestLocationPermission: LiveData<Event<Unit>> get() = _requestLocationPermission

    private val _navigateToMovie = MutableLiveData<Event<Movie>>()
    val navigateToMovie: LiveData<Event<Movie>> get() = _navigateToMovie

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    private val _movieCategory = MutableLiveData<MovieCategoryEnum>()
    val movieCategory: LiveData<MovieCategoryEnum>
        get() {
            if (_movieCategory.value == null) {
                _movieCategory.value = MovieCategoryEnum.TOP_RATED
            }

            return _movieCategory
        }

    //endregion

    //region Override Methods & Callbacks

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }

    //endregion

    //region Public Methods

    fun onMovieListRefresh() {
        _requestLocationPermission.value = Event(Unit)
    }

    fun onMovieCategory() = _movieCategory.value

    fun onMoviePopularListRefresh() {
        _movieCategory.value = MovieCategoryEnum.POPULAR
    }

    fun onMovieHighestRatedListRefresh() {
        _movieCategory.value = MovieCategoryEnum.TOP_RATED
    }

    fun onMovieFavoriteListRefresh() {
        _movieCategory.value = MovieCategoryEnum.FAVORITE
    }

    fun onMovieClicked(movie: Movie) {
        _navigateToMovie.value = Event(movie)
    }

    fun onCoarsePermissionRequested(hasPermission: Boolean) {
        if(hasPermission){
            refresh(_movieCategory.value ?: MovieCategoryEnum.TOP_RATED)
            return
        }

        _loading.value = false
        _error.value = true
    }

    //endregion

    //region Private Methods

    private fun refresh(movieCategory: MovieCategoryEnum){
        launch {
            _loading.value = true
            _error.value = false

            val movieList: List<Movie>? = when (movieCategory) {
                MovieCategoryEnum.TOP_RATED -> getTopRatedMovieListUseCase.invoke()
                MovieCategoryEnum.POPULAR -> getPopularMovieListUseCase.invoke()
                MovieCategoryEnum.FAVORITE -> getFavoriteMovieListUseCase.invoke()
            }

            _error.value = movieList.isNullOrEmpty()
            _movies.value = movieList

            _loading.value = false
        }
    }

    //endregion
}