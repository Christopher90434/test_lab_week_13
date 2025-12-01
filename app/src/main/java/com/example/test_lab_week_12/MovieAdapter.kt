package com.example.test_lab_week_12

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test_lab_week_12.model.Movie

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val movies = mutableListOf<Movie>()

    fun addMovies(newMovies: List<Movie>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.size

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.movie_title)
        private val releaseDateTextView: TextView = itemView.findViewById(R.id.movie_release_date)
        private val popularityTextView: TextView = itemView.findViewById(R.id.movie_popularity)

        fun bind(movie: Movie) {
            titleTextView.text = movie.title
            releaseDateTextView.text = movie.releaseDate ?: "N/A"
            popularityTextView.text = "Popularity: ${movie.popularity}"

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailsActivity::class.java).apply {
                    putExtra("MOVIE_TITLE", movie.title)
                    putExtra("MOVIE_OVERVIEW", movie.overview)
                    putExtra("MOVIE_RELEASE", movie.releaseDate)
                    putExtra("MOVIE_RATING", movie.voteAverage)
                }
                itemView.context.startActivity(intent)
            }
        }
    }
}
