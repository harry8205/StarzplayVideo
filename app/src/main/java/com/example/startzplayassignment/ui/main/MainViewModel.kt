package com.example.startzplayassignment.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.startzplayassignment.data.model.MediaItem
import com.example.startzplayassignment.data.model.GetSearchData
import com.example.startzplayassignment.data.network.RemoteDataSource
import kotlinx.coroutines.launch

class MainViewModel(private val remoteDataSource: RemoteDataSource) : ViewModel() {

    private val _mediaItems = MutableLiveData<Map<String, List<MediaItem>>>()
    val mediaItems: LiveData<Map<String, List<MediaItem>>> = _mediaItems

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun searchMedia(query: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = remoteDataSource.searchMulti(query)
                Log.d("TAG", "API Response: ${response.body()}")
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        _mediaItems.value = processSearchData(data)
                    }
                } else {
                    _error.value = "Error: ${response.message()}"
                    Log.i("TAG", "searchMedia: ${response.message()} ")
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.localizedMessage}"
                Log.e("TAG", "Exception occurred: ${e.message}", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun processSearchData(data: GetSearchData): Map<String, List<MediaItem>> {
        val groupedItems = data.results
            .filter { it.mediaType != null || it.name != null }
            .groupBy { it.mediaType ?: "Unknown" }

        Log.d("TAG", "Grouped items: $groupedItems")

        return groupedItems.mapValues { entry ->
            entry.value.map { result ->

                when (result.mediaType) {
                    "movie" -> MediaItem.Movie(
                        id = result.id,
                        title = result.name ?: result.originalName ?: "",
                        overview = result.overview ?: "No overview available",
                        posterPath = result.posterPath?:"",
                        voteAverage = result.voteAverage
                    )
                    "tv" -> MediaItem.TVShow(
                        id = result.id,
                        name = result.name ?: result.originalName ?: "",

                        overview = result.overview ?: "No overview available",
                        posterPath = result.posterPath?: "",
                        voteAverage = result.voteAverage
                    )
                    else -> MediaItem.Movie(
                        id = result.id,
                        title = result.name ?: result.originalName ?: "Untitled",

                        overview = result.overview ?: "No overview available",
                        posterPath = result.posterPath?: "",
                        voteAverage = result.voteAverage
                    )
                }
            }
        }
    }
}
