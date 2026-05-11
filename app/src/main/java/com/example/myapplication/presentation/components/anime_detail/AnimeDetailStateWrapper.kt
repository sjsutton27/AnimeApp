package com.example.myapplication.presentation.components.anime_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.presentation.screens.anime_detail.AnimeDetailViewModel

@Composable
internal fun AnimeDetailStateWrapper(
    modifier: Modifier = Modifier,
    loadModifier: Modifier = Modifier,
    viewModel: AnimeDetailViewModel
) {
    val state = viewModel.state.collectAsState().value
    
    Box(modifier = modifier) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = loadModifier
                )
            }
            state.error != null -> {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            state.anime != null -> {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 300.dp, start = 16.dp, end = 16.dp)
                        .verticalScroll(state = rememberScrollState())
                ) {
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = state.anime.data.synopsis,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
