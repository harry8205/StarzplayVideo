package com.example.startzplayassignment.data.repository

import com.example.startzplayassignment.data.model.GetSearchData
import com.example.startzplayassignment.data.network.RemoteDataSource
import retrofit2.Response

class MediaRepository(private val remoteDataSource: RemoteDataSource) {
    suspend fun searchMedia(query: String): Response<GetSearchData> {
        return remoteDataSource.searchMulti(query)
    }
}
