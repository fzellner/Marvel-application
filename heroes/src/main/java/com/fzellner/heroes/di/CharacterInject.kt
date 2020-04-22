package com.fzellner.heroes.di

import com.fzellner.heroes.interactor.GetAll
import com.fzellner.heroes.ui.HeroesViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object CharacterInject {
    fun modules(): List<Module> = listOf(viewModelModule, useCaseModule)

    private val viewModelModule: Module = module {
        single { HeroesViewModel(get())}
    }

    private val useCaseModule: Module = module {
        factory { GetAll(get()) }
    }


}