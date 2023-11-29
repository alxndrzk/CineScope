package com.example.myfavmovies.model


data class Movies(
    val id: Int,
    val name: String,
    val year: Int,
    val image: Int,
    val description: String,
    val actor: String,
    val rating: Double,
    var isFavorite: Boolean = false
)