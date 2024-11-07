package com.example.startzplayassignment.data.model

// MediaItem.kt
sealed class MediaItem {
    data class Movie(
        val id: Int,
        val title: String,
        override val overview: String,
        override val posterPath: String,
        val voteAverage: Double
    ) : MediaItem() {
        override val displayTitle: String get() = title
    }

    data class TVShow(
        val id: Int,
        val name: String,
        override val overview: String,
        override val posterPath: String,
        val voteAverage: Double
    ) : MediaItem() {
        override val displayTitle: String get() = name
    }

    abstract val displayTitle: String
    abstract val overview: String
    abstract val posterPath: String
}
