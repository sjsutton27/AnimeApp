package com.example.myapplication.presentation.screens.anime_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.AnimeApplication
import com.example.myapplication.presentation.components.anime_detail.AnimeDetailHeader
import com.example.myapplication.presentation.components.anime_detail.AnimeDetailImageContainer
import com.example.myapplication.presentation.components.anime_detail.AnimeDetailStateWrapper
import com.example.myapplication.presentation.viewModelFactory
import com.example.myapplication.ui.theme.PurpleGrey80

@Composable
internal fun AnimeDetailScreen(
    id: Int,
    navController: NavController,
    viewModel: AnimeDetailViewModel = viewModel<AnimeDetailViewModel>(
        factory = viewModelFactory {
            AnimeDetailViewModel(repository = AnimeApplication.appModule.animeRepository)
        }
    ),
    topPadding: Dp = 20.dp,
){
    LaunchedEffect(key1 = id) {
        viewModel.getAnimeInfo(id)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = PurpleGrey80)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            AnimeDetailHeader(
                navController = navController,
                viewModel = viewModel,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.25f)
            )

            AnimeDetailStateWrapper(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.surface),
                loadModifier = Modifier
                    .size(100.dp),
                viewModel = viewModel
            )
        }

        AnimeDetailImageContainer(
            viewModel = viewModel,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = topPadding + 40.dp)
        )
    }
}