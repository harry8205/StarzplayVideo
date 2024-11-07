package com.example.startzplayassignment.data.repository

import androidx.recyclerview.widget.DiffUtil
import com.example.startzplayassignment.data.model.MediaItem

class MediaItemDiffCallback : DiffUtil.ItemCallback<MediaItem>() {

    // Check if two items are the same (same identity)
    override fun areItemsTheSame(oldItem: MediaItem, newItem: MediaItem): Boolean {
        // Compare by unique ID
        return when {
            oldItem is MediaItem.Movie && newItem is MediaItem.Movie -> oldItem.id == newItem.id
            oldItem is MediaItem.TVShow && newItem is MediaItem.TVShow -> oldItem.id == newItem.id
            else -> false
        }
    }

    // Check if the content of the two items is the same (same data)
    override fun areContentsTheSame(oldItem: MediaItem, newItem: MediaItem): Boolean {
        return oldItem == newItem
    }
}

