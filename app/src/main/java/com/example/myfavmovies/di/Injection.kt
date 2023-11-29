package com.example.myfavmovies.di

import com.example.myfavmovies.data.MoviesRepository

object Injection {
    fun provideRepository(): MoviesRepository {
        return MoviesRepository.getInstance()
    }
}