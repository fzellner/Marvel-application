package com.fzellner.data.di

import com.fzellner.data.api.di.ApiInject
import org.koin.core.module.Module

object DataInject {

    fun modules(): List<Module> = listOf(ApiInject.module(), RepositoryInject.module())

}