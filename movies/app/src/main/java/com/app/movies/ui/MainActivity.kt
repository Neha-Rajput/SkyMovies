package com.app.movies.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.movies.R
import com.app.movies.databinding.ActivityMainBinding
import com.app.movies.model.MovieData
import com.app.movies.model.Movies
import com.app.movies.network.APIService
import com.app.movies.network.Network
import com.app.movies.ui.adapter.ItemAdapter
import com.app.movies.ui.viewModel.MainActivityViewModel
import com.app.movies.util.NetworkConnectivity
import io.reactivex.Observable

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainActivityViewModel
    private  val adapter : ItemAdapter = ItemAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.recyclerView.adapter = adapter
        val factory = MovieActivityViewModel(Network.getApiService(this))
        viewModel = ViewModelProvider(this, factory).get(MainActivityViewModel::class.java)



        binding.searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String): Boolean {
                if (newText.isNotEmpty() && newText.count() > 2) {

                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isNotEmpty() == true && newText.count() > 2) {
                    viewModel.setQueryListener(Observable.just(newText))
                    viewModel.fetchFilterList().observe(this@MainActivity, {
                        if (it.data.isNotEmpty()) {
                            setMovieAdapter(it.data)
                            binding.searchText.hideKeyboard()
                        } else {
                            ShowNoDataFound()

                        }
                    })
                }
                return true
            }
        })

      //
  /*      binding.searchText.setOnCloseListener {
            binding.tvNoData.visibility = View.GONE
            binding.searchText.onActionViewCollapsed()
            binding.searchText.clearFocus()
           // showMovies()
            true
        }*/

        binding.searchText.setOnCloseListener(SearchView.OnCloseListener {
           // binding.searchText.onActionViewCollapsed()
            binding.searchText.hideKeyboard()
            observeViewModel()
            true
        })
    }

    private fun ShowNoDataFound() {
        binding.recyclerView.visibility = View.GONE
        binding.tvNoData.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        // println("In onResume().........")
        if (NetworkConnectivity.hasNetwork(applicationContext) == true) {
            viewModel.makeApiCall()
        } else {
            Toast.makeText(this@MainActivity, "No Internet Connection", Toast.LENGTH_SHORT).show()
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loading.observe(this, { t ->
            if (t == false) {
                binding.progressBar.visibility = View.GONE
            } else if (t == true) {
                binding.progressBar.visibility = View.VISIBLE
            }
        })

        viewModel.postLoadError.observe(this, { t ->
            if (!t.isNullOrEmpty()) {
                Toast.makeText(this, "Error in API call $t", Toast.LENGTH_LONG).show()
            }
        })

       // showMovies()

        viewModel.fetchMovieListObserver().observe(this, {
            setMovieAdapter(it.data)
        })
    }

    /*private fun showMovies() {
        binding.tvNoData.visibility = View.GONE

        viewModel.fetchMovieListObserver().observe(this, {
            setMovieAdapter(it.data)
        })
    }*/

    fun setMovieAdapter(movieData: ArrayList<MovieData>) {
        binding.recyclerView.visibility = View.VISIBLE
        binding.tvNoData.visibility = View.GONE
        adapter.submitList(movieData)
        adapter.notifyDataSetChanged()
    }

    fun View.hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    // this is for passing the constructor parameter into the ViewModel
    class MovieActivityViewModel(private val apiService: APIService) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
                return MainActivityViewModel(apiService) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
