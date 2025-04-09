package com.example.aniworld.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aniworld.data.model.Anime
import com.example.aniworld.data.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _state = mutableStateOf(AnimeListState())
    val state: State<AnimeListState> = _state
    
    private var currentPage = 1
    private var hasMorePages = true

    init {
        loadAnimeList()
    }

    fun loadAnimeList() {
        viewModelScope.launch {
            _state.value = AnimeListState(isLoading = true)
            currentPage = 1
            
            try {
                val response = repository.getTopAnime(page = 1)
                hasMorePages = response.pagination.hasNextPage
                _state.value = AnimeListState(
                    animeList = response.animeList
                )
            } catch (e: Exception) {
                _state.value = AnimeListState(
                    error = "Failed to load anime"
                )
            }
        }
    }
    
    fun loadMoreAnime() {
        if (_state.value.isLoadingMore || !hasMorePages) return
        
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoadingMore = true)
            
            try {
                val nextPage = currentPage + 1
                val response = repository.getTopAnime(page = nextPage)
                
                val currentList = _state.value.animeList.toMutableList()
                currentList.addAll(response.animeList)
                
                currentPage = nextPage
                hasMorePages = response.pagination.hasNextPage
                
                _state.value = _state.value.copy(
                    animeList = currentList,
                    isLoadingMore = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoadingMore = false
                )
            }
        }
    }
}

data class AnimeListState(
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val animeList: List<Anime> = emptyList(),
    val error: String? = null
) 