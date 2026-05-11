package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.presentation.screens.anime_detail.AnimeDetailScreen
import com.example.myapplication.presentation.screens.anime_list.AnimeListScreen
import com.example.myapplication.ui.theme.AnimeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimeAppTheme() {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "anime_list_screen"
                ){
                    composable(route = "anime_list_screen"){
                        AnimeListScreen(
                            navController = navController
                        )
                    }
                    composable(
                        route = "anime_detail_screen/{mal_id}",
                        arguments = listOf(
                            navArgument("mal_id") { type = NavType.IntType },
                        )
                    ) { backStackEntry ->
                        val malId = backStackEntry.arguments?.getInt("mal_id") ?: 0
                        AnimeDetailScreen(
                            id = malId,
                            navController = navController,
                        )
                    }
                }
            }
        }
    }
}
