package com.example.aniworld.data.api

import com.example.aniworld.data.model.AnimeDetailResponse
import com.example.aniworld.data.model.AnimeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApiService {
    @GET("v4/top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 15 // Reduced for better API reliability
    ): AnimeResponse
    
    @GET("v4/anime/{id}")
    suspend fun getAnimeDetails(
        @Path("id") id: Int
    ): AnimeDetailResponse
    
    @GET("v4/anime")
    suspend fun searchAnime(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 15
    ): AnimeResponse
} 