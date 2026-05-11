package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.data.remote.AnimeApi
import com.example.myapplication.data.repository.AnimeRepository
import com.example.myapplication.common.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppModule{
    val animeApi: AnimeApi
    val animeRepository: AnimeRepository
}

class AppModuleImpl(
    private val appContext: Context
): AppModule{
    override val animeApi: AnimeApi by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AnimeApi::class.java)
    }
    override val animeRepository: AnimeRepository by lazy{
        AnimeRepository(api = animeApi)
    }

}