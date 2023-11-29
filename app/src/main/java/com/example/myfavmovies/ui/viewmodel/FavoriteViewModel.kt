package com.example.myfavmovies.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfavmovies.data.MoviesRepository
import com.example.myfavmovies.model.Movies
import com.example.myfavmovies.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val repository: MoviesRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Movies>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Movies>>>
        get() = _uiState

    fun getFavoriteMovies() = viewModelScope.launch {
        repository.getFavoriteMovies()
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updateMovies(id: Int, newState: Boolean) {
        repository.updateMovies(id, newState)
        getFavoriteMovies()
    }
}