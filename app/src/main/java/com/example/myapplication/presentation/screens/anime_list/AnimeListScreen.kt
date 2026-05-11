package com.example.myapplication.presentation.screens.anime_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.myapplication.presentation.components.anime_list.AnimeList
import com.example.myapplication.presentation.components.anime_list.AnimeListHeader
import com.example.myapplication.ui.theme.PurpleGrey40

@Composable
fun AnimeListScreen(
    navController: NavController
){
    Surface(
        color = PurpleGrey40,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column{
            AnimeListHeader()
            AnimeList(
                navController = navController
            )
        }
    }
}