package com.app.movies.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.movies.R
import com.app.movies.databinding.ActivityMainBinding
import com.app.movies.ui.adapter.ItemAdapter
import com.app.movies.ui.viewModel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainActivityViewModel
    private val adapter = ItemAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.recyclerView.adapter = adapter
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.makeApiCall()

        binding.searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String): Boolean {
                if (newText.isNotEmpty() && newText.count() > 2) {
                    viewModel.filterMovies(newText)
                    adapter.notifyDataSetChanged()
                    viewModel.fetchFilterList().observe(this@MainActivity, {
                        if (it != null) {
                            adapter.submitList(it.data)
                            adapter.notifyDataSetChanged()
                            binding.searchText.hideKeyboard()
                        } else {
                            Toast.makeText(this@MainActivity, "No match found", Toast.LENGTH_SHORT)
                                .show()
                        }
                    })
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isNotEmpty() == true && newText.count() > 2) {

                }
                return false
            }
        })

        viewModel.fetchMovieListObserver().observe(this, {
            if (it != null) {
                with(it) {
                    binding.progressBar.isVisible = isLoading
                    adapter.submitList(it.data)
                    if (errorMessage != null) {
                        Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    fun View.hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}