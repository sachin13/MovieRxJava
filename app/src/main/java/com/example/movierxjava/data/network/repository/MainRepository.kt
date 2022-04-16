package com.example.movierxjava.data.network.repository

import com.example.movierxjava.data.network.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getAllMovies() = retrofitService.getAllMovies()
}