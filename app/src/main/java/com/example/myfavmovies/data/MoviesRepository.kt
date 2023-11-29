package com.example.myfavmovies.data

import com.example.myfavmovies.model.Movies
import com.example.myfavmovies.model.MoviesData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class MoviesRepository {
    private val dummyMovies = mutableListOf<Movies>()

    init {
        if (dummyMovies.isEmpty()) {
            MoviesData.dummyMovies.forEach {
                dummyMovies.add(it)
            }
        }
    }

    fun getMoviesById(moviesId: Int): Movies {
        return dummyMovies.first {
            it.id == moviesId
        }
    }

    fun getFavoriteMovies(): Flow<List<Movies>> {
        return flowOf(dummyMovies.filter { it.isFavorite })
    }

    fun searchMovies(query: String) = flow {
        val data = dummyMovies.filter {
            it.name.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    fun updateMovies(moviesId: Int, newState: Boolean): Flow<Boolean> {
        val index = dummyMovies.indexOfFirst { it.id == moviesId }
        val result = if (index >= 0) {
            val movies = dummyMovies[index]
            dummyMovies[index] = movies.copy(isFavorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: MoviesRepository? = null

        fun getInstance(): MoviesRepository =
            instance ?: synchronized(this) {
                MoviesRepository().apply {
                    instance = this
                }
            }
    }
}