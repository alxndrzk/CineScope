package com.example.myfavmovies.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfavmovies.data.MoviesRepository
import com.example.myfavmovies.model.Movies
import com.example.myfavmovies.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: MoviesRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Movies>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Movies>>
        get() = _uiState

    fun getMoviesById(id: Int) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(repository.getMoviesById(id))
    }


    fun updateMovies(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateMovies(id, !newState)
            .collect { isUpdated ->
                if (isUpdated) getMoviesById(id)
            }
    }

}