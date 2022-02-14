package com.app.movies.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.movies.data.Data
import com.app.movies.data.Movies
import com.app.movies.network.APIService
import com.app.movies.network.RetroInstance
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel : ViewModel() {
    var movies: MutableLiveData<Movies> = MutableLiveData()
    var finalFilter: MutableLiveData<Movies> = MutableLiveData()

    fun fetchMovieListObserver(): MutableLiveData<Movies> {
        return movies
    }

    fun fetchFilterList(): MutableLiveData<Movies> {
        return finalFilter
    }


    fun makeApiCall() {
        val retroInstance = RetroInstance.getRetroInstance().create(APIService::class.java)
        retroInstance.fetchMovies().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(getMovieList())
    }

    fun filterMovies(value: String) {
        println("111111")
        println(value)
        var filterMovieList: ArrayList<Data> = ArrayList()
        for (it in movies.value?.data!!) {
            if (it.genre.contains(value, true) || it.title.contains(value, true)) {
                println("------------")
                println(value)
                println("------------")
                println(value)
                filterMovieList.add((it))
                println(filterMovieList)

                // movies.value!!.data = arrayListOf(it)
            }
        }
        finalFilter.postValue(Movies(filterMovieList))
    }

    private fun getMovieList(): Observer<Movies> {
        return object : Observer<Movies> {
            override fun onComplete() {
                //hide progress indicator .
                Movies(isLoading = false)
            }

            override fun onError(e: Throwable) {
                Movies(isLoading = false, errorMessage = e.toString())
            }

            override fun onNext(it: Movies) {
                movies.postValue(it)
            }

            override fun onSubscribe(d: Disposable) {
                //start showing progress indicator.
                Movies(isLoading = true)
            }
        }
    }
}