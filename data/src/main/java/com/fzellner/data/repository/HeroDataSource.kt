package com.fzellner.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.commom.utils.model.NetworkState
import com.fzellner.data.api.MarvelApi
import com.fzellner.data.domain.model.HeroResult
import com.fzellner.data.utils.safeAwait
import com.fzellner.heroes.domain.model.Hero
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

class HeroDataSource(
    private val marvelApi: MarvelApi,
    private val executor: Executor,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, Hero>() {

    companion object {
        const val LIMIT = 20
        const val INITIAL_OFFSET = 0
        const val DESCRIPTION_NOT_PROVIDED = "Hero description is not provided"
        const val NAME_NOT_PROVIDED = "Name not provided"
    }

    val network = MutableLiveData<NetworkState>()
    val initial = MutableLiveData<NetworkState>()
    var retry: (() -> Any)? = null

    var currentOffset = INITIAL_OFFSET


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Hero>
    ) {
        val nextOffset = LIMIT
        makeInitialCall(currentOffset, nextOffset, params, callback)
    }

    private fun makeInitialCall(
        currentOffset: Int,
        nextOffset: Int,
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Hero>
    ) {

        scope.launch(Dispatchers.IO) {
            marvelApi.getCharacterList(
                LIMIT, currentOffset
            ).safeAwait()
                .onStart {
                    postInitialState(NetworkState.LOADING)
                }
                .onCompletion {
                    postInitialState(NetworkState.LOADED)
                }
                .catch {
                    postInitialState(NetworkState.error(it.message))
                    retry = { loadInitial(params, callback) }
                }
                .collect {
                    val heroList = it.data.results.map {
                        Hero(
                            it.id,
                            it.name?: NAME_NOT_PROVIDED,
                            it.description ?: DESCRIPTION_NOT_PROVIDED,
                            mapThumbnail(it)
                        )
                    }
                    retry = null
                    callback.onResult(heroList, null, nextOffset)
                }
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Hero>) {
        val currentOffset = params.key
        val nextOffset = currentOffset + LIMIT
        loadMoreItens(params, callback, currentOffset, nextOffset)

    }

    private fun loadMoreItens(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Hero>,
        currentOffset: Int,
        nextOffset: Int
    ) {
        scope.launch(Dispatchers.IO) {
            marvelApi.getCharacterList(
                LIMIT, currentOffset
            ).safeAwait()
                .onStart {
                    postInitialState(NetworkState.LOADING)
                }
                .onCompletion {
                    postInitialState(NetworkState.LOADED)
                }
                .catch {
                    postInitialState(NetworkState.error(it.message))
                    retry = { loadAfter(params, callback) }
                }
                .collect {
                    val heroList = it.data?.results.map {
                        Hero(
                            it.id,
                            it.name?: NAME_NOT_PROVIDED,
                            it.description ?: DESCRIPTION_NOT_PROVIDED,
                            mapThumbnail(it)
                        )
                    }
                    retry = null
                    callback.onResult(heroList, nextOffset)
                }
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Hero>) {
        //do nothing
    }

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let { retry ->
            executor.execute { retry() }
        }
    }

    private fun postInitialState(state: NetworkState) {
        network.postValue(state)
        initial.postValue(state)
    }

    private fun mapThumbnail(it: HeroResult) = it.thumbnail.path + "." + it.thumbnail.extension

}

