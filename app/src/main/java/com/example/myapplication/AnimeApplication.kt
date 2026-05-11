package com.example.myapplication

import android.app.Application
import com.example.myapplication.di.AppModule
import com.example.myapplication.di.AppModuleImpl
import timber.log.Timber

class AnimeApplication : Application() {
    companion object{
        lateinit var appModule: AppModule
    }
    override fun onCreate(){
        super.onCreate()
        appModule = AppModuleImpl(appContext = this)
        Timber.plant(tree = Timber.DebugTree())
    }
}