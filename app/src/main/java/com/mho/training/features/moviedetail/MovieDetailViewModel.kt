package com.mho.training.features.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.domain.Movie
import com.example.android.usecases.GetFavoriteMovieStatus
import com.example.android.usecases.UpdateFavoriteMovieStatusUseCase
import com.mho.training.bases.BaseViewModel
import com.mho.training.utils.Event
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movie: Movie?,
    private val getFavoriteMovieStatus: GetFavoriteMovieStatus,
    private val updateFavoriteMovieStatusUseCase: UpdateFavoriteMovieStatusUseCase,
    uiDispatcher: CoroutineDispatcher
) : BaseViewModel(uiDispatcher) {

    //region Constructors

    init {
        initScope()
    }

    //endregion

    //region Fields

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

    fun onMovieValidation(){
        if(movie == null){
            _events.value = Event(Navigation.CloseActivity)
            return
        }

        _events.value = Event(Navigation.InitializeMovieDetail(movie))
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

    //region Inner Classes & Interfaces

    sealed class Navigation {
        data class InitializeMovieDetail(val movie: Movie): Navigation()
        object CloseActivity: Navigation()
    }

    //endregion

}