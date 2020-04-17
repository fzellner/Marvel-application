package com.fzellner.data.repository

import com.fzellner.character.domain.model.Hero
import com.fzellner.character.repository.HeroesListRepository
import com.fzellner.data.api.MarvelApi
import com.fzellner.data.domain.model.ApiKeyProvider
import com.fzellner.data.utils.safeAwait
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*


class HeroListRepositoryImpl(
    private val apiKeyProvider: ApiKeyProvider,
    private val marvelApi: MarvelApi
) : HeroesListRepository {
    override fun get(limit: Int, offset: Int): Flow<List<Hero>> {
        val timestamp = (Calendar.getInstance().timeInMillis/1000).toString()
        return marvelApi.getCharacterList(timestamp, apiKeyProvider.apiKey, apiKeyProvider.hash ,limit, offset)
            .safeAwait()
            .map {
                it.data?.results?.map {
                    Hero(
                        it.id,
                        it.name,
                        it.description,
                        it.thumbnail.path
                    )
                } ?: emptyList()
            }
    }


}