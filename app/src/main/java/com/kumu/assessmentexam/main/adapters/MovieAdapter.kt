package com.kumu.assessmentexam.main.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kumu.assessmentexam.R
import com.kumu.assessmentexam.data.model.Media
import com.kumu.assessmentexam.databinding.ItemMediaBinding
import com.squareup.picasso.Picasso


/**
 * Created by Darryl Dave P. de Castro on 12/02/2022.
 */
class MovieAdapter : ListAdapter<Media, MovieAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Media>() {
        override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean =
            oldItem.trackId == newItem.trackId

        override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean =
            oldItem == newItem
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_media,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val item = currentList[holder.absoluteAdapterPosition]
            holder.binding.media = item

            holder.binding.tvPrice.text = "$" + item.trackPrice.toString()

            Picasso.get()
                .load(item.artwork)
                .into(holder.binding.ivArtwork)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    class ViewHolder(val binding: ItemMediaBinding) : RecyclerView.ViewHolder(binding.root)
}