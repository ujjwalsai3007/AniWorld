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
    
    private val _searchState = mutableStateOf(SearchState())
    val searchState: State<SearchState> = _searchState
    
    private var currentPage = 1
    private var hasMorePages = true
    
    private var searchPage = 1
    private var hasMoreSearchPages = true
    private var currentSearchQuery = ""

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
    
    fun searchAnime(query: String) {
        if (query.isEmpty()) return
        
        viewModelScope.launch {
            _searchState.value = SearchState(isLoading = true)
            searchPage = 1
            currentSearchQuery = query
            
            try {
                val response = repository.searchAnime(query, page = 1)
                hasMoreSearchPages = response.pagination.hasNextPage
                _searchState.value = SearchState(
                    searchResults = response.animeList,
                    query = query
                )
            } catch (e: Exception) {
                _searchState.value = SearchState(
                    error = "Failed to search anime: ${e.message}",
                    query = query
                )
            }
        }
    }
    
    fun loadMoreSearchResults() {
        if (_searchState.value.isLoadingMore || !hasMoreSearchPages || currentSearchQuery.isEmpty()) return
        
        viewModelScope.launch {
            _searchState.value = _searchState.value.copy(isLoadingMore = true)
            
            try {
                val nextPage = searchPage + 1
                val response = repository.searchAnime(currentSearchQuery, page = nextPage)
                
                val currentList = _searchState.value.searchResults.toMutableList()
                currentList.addAll(response.animeList)
                
                searchPage = nextPage
                hasMoreSearchPages = response.pagination.hasNextPage
                
                _searchState.value = _searchState.value.copy(
                    searchResults = currentList,
                    isLoadingMore = false
                )
            } catch (e: Exception) {
                _searchState.value = _searchState.value.copy(
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

data class SearchState(
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val searchResults: List<Anime> = emptyList(),
    val query: String = "",
    val error: String? = null
) 