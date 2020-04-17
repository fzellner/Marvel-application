package com.fzellner.character.di

import com.fzellner.character.interactor.GetHeroes
import com.fzellner.character.ui.CharacterListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object CharacterInject {
    fun modules(): List<Module> = listOf(viewModelModule, useCaseModule)

    private val viewModelModule: Module = module {
        viewModel { CharacterListViewModel(get())}
    }

    private val useCaseModule: Module = module {
        factory { GetHeroes(get()) }
    }


}