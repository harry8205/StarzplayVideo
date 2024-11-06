package com.example.startzplayassignment.ui.component


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.startzplayassignment.data.model.MediaItem
import com.example.startzplayassignment.databinding.ItemMediaCardBinding

class MediaItemAdapter : ListAdapter<MediaItem, MediaItemAdapter.MediaItemViewHolder>(MediaItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaItemViewHolder {
        val binding = ItemMediaCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MediaItemViewHolder(private val binding: ItemMediaCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MediaItem) {
            // Bind data to the view, e.g., item title, image, etc.
            binding.mediaTitle.text = when (item) {
                is MediaItem.Movie -> item.title
                is MediaItem.TVShow -> item.name
            }
            // Load image (e.g., with Glide or Picasso)
        }
    }
}

class MediaItemDiffCallback : DiffUtil.ItemCallback<MediaItem>() {
    override fun areItemsTheSame(oldItem: MediaItem, newItem: MediaItem): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: MediaItem, newItem: MediaItem): Boolean = oldItem == newItem
}
