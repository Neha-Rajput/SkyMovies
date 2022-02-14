package com.app.movies.network

import com.app.movies.model.Movies
import io.reactivex.Observable
import retrofit2.http.GET

interface APIService {
    @GET("movies")
    fun fetchMovies(): Observable<Movies>
}