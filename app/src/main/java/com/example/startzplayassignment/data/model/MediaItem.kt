package com.example.startzplayassignment.data.model

sealed class MediaItem {
    data class Movie(
        val id: Int,
        val title: String,
        val overview: String,
        val posterPath: String?,
        val voteAverage: Double
    ) : MediaItem()

    data class TVShow(
        val id: Int,
        val name: String,
        val overview: String,
        val posterPath: String?,
        val voteAverage: Double
    ) : MediaItem()
}
