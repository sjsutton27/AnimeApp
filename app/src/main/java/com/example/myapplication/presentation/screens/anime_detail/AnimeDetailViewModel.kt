package com.example.myapplication.presentation.screens.anime_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.common.resource.Resource
import com.example.myapplication.data.repository.AnimeRepository
import com.example.myapplication.presentation.states.AnimeDetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AnimeDetailViewModel(
    private val repository: AnimeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AnimeDetailState())
    val state: StateFlow<AnimeDetailState> = _state

    fun getAnimeInfo(id: Int) {
        viewModelScope.launch {
            _state.value = AnimeDetailState(isLoading = true)
            when (val result = repository.getAnimeInfo(id = id)) {

                is Resource.Success -> {
                    _state.value = AnimeDetailState(
                        anime = result.data,
                        title = result.data?.data?.title
                    )
                }

                is Resource.Error -> {
                    _state.value = AnimeDetailState(
                        error = result.message ?: "Unknown error"
                    )
                }

                is Resource.Loading -> {
                    _state.value = AnimeDetailState(isLoading = true)
                }
            }
        }
    }
}

