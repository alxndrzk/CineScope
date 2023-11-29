package com.example.myfavmovies.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object DetailMovies : Screen("home/{moviesId}") {
        fun createRoute(moviesId: Int) = "home/$moviesId"
    }
}