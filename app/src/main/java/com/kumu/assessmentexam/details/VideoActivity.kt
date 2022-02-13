package com.kumu.assessmentexam.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.kumu.assessmentexam.R
import com.kumu.assessmentexam.data.model.Media
import com.kumu.assessmentexam.databinding.ActivityMoviePlayerBinding
import com.kumu.assessmentexam.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_movie_player.*

/**
 * Created by Darryl Dave P. de Castro on 12/02/2022.
 */
class VideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoviePlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_player)

        AppPreferences.init(this)
        initUI()
    }

    override fun onResume() {
        super.onResume()
        AppPreferences.screen = 3
    }

    private fun initUI() {
        val gson = Gson()
        var intentMedia = intent.getStringExtra("media")
        val media = if (intentMedia != null) {
            gson.fromJson(intentMedia, Media::class.java)
        } else {
            gson.fromJson(AppPreferences.media, Media::class.java)
        }
        binding.vvVideo.setVideoPath(media.previewUrl)
        binding.vvVideo.start()

        if (media.kind == getString(R.string.movie)) {
            ivMusic.visibility = View.GONE
        }

        binding.ivBack.setOnClickListener {
            if (intentMedia == null) {
                AppPreferences.screen = 1
            }
            finish()
        }
    }
}