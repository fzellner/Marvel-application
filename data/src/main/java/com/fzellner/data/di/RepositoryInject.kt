package com.fzellner.data.di

import com.fzellner.character.repository.HeroesListRepository
import com.fzellner.data.repository.HeroListRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

object RepositoryInject {
    fun module(): Module = module{
        single<HeroesListRepository> { HeroListRepositoryImpl(get(),get ()) }
    }
}