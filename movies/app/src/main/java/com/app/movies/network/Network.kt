package com.app.movies.network

import android.content.Context
import android.util.Log
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object Network {
    private const val BASE_URL = "https://movies-sample.herokuapp.com/api/"

    fun getApiService(context: Context): APIService {

        val cache = createCache(context)
        val networkCacheInterceptor = createCacheInterceptor()

        val httpClient =
            OkHttpClient.Builder().cache(cache).addNetworkInterceptor(networkCacheInterceptor)
                .build()

        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(httpClient)
                .build()

        return retrofit.create(APIService::class.java)
    }


    private fun createCache(context: Context): Cache? {
        var cache: Cache? = null
        try {
            val cacheSize = 2048// 10 MB
            val httpCacheDirectory = File(context.getCacheDir(), "http-cache")
            cache = Cache(httpCacheDirectory, cacheSize.toLong())
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("error", "Failed to create create Cache!")
        }
        return cache
    }


    private fun createCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())

            var cacheControl = CacheControl.Builder().maxAge(1, TimeUnit.MINUTES).build()
            response.newBuilder().header("Cache-Control", cacheControl.toString()).build()
        }
    }

}