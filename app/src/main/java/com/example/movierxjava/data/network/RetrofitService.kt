package com.example.movierxjava.data.network

import com.example.movierxjava.data.network.model.MovieList
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {
    @GET("api?s=batman")
    fun getAllMovies(): Observable<MovieList>

    companion object {

        var retrofitService: RetrofitService? = null

        //Create the RetrofitService instance using the retrofit.
        fun getInstance(): RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://fake-movie-database-api.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())

                    //You need to tell Retrofit that you want to use RxJava 3
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}