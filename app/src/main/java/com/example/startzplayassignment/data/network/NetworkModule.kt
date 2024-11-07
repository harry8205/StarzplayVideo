package com.example.startzplayassignment.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object NetworkModule {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkOGY4OWNjYjQ3YTFmOGMzNjMzYzY5NjY0MWQyNDlhMyIsIm5iZiI6MTczMDM3MzcxMC45MTI3Mzc0LCJzdWIiOiI2MzM5NmU4ODE3NTA1MTAwODRjNDI4MzYiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.thKes3P6guzTLERGiysFPgkSBNLuRZaauQyChoQKoIA" // Replace with your API key


    private fun provideOkHttpClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $API_KEY")
                        .build()
                    chain.proceed(newRequest)
                }
                .build()
        }

        fun provideApiService(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
