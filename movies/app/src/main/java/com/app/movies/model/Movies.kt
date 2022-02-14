package com.app.movies.model

data class Movies(
    var `data`: ArrayList<MovieData> = ArrayList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)