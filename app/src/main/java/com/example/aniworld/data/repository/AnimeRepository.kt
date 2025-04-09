package com.example.aniworld.data.repository

import com.example.aniworld.data.api.AnimeApiService
import com.example.aniworld.data.model.AnimeDetailResponse
import com.example.aniworld.data.model.AnimeResponse
import javax.inject.Inject

class AnimeRepository @Inject constructor(
    private val apiService: AnimeApiService
) {
    suspend fun getTopAnime(page: Int = 1, limit: Int = 15): AnimeResponse {
        return apiService.getTopAnime(page, limit)
    }
    
    suspend fun getAnimeDetails(id: Int): AnimeDetailResponse {
        return apiService.getAnimeDetails(id)
    }
} 