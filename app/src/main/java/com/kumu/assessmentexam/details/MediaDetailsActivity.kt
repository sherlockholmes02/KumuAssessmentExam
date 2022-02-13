package com.kumu.assessmentexam.details

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.kumu.assessmentexam.R
import com.kumu.assessmentexam.data.model.Media
import com.kumu.assessmentexam.databinding.ActivityMediaDetailsBinding
import com.kumu.assessmentexam.utils.NetworkUtil
import com.squareup.picasso.Picasso

/**
 * Created by Darryl Dave P. de Castro on 12/02/2022.
 */
class MediaDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediaDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_media_details)

        initUI()
    }

    private fun initUI() {
        val gson = Gson()
        val media = gson.fromJson(intent.getStringExtra("media"), Media::class.java)
        binding.media = media

        if (media.kind == getString(R.string.movie)) {
            binding.tvPrice.visibility = View.GONE

            binding.tvTrackName.text = media.trackName
            binding.clPrices.visibility = View.VISIBLE
            binding.tvTrackPrice.text = "$ " + media.trackPrice.toString()
            binding.tvTrackRentalPrice.text = "$ " + media.trackRentalPrice.toString()
            binding.tvTrackHdPrice.text = "$ " + media.trackHdPrice.toString()
            binding.tvTrackHdRentalPrice.text = "$ " + media.trackHdRentalPrice.toString()

            binding.tvAbout.text = "About this Movie"
            binding.tvAbout.visibility = View.VISIBLE
            binding.tvLongDescription.visibility = View.VISIBLE
            binding.tvLongDescription.text = media.longDescription
        } else if (media.kind == getString(R.string.music)) {
            binding.tvTrackName.text = media.trackName
            binding.tvPrice.visibility = View.VISIBLE
            binding.tvPrice.text = "$ " + media.trackPrice.toString()
            binding.clPrices.visibility = View.GONE
            binding.tvAbout.visibility = View.GONE
            binding.tvLongDescription.visibility = View.GONE
            binding.tvLongDescription.text = media.longDescription
        } else {
            binding.tvPrice.visibility = View.GONE
            binding.clPrices.visibility = View.GONE
            binding.tvAbout.visibility = View.VISIBLE
            binding.tvAbout.text = "About this Audio Book"
            binding.tvLongDescription.visibility = View.VISIBLE
            binding.tvTrackName.text = media.collectionName

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.tvLongDescription.text =
                    Html.fromHtml(
                        media.description,
                        Html.FROM_HTML_MODE_COMPACT
                    );
            } else {
                binding.tvLongDescription.text =
                    Html.fromHtml(media.description)
            }
        }

        if (media.trackTimeMillis != null) {
            binding.tvRuntime.text = convertMillisToRuntime(media.trackTimeMillis!!)
        }

        Picasso.get()
            .load(media.artwork)
            .placeholder(R.drawable.logo)
            .error(R.drawable.logo)
            .into(binding.ivArtwork)

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnPlay.setOnClickListener {
            if (NetworkUtil.isNetworkAvailable(this)) {
                val intent = Intent(this@MediaDetailsActivity, VideoActivity::class.java)
                intent.putExtra("media", media.previewUrl)
                intent.putExtra("kind", media.kind)
                startActivity(intent)
            } else {
                Toast.makeText(
                    this,
                    "Please check your internet connection",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun convertMillisToRuntime(millis: Long): String {
        val m = millis / 60 % 60
        val h = millis / (60 * 60) % 24
        return String.format("%dh %02dm", h, m)
    }
}