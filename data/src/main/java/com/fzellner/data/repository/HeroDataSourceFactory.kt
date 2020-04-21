package com.fzellner.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.fzellner.character.domain.model.Hero
import com.fzellner.data.api.MarvelApi
import kotlinx.coroutines.CoroutineScope
import java.util.concurrent.Executor

class HeroDataSourceFactory(
    private val marvelApi: MarvelApi,
    private val executor: Executor,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Hero>() {


    val source = MutableLiveData<HeroDataSource>()

    override fun create(): DataSource<Int, Hero> {
        val source = HeroDataSource(marvelApi, executor, scope)
        this.source.postValue(source)
        return source
    }

}