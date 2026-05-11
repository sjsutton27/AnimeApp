package com.example.myapplication.presentation.screens.anime_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.common.Constants.PAGE_SIZE
import com.example.myapplication.data.repository.AnimeRepository
import com.example.myapplication.common.resource.Resource
import com.example.myapplication.domain.model.AnimeListItemData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.collections.*

class AnimeListViewModel(
    private val repository: AnimeRepository
): ViewModel(){
    private var curPage = 1

    private val _loadError = MutableStateFlow("")
    val loadError: StateFlow<String> = _loadError

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _endReached = MutableStateFlow(false)
    val endReached: StateFlow<Boolean> = _endReached

    private val _animeList =
        MutableStateFlow<List<AnimeListItemData>>(emptyList())

    internal val animeList: StateFlow<List<AnimeListItemData>> = _animeList

    private var _isSearchStarting = MutableStateFlow(true)
    val isSearchStarting: StateFlow<Boolean> = _isSearchStarting
    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> = _isSearching

    private var cachedAnimeList = listOf<AnimeListItemData>()


    init{
        loadAnimePaginated()
    }

    fun searchAnimeList(query: String) {

        val trimmedQuery = query.trim()

        val listToSearch = if (_isSearchStarting.value) {
            _animeList.value
        } else {
            cachedAnimeList
        }

        viewModelScope.launch(Dispatchers.Default) {

            if (trimmedQuery.isEmpty()) {
                _animeList.value = cachedAnimeList
                _isSearching.value = false
                _isSearchStarting.value = true
                return@launch
            }

            val results = listToSearch.filter { anime ->
                anime.title.contains(trimmedQuery, ignoreCase = true) ||
                        anime.id.toString() == trimmedQuery ||
                        anime.year.toString() == trimmedQuery ||
                        anime.rating.contains(trimmedQuery, ignoreCase = true)
            }

            if (_isSearchStarting.value) {
                cachedAnimeList = _animeList.value
                _isSearchStarting.value = false
            }

            _animeList.value = results
            _isSearching.value = true
        }
    }

    fun loadAnimePaginated() {

        if (_isLoading.value || endReached.value) {
            return
        }

        viewModelScope.launch {

            _isLoading.value = true

            val result = repository.getAnimeList(
                limit = PAGE_SIZE,
                page = curPage
            )

            when (result) {

                is Resource.Error -> {
                    _loadError.value = result.message ?: "Unknown error"
                    _isLoading.value = false
                    return@launch
                }

                is Resource.Loading -> {
                    // Optional
                }

                is Resource.Success -> {

                    val response = result.data
                    val animeData = response?.data ?: emptyList()

                    val newAnimeList = animeData.map { anime ->
                        AnimeListItemData(
                            id = anime.mal_id,
                            title = anime.title,
                            imageUrl = anime.images.jpg.image_url,
                            rating = anime.rating,
                            year = anime.year
                        )
                    }

                    // Check if more pages exist
                    val hasNextPage = response?.pagination?.has_next_page ?: false
                    _endReached.value = hasNextPage

                    // Move to next page
                    curPage++

                    // Append new items correctly
                    _animeList.value += newAnimeList
                    _loadError.value = ""
                }
            }

            _isLoading.value = false
        }
    }
}