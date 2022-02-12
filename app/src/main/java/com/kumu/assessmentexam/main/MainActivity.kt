package com.kumu.assessmentexam.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kumu.assessmentexam.R
import com.kumu.assessmentexam.data.model.Media
import com.kumu.assessmentexam.databinding.ActivityMainBinding
import com.kumu.assessmentexam.main.adapters.MovieAdapter
import com.kumu.assessmentexam.network.ApiInterface
import com.kumu.assessmentexam.utils.Coroutines
import com.kumu.assessmentexam.utils.NetworkUtil
import com.kumu.assessmentexam.utils.RetrofitSingleton

class MainActivity : AppCompatActivity(), MainInterface {

    private lateinit var binding: ActivityMainBinding
    private val movieAdapter = MovieAdapter()
    private var movies = mutableListOf<Media>()

    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        var apiInterface = RetrofitSingleton.get<ApiInterface>()
        val profileRepository = MainRepository(apiInterface)
        val factory = MainViewModelFactory(profileRepository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        viewModel.mainListener = this

        setListeners()
        checkInternetConnection()
    }

    private fun setListeners() {
        binding.srlRefresh.setOnRefreshListener {
            binding.srlRefresh.isRefreshing = false
            checkInternetConnection()
        }
    }

    private fun checkInternetConnection() {
        if (NetworkUtil.isNetworkAvailable(this)) {
            viewModel.getMedias()
        } else {
            Toast.makeText(
                this,
                "Please check your internet connection",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onSuccessfulFetch(media: List<Media>) {
        Coroutines.main {
            binding.rvMedias.apply {
                layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
            this.movies.clear()
            this.movies.addAll(media)
            movieAdapter.submitList(media)
        }
    }

    override fun onFailureFetch(message: String) {
        TODO("Not yet implemented")
    }

    override fun showProgressBar() {
        Coroutines.main {
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    override fun hideProgressBar() {
        Coroutines.main {
            binding.progressBar.visibility = View.GONE
        }
    }
}