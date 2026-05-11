package com.example.myapplication.presentation.components.anime_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.AnimeApplication
import com.example.myapplication.presentation.screens.anime_detail.AnimeDetailViewModel
import com.example.myapplication.presentation.viewModelFactory

@Composable
internal fun AnimeDetailImageContainer(
    viewModel: AnimeDetailViewModel = viewModel<AnimeDetailViewModel>(
        factory = viewModelFactory {
            AnimeDetailViewModel(repository = AnimeApplication.appModule.animeRepository)
        }
    ),
    animeImageSize: Dp = 350.dp,
    topPadding: Dp = 20.dp,
    modifier: Modifier = Modifier,
) {
    val state = viewModel.state.collectAsState().value
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier
            .fillMaxSize()
    ){
        when{
            state.anime != null -> {
                state.anime.data.images.jpg.image_url.let{ images ->
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(images)
                            .crossfade(true)
                            .build(),
                        contentDescription = state.anime.data.mal_id.toString(),
                        modifier = modifier
                            .size(animeImageSize)
                            .align(Alignment.TopCenter)
                            .offset(y = topPadding)
                    )
                }
            }

        }
    }
}
