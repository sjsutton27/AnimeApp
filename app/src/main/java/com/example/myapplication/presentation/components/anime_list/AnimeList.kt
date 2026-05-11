package com.example.myapplication.presentation.components.anime_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.AnimeApplication
import com.example.myapplication.presentation.screens.anime_list.AnimeListViewModel
import com.example.myapplication.presentation.viewModelFactory

@Composable
internal fun AnimeList(
    navController: NavController,
    viewModel: AnimeListViewModel = viewModel<AnimeListViewModel>(
        factory = viewModelFactory {
            AnimeListViewModel(repository = AnimeApplication.appModule.animeRepository)
        }
    )
) {
    val animeList by viewModel.animeList.collectAsState()
    val endReached by viewModel.endReached.collectAsState()
    val loadError by viewModel.loadError.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(contentPadding = PaddingValues(vertical = 16.dp)) {
            val itemCount = if(animeList.size % 2 == 0){
                animeList.size / 2
            }else{
                animeList.size / 2 + 1
            }

            items(count = itemCount) { index ->
                if (index >= itemCount - 1 && !endReached && !isLoading && !isSearching) {
                    viewModel.loadAnimePaginated()
                }
                AnimeListItems(
                    index = index,
                    data = animeList,
                    navController = navController
                )
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
            if (loadError.isNotEmpty()) {
                RetrySection(error = loadError) {
                    viewModel.loadAnimePaginated()
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        if (animeList.isEmpty()) {
            viewModel.loadAnimePaginated()
        }
    }
}

@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column {
        Text(error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}