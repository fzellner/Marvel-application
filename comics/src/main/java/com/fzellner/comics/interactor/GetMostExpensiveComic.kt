package com.fzellner.comics.interactor

import com.example.common.utils.model.UseCase
import com.fzellner.comics.domain.Comic
import com.fzellner.comics.repository.ComicRepository
import kotlinx.coroutines.flow.Flow

class GetMostExpensiveComic(private val repository: ComicRepository) :
    UseCase<Comic?, GetMostExpensiveComic.Params>() {

    override fun run(params: Params): Flow<Comic?> =
        repository.get(params.heroId)

    data class Params(val heroId:Long)

}