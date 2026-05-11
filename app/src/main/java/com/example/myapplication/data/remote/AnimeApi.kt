package com.example.myapplication.data.remote

import com.example.myapplication.data.remote.responses.Anime
import com.example.myapplication.data.remote.responses.AnimeList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApi {
    @GET(value = "anime")
    suspend fun getAnimeList(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): AnimeList

    @GET(value = "anime/{mal_id}")
    suspend fun getAnimeInfo(
        @Path("mal_id") mal_id: Int
    ): Anime
}