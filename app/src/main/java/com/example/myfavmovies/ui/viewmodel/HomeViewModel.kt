package com.example.myfavmovies.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfavmovies.data.MoviesRepository
import com.example.myfavmovies.model.Movies
import com.example.myfavmovies.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MoviesRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Movies>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Movies>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) = viewModelScope.launch {
        _query.value = newQuery
        repository.searchMovies(_query.value)
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updateMovies(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateMovies(id, newState)
            .collect { isUpdated ->
                if (isUpdated) search(_query.value)
            }
    }
}