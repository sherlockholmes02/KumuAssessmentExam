package com.kumu.assessmentexam.main.adapters

import android.view.LayoutInflater
import android.view.View
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
class MediaAdapter : ListAdapter<Media, MediaAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Media>() {
        override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean =
            oldItem.trackId == newItem.trackId

        override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean =
            oldItem == newItem
    }
) {

    private var onItemClickListener: (Media) -> Unit = { }

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

            //Handle in case of null trackName eg. audiobooks kind
            if (item.trackName != null) {
                holder.binding.tvTrackName.text = item.trackName
            } else {
                holder.binding.tvTrackName.text = item.collectionName
            }

            //Handle in case of null trackPrice eg. audiobooks
            holder.binding.tvPrice.text = "$" + item.trackPrice.toString()
            if (item.trackPrice != null) {
                holder.binding.tvPrice.text = "$" + item.trackPrice.toString()
            } else {
                holder.binding.tvPrice.text = "$" + item.collectionPrice.toString()
            }

            //Last Visited handler
            if (item.lastVisited != null) {
                holder.binding.tvLastVisited.visibility = View.VISIBLE
                holder.binding.tvLastVisited.text = "Last visited: " + item.lastVisited
            } else {
                holder.binding.tvLastVisited.visibility = View.GONE
            }

            holder.binding.root.setOnClickListener {
                onItemClickListener.invoke(item)
            }

            Picasso.get()
                .load(item.artwork)
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(holder.binding.ivArtwork)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setOnItemClickListener(onItemClickListener: (Media) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    class ViewHolder(val binding: ItemMediaBinding) : RecyclerView.ViewHolder(binding.root)
}