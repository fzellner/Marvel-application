package com.fzellner.marvel

import android.app.Application
import com.fzellner.marvel.di.AppInject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MarvelApplication : Application(){


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MarvelApplication)
            modules(AppInject.modules())
        }
    }


}