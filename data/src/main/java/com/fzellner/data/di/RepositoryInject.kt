package com.fzellner.data.di

import com.fzellner.comics.repository.ComicRepository
import com.fzellner.data.repository.ComicRepositoryImpl
import com.fzellner.data.repository.HeroRepositoryImpl
import com.fzellner.heroes.repository.HeroesRepository
import org.koin.core.module.Module
import org.koin.dsl.module

object RepositoryInject {
    fun module(): Module = module{
        single<HeroesRepository> { HeroRepositoryImpl(get(),get(),get()) }
        single<ComicRepository> { ComicRepositoryImpl(get()) }
    }
}