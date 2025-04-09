package com.example.aniworld.data.api

import com.example.aniworld.data.model.AnimeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AnimeApiService {
    @GET("v4/top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 15 // Reduced for better API reliability
    ): AnimeResponse
} 