package com.example.test_lab_week_12

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test_lab_week_12.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.util.Calendar

class MovieViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    init {
        fetchPopularMovies()
    }

    // LiveData untuk Part 1 (masih ada untuk backward compatibility)
    val popularMovies: LiveData<List<Movie>>
        get() = movieRepository.movies
    val error: LiveData<String>
        get() = movieRepository.error

    // StateFlow untuk Part 2
    private val _popularMoviesFlow = MutableStateFlow(emptyList<Movie>())
    val popularMoviesFlow: StateFlow<List<Movie>> = _popularMoviesFlow

    private val _errorFlow = MutableStateFlow("")
    val errorFlow: StateFlow<String> = _errorFlow

    // Fetch untuk Part 1 (dengan LiveData)
    private fun fetchPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchMovies()
        }
    }

    // Fetch untuk Part 2 (dengan Flow) + ASSIGNMENT: Filter & Sort
    fun fetchPopularMoviesFlow() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchMoviesFlow()
                .catch {
                    _errorFlow.value = "An exception occurred: ${it.message}"
                }
                .collect { movies ->
                    // ASSIGNMENT: Filter by current year & Sort by popularity
                    val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
                    val filteredMovies = movies
                        .filter { movie ->
                            movie.releaseDate?.startsWith(currentYear) == true
                        }
                        .sortedByDescending { it.popularity }

                    _popularMoviesFlow.value = filteredMovies
                }
        }
    }
}
