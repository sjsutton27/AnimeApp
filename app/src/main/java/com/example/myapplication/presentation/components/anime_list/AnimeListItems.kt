package com.example.myapplication.presentation.components.anime_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.domain.model.AnimeListItemData

@Composable
internal fun AnimeListItems(
    index: Int,
    data: List<AnimeListItemData>,
    navController: NavController
){
    Column {
        Row{
            AnimeListItem(
                data = data[index],
                navController = navController,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}