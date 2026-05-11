package com.example.myapplication.presentation.components.anime_list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.example.myapplication.domain.model.AnimeListItemData
import com.example.myapplication.ui.theme.HotPink
import com.example.myapplication.ui.theme.PurpleGrey40
import com.example.myapplication.ui.theme.PurpleGrey80
import com.example.myapplication.ui.theme.White

@Composable
internal fun AnimeListItem(
    data: AnimeListItemData,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(
                    route = "anime_detail_screen/${data.id}"
                )
            }
            .padding(
                top = 12.dp,
                start = 5.dp,
                end = 5.dp,
                bottom = 12.dp
            )
            .border(
                width = 2.dp,
                color = White,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = PurpleGrey80,
                shape = RoundedCornerShape(12.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data.imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = data.title,
            loading = {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .scale(0.5f)
                        .align(Alignment.Center)
                )
            },
            success = {
                SubcomposeAsyncImageContent()
            },
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterVertically)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {

            Text(
                text = data.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Text(
                text = "Rated ${data.rating}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Made in year ${data.year}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}