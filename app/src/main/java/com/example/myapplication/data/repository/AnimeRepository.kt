package com.example.myapplication.data.repository

import com.example.myapplication.data.remote.AnimeApi
import com.example.myapplication.data.remote.responses.Anime
import com.example.myapplication.data.remote.responses.AnimeList
import com.example.myapplication.common.resource.Resource

class AnimeRepository(
    private val api: AnimeApi
){
    suspend fun getAnimeList(limit: Int, page: Int): Resource<AnimeList> {
        val response = try{
            api.getAnimeList(
                limit = limit,
                page = page
            )
        }catch(e : Exception){
            return Resource.Error(message = "Unknown Error Occurred")
        }
        return Resource.Success(data = response)
    }

    suspend fun getAnimeInfo(id: Int): Resource<Anime> {
        val response = try{
            api.getAnimeInfo(
                mal_id = id
            )
        }catch(e : Exception){
            return Resource.Error(message = "Unknown Error Occurred")
        }
        return Resource.Success(data = response)
    }
}