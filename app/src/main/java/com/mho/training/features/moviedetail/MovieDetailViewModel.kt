package com.mho.training.features.moviedetail

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mho.training.R
import com.mho.training.data.remote.models.Review
import com.mho.training.data.remote.models.Trailer
import com.mho.training.domain.Movie
import com.mho.training.utils.Scope

class MovieDetailViewModel(
    private val movie: Movie?,
    private val resources: Resources
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

        //TODO
        val dummyReviews = mutableListOf<Review>()
        dummyReviews.add(Review("1", "Author 1", resources.getString(R.string.content_lorem_ipsum), "http://www.google.com.mx"))
        dummyReviews.add(Review("2", "Author 2", resources.getString(R.string.content_lorem_ipsum), "http://www.google.com.mx"))
        _reviews.value = dummyReviews

        val dummyTrailers = mutableListOf<Trailer>()
        dummyTrailers.add(Trailer("", "Trailer 1", "https://img.youtube.com/vi/aYWB3oOBk6c/default.jpg", "https://www.youtube.com/watch?v=aYWB3oOBk6c"))

        _trailers.value = dummyTrailers
    }

    //endregion

    //region Inner Classes & Interfaces

    class UiModel(val movie: Movie)

    //endregion

}