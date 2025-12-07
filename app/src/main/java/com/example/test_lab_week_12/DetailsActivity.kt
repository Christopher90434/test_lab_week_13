package com.example.test_lab_week_13

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val title = intent.getStringExtra("MOVIE_TITLE")
        val overview = intent.getStringExtra("MOVIE_OVERVIEW")
        val releaseDate = intent.getStringExtra("MOVIE_RELEASE")
        val rating = intent.getDoubleExtra("MOVIE_RATING", 0.0)

        findViewById<TextView>(R.id.detail_title).text = title
        findViewById<TextView>(R.id.detail_overview).text = overview
        findViewById<TextView>(R.id.detail_release).text = "Release: $releaseDate"
        findViewById<TextView>(R.id.detail_rating).text = "Rating: $rating"
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
