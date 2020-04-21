package com.fzellner.data.api.di

import com.fzellner.data.BuildConfig
import com.fzellner.data.api.MarvelApi
import com.fzellner.data.integration.RetrofitApi
import org.koin.core.module.Module
import org.koin.dsl.module

object ApiInject {

    fun module(): Module = module {
        single {
            RetrofitApi.createNetworkClient(BuildConfig.HOST).create(MarvelApi::class.java)
        }
    }

}