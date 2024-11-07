package com.example.startzplayassignment.ui.detail


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.startzplayassignment.data.model.MediaItem

class SharedViewModel : ViewModel() {
    private val _selectedMediaItem = MutableLiveData<MediaItem>()
    val selectedMediaItem: LiveData<MediaItem> get() = _selectedMediaItem

    fun selectMediaItem(item: MediaItem) {
        _selectedMediaItem.value = item
    }
}