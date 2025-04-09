package com.example.aniworld.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aniworld.data.model.AnimeDetail
import com.example.aniworld.data.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    private val repository: AnimeRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(AnimeDetailState())
    val state: State<AnimeDetailState> = _state
    
    init {
        savedStateHandle.get<Int>("animeId")?.let { animeId ->
            loadAnimeDetails(animeId)
        }
    }
    
    fun loadAnimeDetails(id: Int) {
        viewModelScope.launch {
            _state.value = AnimeDetailState(isLoading = true)
            
            try {
                val response = repository.getAnimeDetails(id)
                _state.value = AnimeDetailState(
                    animeDetail = response.anime
                )
            } catch (e: Exception) {
                _state.value = AnimeDetailState(
                    error = "Failed to load anime details"
                )
            }
        }
    }
}

data class AnimeDetailState(
    val isLoading: Boolean = false,
    val animeDetail: AnimeDetail? = null,
    val error: String? = null
) 