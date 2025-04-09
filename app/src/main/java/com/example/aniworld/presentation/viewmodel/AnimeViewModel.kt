package com.example.aniworld.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aniworld.data.model.Anime
import com.example.aniworld.data.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _animeList = MutableStateFlow<List<Anime>>(emptyList())
    val animeList: StateFlow<List<Anime>> = _animeList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()
    
    private val _hasMorePages = MutableStateFlow(true)
    val hasMorePages: StateFlow<Boolean> = _hasMorePages.asStateFlow()
    
    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore.asStateFlow()

    init {
        loadAnimeList()
    }

    fun loadAnimeList() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _currentPage.value = 1
            
            try {
                val response = repository.getTopAnime(page = 1)
                _animeList.value = response.animeList
                _hasMorePages.value = response.pagination.hasNextPage
                if (response.animeList.isEmpty()) {
                    _error.value = "No anime found. Please try again later."
                }
            } catch (e: HttpException) {
                _error.value = "API error: " + (if (e.code() == 400) "Service temporarily unavailable" else e.message())
            } catch (e: IOException) {
                _error.value = "Network error. Please check your connection."
            } catch (e: Exception) {
                _error.value = "Failed to load anime: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun loadMoreAnime() {
        if (_isLoadingMore.value || !_hasMorePages.value) return
        
        viewModelScope.launch {
            _isLoadingMore.value = true
            
            try {
                val nextPage = _currentPage.value + 1
                val response = repository.getTopAnime(page = nextPage)
                
                val currentList = _animeList.value.toMutableList()
                currentList.addAll(response.animeList)
                _animeList.value = currentList
                _currentPage.value = nextPage
                _hasMorePages.value = response.pagination.hasNextPage
            } catch (e: Exception) {
                // Silent failure for pagination - just don't load more
            } finally {
                _isLoadingMore.value = false
            }
        }
    }
} 