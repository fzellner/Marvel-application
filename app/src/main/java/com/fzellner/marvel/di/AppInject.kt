package com.fzellner.marvel.di

import android.content.ContentResolver
import com.fzellner.character.di.CharacterInject
import com.fzellner.comics.di.ComicInject
import com.fzellner.comics.domain.Comic
import com.fzellner.data.di.DataInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.concurrent.Executor
import java.util.concurrent.Executors

object AppInject {

    private val appModules = module {
        single<ContentResolver> { androidContext().contentResolver }
        single { Executors.newSingleThreadExecutor() as Executor }
        factory { CoroutineScope(Dispatchers.IO) }
    }

    fun modules(): List<Module> =
        ArrayList<Module>().apply {
            add(appModules)
            addAll(CharacterInject.modules())
            addAll(ComicInject.modules())
            addAll(DataInject.modules())

        }
}