package com.app.movies

import android.app.Application
import android.content.Context

class MovieApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: MovieApplication? = null
        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        // initialize for any

        // Use ApplicationContext.
        // example: SharedPreferences etc...
        val context: Context = MovieApplication.applicationContext()
    }
}