package com.mho.training.features.moviedetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.mho.training.R
import com.mho.training.adapters.review.ReviewListAdapter
import com.mho.training.data.remote.models.Review
import kotlinx.android.synthetic.main.activity_movie_detail.*


class MovieDetailActivity : AppCompatActivity() {

    //region Fields

    private val TAG = MovieDetailActivity::class.java.simpleName

    private lateinit var reviewListAdapter: ReviewListAdapter

    //endregion

    //region Override Methods & Callbacks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        reviewListAdapter = ReviewListAdapter(::openReview)

        reviewListView.adapter = reviewListAdapter

        //TODO
        val dummyReviews = mutableListOf<Review>()
        dummyReviews.add(Review("1", "Author 1", getString(R.string.content_lorem_ipsum), "http://www.google.com.mx"))
        dummyReviews.add(Review("2", "Author 2", getString(R.string.content_lorem_ipsum), "http://www.google.com.mx"))

        reviewListAdapter.reviews = dummyReviews
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.movie_detail, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        if (item.itemId == R.id.action_favorite) {
            //updateFavoriteMovieStatus
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    //endregion

    //region Private Methods

    private fun openReview(review: Review){
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(review.url)
        }

        startActivity(intent)
    }

    //endregion
}
