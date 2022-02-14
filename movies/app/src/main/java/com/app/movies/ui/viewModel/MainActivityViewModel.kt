package com.app.movies.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.movies.model.MovieData
import com.app.movies.model.Movies
import com.app.movies.network.APIService
import com.app.movies.network.APIConnector
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
        val retroInstance = APIConnector.getRetroInstance().create(APIService::class.java)
        retroInstance.fetchMovies().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(getMovieList())
    }

    fun filterMovies(value: String) {
        val filterMovieList: ArrayList<MovieData> = ArrayList()
        for (it in movies.value?.data!!) {
            if (it.genre.contains(value, true) || it.title.contains(value, true)) {
                filterMovieList.add((it))
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