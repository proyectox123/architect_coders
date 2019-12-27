package com.mho.training.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mho.training.domain.Movie
import com.mho.training.enums.MovieCategoryEnum
import com.mho.training.usecases.GetFavoriteMovieList
import com.mho.training.usecases.GetPopularMovieList
import com.mho.training.usecases.GetTopRatedMovieList
import com.mho.training.utils.Event
import com.mho.training.utils.Scope
import kotlinx.coroutines.launch

class MainViewModel(
    private val getTopRatedMovieList: GetTopRatedMovieList,
    private val getPopularMovieList: GetPopularMovieList,
    private val getFavoriteMovieList: GetFavoriteMovieList
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

    private val _navigateToMovie = MutableLiveData<Event<Int>>()
    val navigateToMovie: LiveData<Event<Int>> get() = _navigateToMovie

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
        refresh(_movieCategory.value ?: MovieCategoryEnum.TOP_RATED)
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
        _navigateToMovie.value = Event(movie.id)
    }

    //endregion

    //region Private Methods

    private fun refresh(movieCategory: MovieCategoryEnum) {
        launch {
            _loading.value = true
            _error.value = false

            val movieList: List<Movie>? = when (movieCategory) {
                MovieCategoryEnum.TOP_RATED -> getTopRatedMovieList.invoke()
                MovieCategoryEnum.POPULAR -> getPopularMovieList.invoke()
                MovieCategoryEnum.FAVORITE -> getFavoriteMovieList.invoke()
            }

            _error.value = movieList.isNullOrEmpty()
            _movies.value = movieList

            _loading.value = false
        }
    }

    //endregion
}