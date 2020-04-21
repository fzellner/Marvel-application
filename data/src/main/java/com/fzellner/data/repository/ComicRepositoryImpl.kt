package com.fzellner.data.repository

import com.fzellner.comics.domain.Comic
import com.fzellner.comics.domain.exception.ComicException
import com.fzellner.comics.repository.ComicRepository
import com.fzellner.data.api.MarvelApi
import com.fzellner.data.domain.exception.ForbiddenException
import com.fzellner.data.domain.exception.InvalidApiKeyException
import com.fzellner.data.domain.exception.MissingApiKeyException
import com.fzellner.data.domain.exception.NotAllowedException
import com.fzellner.data.domain.model.ComicResult
import com.fzellner.data.utils.safeAwait
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.lang.Exception

class ComicRepositoryImpl(private val marvelApi: MarvelApi) : ComicRepository {

    companion object {
        const val DESCRITION_NOT_PROVIDED = "Description not provided"
        const val TITLE_NOT_PROVIDED = "Title not provided"
    }

    override fun get(heroId: Long): Flow<Comic?> {
        return marvelApi.getComicsByHero(heroId.toString())
            .safeAwait()
            .map {
                val comicList = it.data?.results?.map {
                    Comic(
                        it.title ?: TITLE_NOT_PROVIDED,
                        it.description ?: DESCRITION_NOT_PROVIDED,
                        mapPathComic(it),
                        it.prices?.maxBy { it.price }?.price ?: 0f
                    )
                } ?: throw ComicException


                comicList?.maxBy { it.price }

            }
    }

    private fun mapPathComic(it: ComicResult) =
        it.thumbnail.path + "." + it.thumbnail.extension
}