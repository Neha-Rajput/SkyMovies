package com.app.movies.ui.viewModel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.app.movies.model.MovieData
import com.app.movies.model.Movies
import com.app.movies.network.APIService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel(val apiService: APIService) : ViewModel() {
    var movies: MutableLiveData<Movies> = MutableLiveData()
    var finalFilter: MutableLiveData<Movies> = MutableLiveData()
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    var postLoadError: MutableLiveData<String> = MutableLiveData()
    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        loading.value = true
    }

    fun fetchMovieListObserver(): MutableLiveData<Movies> {
        return movies
    }

    fun fetchFilterList(): MutableLiveData<Movies> {
        return finalFilter
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    internal fun makeApiCall() {
        compositeDisposable.add(
            apiService.fetchMovies().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(getMovieList())
        )
    }

    fun getMovieList(): DisposableObserver<Movies> {
        return object : DisposableObserver<Movies>() {
            override fun onNext(t: Movies) {
                movies.postValue(t)
                loading.postValue(false)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                postLoadError.value = e.message.toString()
                loading.postValue(false)
            }

            override fun onComplete() {
                loading.postValue(false)
            }
        }
    }

    fun setQueryListener(queryObservable: Observable<String>) {
        queryObservable.observeOn(AndroidSchedulers.mainThread()).map(::filterMovies) //2
            .subscribe()
    }

    private fun filterMovies(value: String) {
        val filterMovieList: ArrayList<MovieData> = ArrayList()
        for (movieData in movies.value?.data!!) {
            if (movieData.genre.contains(value, true) || movieData.title.contains(value, true)) {
                filterMovieList.add((movieData))
            }
            finalFilter.postValue(Movies(filterMovieList))
        }
    }

        override fun onCleared() {
            super.onCleared()
            compositeDisposable.clear()
        }
}