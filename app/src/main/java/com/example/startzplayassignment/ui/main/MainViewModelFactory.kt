package com.example.startzplayassignment.ui.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.startzplayassignment.data.network.RemoteDataSource

class MainViewModelFactory(private val remoteDataSource: RemoteDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(remoteDataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
