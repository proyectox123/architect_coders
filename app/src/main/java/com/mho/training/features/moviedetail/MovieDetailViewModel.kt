package com.mho.training.features.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.domain.Credit
import com.example.android.domain.Movie
import com.example.android.domain.MovieDetail
import com.example.android.domain.result.DataResult
import com.example.android.usecases.GetCreditListUseCase
import com.example.android.usecases.GetFavoriteMovieStatus
import com.example.android.usecases.GetMovieDetailByIdUseCase
import com.example.android.usecases.UpdateFavoriteMovieStatusUseCase
import com.mho.training.bases.BaseViewModel
import com.mho.training.utils.Event
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movie: Movie?,
    private val getMovieDetailByIdUseCase: GetMovieDetailByIdUseCase,
    private val getFavoriteMovieStatus: GetFavoriteMovieStatus,
    private val updateFavoriteMovieStatusUseCase: UpdateFavoriteMovieStatusUseCase,
    private val getCreditListUseCase: GetCreditListUseCase,
    uiDispatcher: CoroutineDispatcher
) : BaseViewModel(uiDispatcher) {

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
                _credits.value = emptyList()
                _hasNotCredits.value = true
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