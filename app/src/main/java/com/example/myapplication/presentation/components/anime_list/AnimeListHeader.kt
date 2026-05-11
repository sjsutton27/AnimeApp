package com.example.myapplication.presentation.components.anime_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.AnimeApplication
import com.example.myapplication.presentation.screens.anime_list.AnimeListViewModel
import com.example.myapplication.presentation.viewModelFactory
import com.example.myapplication.ui.theme.PurpleGrey80

@Composable
internal fun AnimeListHeader(
    viewModel: AnimeListViewModel = viewModel<AnimeListViewModel>(
        factory = viewModelFactory {
            AnimeListViewModel(repository = AnimeApplication.appModule.animeRepository)
        }
    )
) {

    Spacer(modifier = Modifier.height(50.dp))

    AnimeListTitle()

    Spacer(modifier = Modifier.height(16.dp))

    SearchBar(
        modifier = Modifier.padding(horizontal = 16.dp),
        hint = "Search..."
    ){ searchItem ->
        viewModel.searchAnimeList(query = searchItem)
    }
}

@Composable
internal fun AnimeListTitle() {
    Text(
        text = "Anime Browser",
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        color = PurpleGrey80,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {

    var text by remember {
        mutableStateOf(value = "")
    }

    var isHintDisplayed by remember {
        mutableStateOf(value = hint != "")
    }

    Box(modifier = modifier) {

        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 5.dp, shape = CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged { focusState ->
                    isHintDisplayed =
                        !focusState.isFocused && text.isEmpty()
                }
        )

        if (isHintDisplayed) {

            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier.padding(
                    horizontal = 20.dp,
                    vertical = 12.dp
                )
            )
        }
    }
}