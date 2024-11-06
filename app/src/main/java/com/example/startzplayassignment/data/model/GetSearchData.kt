package com.example.startzplayassignment.data.model

data class GetSearchData(
    val page: Int,
    val totalPages: Int,
    val totalResults: Int,
    val results: List<ResultsBean>
){
    data class ResultsBean(
        val backdropPath: String?,
        val id: Int,
        val name: String?,
        val originalName: String?,
        val overview: String?,
        val posterPath: String?,
        val mediaType: String?,
        val adult: Boolean,
        val originalLanguage: String?,
        val popularity: Double,
        val firstAirDate: String?,
        val voteAverage: Double,
        val voteCount: Int,
        val genreIds: List<Int>?,
        val originCountry: List<String>?
    )
}