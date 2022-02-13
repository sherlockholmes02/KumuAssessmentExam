package com.kumu.assessmentexam.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.kumu.assessmentexam.R
import com.kumu.assessmentexam.data.MediaDatabase
import com.kumu.assessmentexam.data.model.Media
import com.kumu.assessmentexam.databinding.ActivityMainBinding
import com.kumu.assessmentexam.details.MediaDetailsActivity
import com.kumu.assessmentexam.main.adapters.MediaAdapter
import com.kumu.assessmentexam.network.ApiInterface
import com.kumu.assessmentexam.utils.Coroutines
import com.kumu.assessmentexam.utils.NetworkUtil
import com.kumu.assessmentexam.utils.RetrofitSingleton

class MainActivity : AppCompatActivity(), MainInterface {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mediaAdapter: MediaAdapter
    private var medias = mutableListOf<Media>()

    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val mediaDatabase = MediaDatabase.getInstance()
        var apiInterface = RetrofitSingleton.get<ApiInterface>()
        val profileRepository = MainRepository(apiInterface, mediaDatabase)
        val factory = MainViewModelFactory(profileRepository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        viewModel.mainListener = this

        initUI()

        Coroutines.inputOutput {
            getMediaListFromDb()
        }
    }

    private fun initUI() {
        mediaAdapter = MediaAdapter().apply {
            setOnItemClickListener {
                val gson = Gson()
                val intent = Intent(this@MainActivity, MediaDetailsActivity::class.java)
                intent.putExtra("media", gson.toJson(it))
                startActivity(intent)
            }
        }

        binding.srlRefresh.setOnRefreshListener {
            binding.srlRefresh.isRefreshing = false
            checkInternetConnection()
        }
    }

    private suspend fun getMediaListFromDb() {
        Coroutines.main {
            val medias = viewModel.medias.await()
            medias.observe(this, {
                if (it.isEmpty()) {
                    checkInternetConnection()
                } else {
                    this.medias.clear()
                    this.medias.addAll(it)
                    initRecyclerview()
                }
            })
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
        this.medias.clear()
        this.medias.addAll(media)
        initRecyclerview()
    }

    private fun initRecyclerview() {
        Coroutines.main {
            binding.rvMedias.apply {
                layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = mediaAdapter
            }
            mediaAdapter.submitList(medias)
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