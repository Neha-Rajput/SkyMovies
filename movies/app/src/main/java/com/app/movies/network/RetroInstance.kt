package com.app.movies.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {

    companion object {
        val baseURL = "https://movies-sample.herokuapp.com/api/"//movies

        fun getRetroInstance(): Retrofit {


            return Retrofit.Builder().baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        }
    }
}