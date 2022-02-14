package com.app.movies.data


data class Movies(
    var `data`: ArrayList<Data> = ArrayList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)