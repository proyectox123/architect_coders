package com.mho.training.features.moviedetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mho.training.R

class MovieDetailActivity : AppCompatActivity() {

    //region Fields

    private val TAG = MovieDetailActivity::class.java.simpleName

    //endregion

    //region Override Methods & Callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

    }

    //endregion
}
