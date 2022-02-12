package com.kumu.assessmentexam.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.kumu.assessmentexam.R
import com.kumu.assessmentexam.databinding.ActivityMoviePlayerBinding
import kotlinx.android.synthetic.main.activity_movie_player.*

/**
 * Created by Darryl Dave P. de Castro on 12/02/2022.
 */
class VideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoviePlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_player)

        initUI()
    }

    private fun initUI() {
        binding.vvVideo.setVideoPath(intent.getStringExtra("media"))
        binding.vvVideo.start()

        if (intent.getStringExtra("kind") == getString(R.string.movie)) {
            ivMusic.visibility = View.GONE
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}