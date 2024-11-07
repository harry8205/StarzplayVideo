package com.example.startzplayassignment.ui.component

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.startzplayassignment.R
import com.example.startzplayassignment.data.model.MediaItem
import com.example.startzplayassignment.data.repository.MediaItemDiffCallback
import com.example.startzplayassignment.databinding.ItemMediaCardBinding
import com.example.startzplayassignment.databinding.ItemTvShowCardBinding
import com.squareup.picasso.Picasso

class MediaItemAdapter(private val onClick: (MediaItem) -> Unit) : ListAdapter<MediaItem, RecyclerView.ViewHolder>(MediaItemDiffCallback()) {

    companion object {
        const val VIEW_TYPE_MOVIE = 1
        const val VIEW_TYPE_TV_SHOW = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_MOVIE -> {
                val binding = ItemMediaCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MovieItemViewHolder(binding)
            }
            VIEW_TYPE_TV_SHOW -> {
                val binding = ItemTvShowCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TvShowItemViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is MovieItemViewHolder -> {
                if (item is MediaItem.Movie) {
                    holder.bind(item, onClick)
                } else {
                    Log.e("MediaItemAdapter", "Item at position $position is not a Movie!")
                }
            }
            is TvShowItemViewHolder -> {
                if (item is MediaItem.TVShow) {
                    holder.bind(item, onClick)
                } else {
                    Log.e("MediaItemAdapter", "Item at position $position is not a TV Show!")
                }
            }
            else -> Log.e("MediaItemAdapter", "Unknown ViewHolder type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)

        return when (item) {
            is MediaItem.Movie -> VIEW_TYPE_MOVIE
            is MediaItem.TVShow -> VIEW_TYPE_TV_SHOW
            else -> throw IllegalArgumentException("Unknown media type: ${item}")
        }
    }
    class MovieItemViewHolder(private val binding: ItemMediaCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MediaItem.Movie, onClick: (MediaItem) -> Unit) {
            binding.mediaTitle.text = item.title

            val imageUrl = if (!item.posterPath.isNullOrEmpty()) {
                "https://image.tmdb.org/t/p/w500" + item.posterPath
            } else {
                "https://via.placeholder.com/500x750.png?text=No+Image"
            }

            Picasso.get().load(imageUrl).into(binding.mediaImage)

            Log.d("TAG", "imagePath2: ${item.posterPath}")
            binding.root.setOnClickListener {
                onClick(item)  // Trigger onClick with the selected media item
            }
        }
    }




    class TvShowItemViewHolder(private val binding: ItemTvShowCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MediaItem.TVShow, onClick: (MediaItem) -> Unit) {
            binding.mediaTitle.text = item.name
//            Glide.with(binding.root.context)
//                .load("https://image.tmdb.org/t/p/w500" + item.imageUrl)
//                .placeholder(R.drawable.poster_placeholder)
//                .error(R.drawable.poster_placeholder)
//                .into(binding.mediaImage)
            Picasso.get().load("https://image.tmdb.org/t/p/w500" + item.posterPath).into(binding.mediaImage);
            Log.d("TAG", "imagePath: ${item.posterPath}")
            binding.root.setOnClickListener {
                onClick(item)  // Trigger onClick with the selected media item
            }
        }
    }
}

