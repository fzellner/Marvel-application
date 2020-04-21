package com.fzellner.comics.di


import com.fzellner.comics.interactor.GetMostExpensiveComic
import com.fzellner.comics.ui.ComicViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object ComicInject {
    fun modules(): List<Module> = listOf(viewModelModule, useCaseModule)

    private val viewModelModule: Module = module {
        viewModel { ComicViewModel(get()) }
    }

    private val useCaseModule: Module = module {
        factory { GetMostExpensiveComic(get()) }
    }


}

