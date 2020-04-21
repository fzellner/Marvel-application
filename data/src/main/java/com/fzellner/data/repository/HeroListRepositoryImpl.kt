package com.fzellner.data.repository

import androidx.annotation.MainThread
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.commom.utils.model.Listing
import com.fzellner.data.api.MarvelApi
import com.fzellner.heroes.domain.model.Hero
import com.fzellner.heroes.repository.HeroesListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.Executor


class HeroListRepositoryImpl(
    private val marvelApi: MarvelApi,
    private val networkExecutor: Executor,
    private val scope: CoroutineScope
) : HeroesListRepository {

    companion object {
        const val PAGE_SIZE = 20
    }

    @MainThread
    override fun get(): Flow<Listing<Hero>> = flow {

        val factory = heroDataSourceFactory()
        val config = pagedListConfig(PAGE_SIZE)
        val livePagedList = LivePagedListBuilder(factory, config)
            .setFetchExecutor(networkExecutor)
            .build()

        emit(
            Listing(
                pagedList = livePagedList,
                networkState = switchMap(factory.source) { it.network },
                retry = { factory.source.value?.retryAllFailed() },
                refresh = { factory.source.value?.invalidate() },
                refreshState = switchMap(factory.source) { it.initial })
        )

    }

    private fun heroDataSourceFactory(): HeroDataSourceFactory =
        HeroDataSourceFactory(marvelApi, networkExecutor, scope)


    private fun pagedListConfig(pageSize: Int): PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(pageSize * 2)
            .setPageSize(pageSize)
            .build()
    }

}
