package com.example.myapplication.presentation.states

import com.example.myapplication.data.remote.responses.Anime

data class AnimeDetailState(
    val isLoading: Boolean = false,
    val anime: Anime? = null,
    val error: String? = null,
    val title: String? = "",
)