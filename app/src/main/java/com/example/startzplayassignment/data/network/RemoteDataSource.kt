package com.example.startzplayassignment.data.network

import com.example.startzplayassignment.data.model.GetSearchData
import retrofit2.Response

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun searchMulti(query: String): Response<GetSearchData> {
        return apiService.searchMulti(query)
    }
}
